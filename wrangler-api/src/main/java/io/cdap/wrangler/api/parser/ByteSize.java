package io.cdap.wrangler.api.parser;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

/**
 * A class to represent and parse byte size strings like "10KB", "1.5MB", etc.
 */
public class ByteSize implements Token {

    private final String original;
    private final long bytes;

    public ByteSize(String value) {
        this.original = value.trim().toUpperCase();
        this.bytes = parseBytes(this.original);
    }

    private long parseBytes(String value) {
        if (value.endsWith("KB")) {
            return (long) (Double.parseDouble(value.replace("KB", "")) * 1024);
        } else if (value.endsWith("MB")) {
            return (long) (Double.parseDouble(value.replace("MB", "")) * 1024 * 1024);
        } else if (value.endsWith("GB")) {
            return (long) (Double.parseDouble(value.replace("GB", "")) * 1024 * 1024 * 1024);
        } else if (value.endsWith("B")) {
            return Long.parseLong(value.replace("B", ""));
        } else {
            throw new IllegalArgumentException("Invalid byte size format: " + value);
        }
    }

    public long getBytes() {
        return this.bytes;
    }

    @Override
    public TokenType type() {
        return TokenType.BYTE_SIZE;
    }

    @Override
    public Object value() {
        return bytes;
    }

    @Override
    public JsonElement toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put("type", type().name());
        json.put("value", bytes);
        return new Gson().toJsonTree(json);
    }
}
