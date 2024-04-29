package apps.amaralus.qa.platform.rocksdb;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RocksDbEntityConverter {

    private final ObjectMapper mapper = new ObjectMapper()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .registerModule(new JavaTimeModule());

    public byte[] entityToBytes(Object entity) {
        try {
            return mapper.writer().writeValueAsBytes(entity);
        } catch (JsonProcessingException e) {
            throw new RocksDbRuntimeException(e);
        }
    }

    public <T> T bytesToEntity(byte[] bytes, Class<T> type) {
        if (bytes == null)
            return null;

        try {
            return mapper.reader().readValue(bytes, type);
        } catch (IOException e) {
            throw new RocksDbRuntimeException(e);
        }
    }

    public byte[] idToBytes(Object id) {
        if (id instanceof Integer i)
            return intToBytes(i);
        else if (id instanceof Long l)
            return longToBytes(l);
        else if (id instanceof String s)
            return s.getBytes(StandardCharsets.UTF_8);
        else
            throw new UnsupportedOperationException("Id type [" + id.getClass().getName() + "] is unsupported!");
    }

    public Object bytesToId(byte[] bytes, Class<?> idClass) {
        if (idClass.isAssignableFrom(Integer.class))
            return bytesToInt(bytes);
        if (idClass.isAssignableFrom(Integer.TYPE))
            return bytesToInt(bytes);
        else if (idClass.isAssignableFrom(Long.class))
            return bytesToLong(bytes);
        else if (idClass.isAssignableFrom(Long.TYPE))
            return bytesToLong(bytes);
        else if (idClass.isAssignableFrom(String.class))
            return new String(bytes, StandardCharsets.UTF_8);
        else
            throw new UnsupportedOperationException("Id type [" + idClass.getName() + "] is unsupported!");
    }

    private byte[] intToBytes(int data) {
        return new byte[]{
                (byte) ((data >> 24) & 0xff),
                (byte) ((data >> 16) & 0xff),
                (byte) ((data >> 8) & 0xff),
                (byte) ((data) & 0xff),
        };
    }

    private int bytesToInt(byte[] data) {
        if (data == null || data.length != 4) return 0x0;
        return ((0xff & data[0]) << 24 |
                (0xff & data[1]) << 16 |
                (0xff & data[2]) << 8 |
                (0xff & data[3]));
    }

    private byte[] longToBytes(long data) {
        return new byte[]{
                (byte) ((data >> 56) & 0xff),
                (byte) ((data >> 48) & 0xff),
                (byte) ((data >> 40) & 0xff),
                (byte) ((data >> 32) & 0xff),
                (byte) ((data >> 24) & 0xff),
                (byte) ((data >> 16) & 0xff),
                (byte) ((data >> 8) & 0xff),
                (byte) ((data) & 0xff),
        };
    }

    @SuppressWarnings("cast")
    private long bytesToLong(byte[] data) {
        if (data == null || data.length != 8) return 0x0;
        return ((long) (0xff & data[0]) << 56 |
                (long) (0xff & data[1]) << 48 |
                (long) (0xff & data[2]) << 40 |
                (long) (0xff & data[3]) << 32 |
                (long) (0xff & data[4]) << 24 |
                (long) (0xff & data[5]) << 16 |
                (long) (0xff & data[6]) << 8 |
                (long) (0xff & data[7])
        );
    }
}
