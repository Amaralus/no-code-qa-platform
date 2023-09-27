package org.example.nocodetestplatform.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.rocksdb.*;
import org.springframework.beans.factory.DisposableBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
public final class RocksDbLifecycleBean implements DisposableBean {

    private final List<ColumnFamilyHandle> columnFamilyHandleList = new ArrayList<>();
    private final ColumnFamilyOptions columnFamilyOptions;
    private final DBOptions options;
    @Getter
    private final RocksDB rocksDB;

    public RocksDbLifecycleBean(String dirPath) throws RocksDBException {
        this(dirPath, Collections.emptyList());
    }

    @SuppressWarnings("java:S2095")
    public RocksDbLifecycleBean(@NotNull String dirPath, @NotNull List<String> columnFamiliesNames) throws RocksDBException {
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

            rocksDB = RocksDB.open(options, dirPath, cfDescriptors, columnFamilyHandleList);
            log.debug("RocksDB initialized");
        } catch (RocksDBException e) {
            destroy();
            throw e;
        }
    }

    @Override
    public void destroy() {
        columnFamilyHandleList.forEach(ColumnFamilyHandle::close);

        if (columnFamilyOptions != null)
            columnFamilyOptions.close();

        if (rocksDB != null)
            rocksDB.close();

        if (options != null)
            options.close();

        log.debug("RocksDB successfully finished");
    }
}
