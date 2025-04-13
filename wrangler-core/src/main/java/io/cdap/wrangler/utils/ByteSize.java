package io.cdap.wrangler.utils;

public class ByteSize {
    private final long bytes;

    public ByteSize(String value) {
        this.bytes = parseBytes(value);
    }

    private long parseBytes(String value) {
        value = value.trim().toUpperCase();
        if (value.endsWith("KB")) return Long.parseLong(value.replace("KB", "")) * 1024;
        if (value.endsWith("MB")) return Long.parseLong(value.replace("MB", "")) * 1024 * 1024;
        if (value.endsWith("GB")) return Long.parseLong(value.replace("GB", "")) * 1024 * 1024 * 1024;
        if (value.endsWith("TB")) return Long.parseLong(value.replace("TB", "")) * 1024L * 1024L * 1024L * 1024L;
        return Long.parseLong(value);
    }

    public long getBytes() {
        return bytes;
    }

    @Override
    public String toString() {
        return bytes + " bytes";
    }
}
