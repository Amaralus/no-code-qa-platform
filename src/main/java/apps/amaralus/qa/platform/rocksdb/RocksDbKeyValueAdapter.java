package apps.amaralus.qa.platform.rocksdb;

import apps.amaralus.qa.platform.rocksdb.sequence.Sequence;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.rocksdb.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.keyvalue.core.AbstractKeyValueAdapter;
import org.springframework.data.keyvalue.core.ForwardingCloseableIterator;
import org.springframework.data.util.CloseableIterator;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public final class RocksDbKeyValueAdapter extends AbstractKeyValueAdapter {
    private static final String ID_NOT_NULL_MESSAGE = "id must not be null!";
    private static final String KEYSPACE_NOT_NULL_MESSAGE = "keyspace must not be null!";
    private final Map<String, ColumnFamilyHandle> keySpaces;
    private final Map<String, Class<?>> keySpacesEntityClasses;
    private final ColumnFamilyOptions columnFamilyOptions;
    private final DBOptions options;
    private final RocksDB rocksDB;
    private final RocksDbEntityConverter converter = new RocksDbEntityConverter();

    @SuppressWarnings("java:S2095")
    public RocksDbKeyValueAdapter(@NotNull String dirPath, @NotNull Map<String, Class<?>> keySpacesEntityClasses) 
            throws RocksDBException {
        log.info("Starting RocksDB v{}", RocksDB.rocksdbVersion());
        Assert.notNull(dirPath, "dirPath must not be null!");
        Assert.notNull(keySpacesEntityClasses, "keySpacesEntityClasses must not be null!");
        
        try {
            RocksDB.loadLibrary();
            columnFamilyOptions = new ColumnFamilyOptions().optimizeUniversalStyleCompaction();

            var cfDescriptors = new ArrayList<ColumnFamilyDescriptor>();
            cfDescriptors.add(new ColumnFamilyDescriptor(RocksDB.DEFAULT_COLUMN_FAMILY, columnFamilyOptions));
            cfDescriptors.add(new ColumnFamilyDescriptor(Sequence.KEY_SPACE.getBytes(StandardCharsets.UTF_8), columnFamilyOptions));

            this.keySpacesEntityClasses = new HashMap<>(keySpacesEntityClasses);
            this.keySpacesEntityClasses.put(Sequence.KEY_SPACE, Sequence.class);

            keySpacesEntityClasses.keySet()
                    .forEach(name -> cfDescriptors.add(new ColumnFamilyDescriptor(name.getBytes(), columnFamilyOptions)));

            options = new DBOptions()
                    .setCreateIfMissing(true)
                    .setCreateMissingColumnFamilies(true);

            var columnFamilyHandleList = new ArrayList<ColumnFamilyHandle>();
            rocksDB = RocksDB.open(options, dirPath, cfDescriptors, columnFamilyHandleList);

            this.keySpaces = new ConcurrentHashMap<>();
            for (var columnFamilyHandle : columnFamilyHandleList)
                this.keySpaces.put(new String(columnFamilyHandle.getName()), columnFamilyHandle);

        } catch (RocksDBException e) {
            destroy();
            throw e;
        }
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public Object put(@NotNull Object id, @NotNull Object item, @NotNull String keyspace) {
        Assert.notNull(id, ID_NOT_NULL_MESSAGE);
        Assert.notNull(item, "item must not be null!");
        Assert.notNull(keyspace, KEYSPACE_NOT_NULL_MESSAGE);
        
        var previous = get(id, keyspace);
        try {
            rocksDB.put(getKeySpaceHandle(keyspace), converter.idToBytes(id), converter.entityToBytes(item));
        } catch (RocksDBException e) {
            throw new RocksDbRuntimeException(e);
        }
        return previous;
    }

    @Override
    public boolean contains(@NotNull Object id, @NotNull String keyspace) {
        Assert.notNull(id, ID_NOT_NULL_MESSAGE);
        Assert.notNull(keyspace, KEYSPACE_NOT_NULL_MESSAGE);
        
        return get(id, keyspace) != null;
    }

    @Override
    public Object get(@NotNull Object id, @NotNull String keyspace) {
        Assert.notNull(id, ID_NOT_NULL_MESSAGE);
        Assert.notNull(keyspace, KEYSPACE_NOT_NULL_MESSAGE);
        
        try {
            var payload = rocksDB.get(getKeySpaceHandle(keyspace), converter.idToBytes(id));
            return converter.bytesToEntity(payload, getKeySpaceClass(keyspace));
        } catch (RocksDBException e) {
            throw new RocksDbRuntimeException(e);
        }
    }

    @Override
    public Object delete(@NotNull Object id, @NotNull String keyspace) {
        Assert.notNull(id, ID_NOT_NULL_MESSAGE);
        Assert.notNull(keyspace, KEYSPACE_NOT_NULL_MESSAGE);
        
        var previous = get(id, keyspace);
        try {
            if (previous != null)
                rocksDB.delete(getKeySpaceHandle(keyspace), converter.idToBytes(id));
        } catch (RocksDBException e) {
            throw new RocksDbRuntimeException(e);
        }

        return previous;
    }

    @Override
    public @NotNull Iterable<?> getAllOf(@NotNull String keyspace) {
        Assert.notNull(keyspace, KEYSPACE_NOT_NULL_MESSAGE);
        return getAll(keyspace).values();
    }

    @Override
    public @NotNull CloseableIterator<Map.Entry<Object, Object>> entries(@NotNull String keyspace) {
        Assert.notNull(keyspace, KEYSPACE_NOT_NULL_MESSAGE);
        return new ForwardingCloseableIterator<>(getAll(keyspace).entrySet().iterator());
    }

    private Map<Object, Object> getAll(String keySpace) {
        try (var iterator = rocksDB.newIterator(getKeySpaceHandle(keySpace))) {

            var map = new HashMap<>();
            var entityClass = getKeySpaceClass(keySpace);
            var keyClass = Arrays.stream(entityClass.getDeclaredFields())
                    .filter(field -> field.getAnnotation(Id.class) != null)
                    .findFirst()
                    .map(Field::getType)
                    .orElseThrow();

            for (iterator.seekToFirst(); iterator.isValid(); iterator.next()) {
                var key = converter.bytesToId(iterator.key(), keyClass);
                var value = converter.bytesToEntity(iterator.value(), entityClass);
                map.put(key, value);
            }

            iterator.status();

            return map;
        } catch (RocksDBException e) {
            throw new RocksDbRuntimeException(e);
        }
    }

    @Override
    public void deleteAllOf(@NotNull String keyspace) {
        Assert.notNull(keyspace, KEYSPACE_NOT_NULL_MESSAGE);
        
        keySpaces.computeIfPresent(keyspace, (keySpace, columnFamilyHandle) -> {
            try {
                rocksDB.dropColumnFamily(columnFamilyHandle);
                return rocksDB.createColumnFamily(new ColumnFamilyDescriptor(keySpace.getBytes()));
            } catch (RocksDBException e) {
                throw new RocksDbRuntimeException(e);
            }
        });
    }

    @Override
    public void clear() {
        keySpaces.forEach((keySpace, columnFamilyHandle) -> deleteAllOf(keySpace));
    }

    @Override
    public long count(@NotNull String keyspace) {
        Assert.notNull(keyspace, KEYSPACE_NOT_NULL_MESSAGE);
        try {
            return rocksDB.getLongProperty(getKeySpaceHandle(keyspace), "rocksdb.estimate-num-keys");
        } catch (RocksDBException e) {
            throw new RocksDbRuntimeException(e);
        }
    }

    @Override
    public void destroy() {
        log.debug("Shutdown RocksDB");
        if (keySpaces != null)
            keySpaces.values().forEach(ColumnFamilyHandle::close);

        if (columnFamilyOptions != null)
            columnFamilyOptions.close();

        if (rocksDB != null)
            rocksDB.close();

        if (options != null)
            options.close();
    }

    private ColumnFamilyHandle getKeySpaceHandle(String keySpace) {
        return keySpaces.get(keySpace);
    }

    private Class<?> getKeySpaceClass(String keySpace) {
        return keySpacesEntityClasses.get(keySpace);
    }
}
