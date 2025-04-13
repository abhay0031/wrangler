package io.cdap.wrangler.api.parser;

import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

public class TimeDuration implements Token {
    private final String original;
    private final long milliseconds;

    public TimeDuration(String value) {
        this.original = value.trim().toLowerCase();
        this.milliseconds = parseTime(this.original);
    }

    private long parseTime(String input) {
        double number = Double.parseDouble(input.replaceAll("[a-z]+", ""));
        if (input.endsWith("ms")) return (long) number;
        if (input.endsWith("s")) return (long) (number * 1000);
        if (input.endsWith("m")) return (long) (number * 60 * 1000);
        if (input.endsWith("h")) return (long) (number * 60 * 60 * 1000);
        throw new IllegalArgumentException("Invalid time duration format: " + input);
    }

    public long getMilliseconds() {
        return milliseconds;
    }

    @Override
    public TokenType type() {
        return TokenType.TIME_DURATION;
    }

    @Override
    public Object value() {
        return milliseconds;
    }

    @Override
    public JsonElement toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put("type", type().name());
        json.put("value", milliseconds);
        return new Gson().toJsonTree(json);
    }
}
