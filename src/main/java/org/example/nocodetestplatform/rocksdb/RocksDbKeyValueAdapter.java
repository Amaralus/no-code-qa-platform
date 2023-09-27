package org.example.nocodetestplatform.rocksdb;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.rocksdb.*;
import org.springframework.data.keyvalue.core.AbstractKeyValueAdapter;
import org.springframework.data.util.CloseableIterator;

import java.util.*;

@Slf4j
public final class RocksDbKeyValueAdapter extends AbstractKeyValueAdapter {
    private final Map<String, ColumnFamilyHandle> columnFamilyHandles;
    private final ColumnFamilyOptions columnFamilyOptions;
    private final DBOptions options;
    private final RocksDB rocksDB;

    @SuppressWarnings("java:S2095")
    public RocksDbKeyValueAdapter(@NotNull String dirPath, @NotNull List<String> columnFamiliesNames) throws RocksDBException {
        log.info("Initializing RocksDB");
        try {
            RocksDB.loadLibrary();

            columnFamilyOptions = new ColumnFamilyOptions().optimizeUniversalStyleCompaction();

            var cfDescriptors = new ArrayList<ColumnFamilyDescriptor>();
            cfDescriptors.add(new ColumnFamilyDescriptor(RocksDB.DEFAULT_COLUMN_FAMILY, columnFamilyOptions));
            columnFamiliesNames.forEach(name -> cfDescriptors.add(new ColumnFamilyDescriptor(name.getBytes(), columnFamilyOptions)));

            options = new DBOptions()
                    .setCreateIfMissing(true)
                    .setCreateMissingColumnFamilies(true);

            var columnFamilyHandleList = new ArrayList<ColumnFamilyHandle>();
            rocksDB = RocksDB.open(options, dirPath, cfDescriptors, columnFamilyHandleList);

            var map = new HashMap<String, ColumnFamilyHandle>();
            for (var columnFamilyHandle : columnFamilyHandleList)
                map.put(new String(columnFamilyHandle.getName()), columnFamilyHandle);
            columnFamilyHandles = Collections.unmodifiableMap(map);

        } catch (RocksDBException e) {
            destroy();
            throw e;
        }
    }

    @Override
    public @NotNull Object put(@NotNull Object id, @NotNull Object item, @NotNull String keyspace) {
        return null;
    }

    @Override
    public boolean contains(@NotNull Object id, @NotNull String keyspace) {
        return false;
    }

    @Override
    public Object get(@NotNull Object id, @NotNull String keyspace) {
        return null;
    }

    @Override
    public Object delete(@NotNull Object id, @NotNull String keyspace) {
        return null;
    }

    @Override
    public @NotNull Iterable<?> getAllOf(@NotNull String keyspace) {
        return null;
    }

    @Override
    public @NotNull CloseableIterator<Map.Entry<Object, Object>> entries(@NotNull String keyspace) {
        return null;
    }

    @Override
    public void deleteAllOf(@NotNull String keyspace) {

    }

    @Override
    public void clear() {

    }

    @Override
    public long count(@NotNull String keyspace) {
        return 0;
    }

    @Override
    public void destroy() {
        columnFamilyHandles.values().forEach(ColumnFamilyHandle::close);

        if (columnFamilyOptions != null)
            columnFamilyOptions.close();

        if (rocksDB != null)
            rocksDB.close();

        if (options != null)
            options.close();
    }
}
