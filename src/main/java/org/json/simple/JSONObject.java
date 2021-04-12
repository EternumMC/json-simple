/*
 * $Id: JSONObject.java,v 1.1 2006/04/15 14:10:48 platform Exp $
 * Created on 2006-4-10
 */
package org.json.simple;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A JSON object. Key value pairs are unordered. JSONObject supports java.util.Map interface.
 *
 * @author FangYidong<fangyidong@yahoo.com.cn>
 * @author l_MrBoom_l<admin@epserv.ru>
 */
public class JSONObject extends HashMap<Object, Object> implements Map<Object, Object>, JSONAware, JSONStreamAware {

    private static final long serialVersionUID = -503443796854799292L;


    public JSONObject() {
        super();
    }

    /**
     * Allows creation of a JSONObject from a Map. After that, both the
     * generated JSONObject and the Map can be modified independently.
     *
     * @param map Map to use as source
     */
    public JSONObject(Map map) {
        super(map);
    }


    /**
     * Encode a map into JSON text and write it to out.
     * If this map is also a JSONAware or JSONStreamAware, JSONAware or JSONStreamAware specific behaviours will be ignored at this top level.
     *
     * @param map {@link Map} to use as input
     * @param out {@link Writer} to use as output
     * @see org.json.simple.JSONValue#writeJSONString(Object, Writer)
     */
    public static void writeJSONString(Map<Object, Object> map, Writer out) throws IOException {
        if (map == null) {
            out.write("null");
            return;
        }

        boolean first = true;
        Iterator<Entry<Object, Object>> iterator = map.entrySet().iterator();

        out.write('{');
        while (iterator.hasNext()) {
            if (first)
                first = false;
            else
                out.write(',');
            Map.Entry<Object, Object> entry = iterator.next();
            out.write('\"');
            out.write(escape(String.valueOf(entry.getKey())));
            out.write('\"');
            out.write(':');
            JSONValue.writeJSONString(entry.getValue(), out);
        }
        out.write('}');
    }

    /**
     * Convert a map to JSON text. The result is a JSON object.
     * If this map is also a JSONAware, JSONAware specific behaviours will be omitted at this top level.
     *
     * @param map {@link Map} to use as input
     * @return JSON text, or "null" if map is null.
     * @see org.json.simple.JSONValue#toJSONString(Object)
     */
    public static String toJSONString(Map<Object, Object> map) {
        final StringWriter writer = new StringWriter();

        try {
            writeJSONString(map, writer);
            return writer.toString();
        } catch (IOException e) {
            // This should never happen with a StringWriter
            throw new RuntimeException(e);
        }
    }

    public static String toString(String key, Object value) {
        StringBuilder sb = new StringBuilder();
        sb.append('\"');
        if (key == null)
            sb.append("null");
        else
            JSONValue.escape(key, sb);
        sb.append('\"').append(':');

        sb.append(JSONValue.toJSONString(value));

        return sb.toString();
    }

    /**
     * Escape quotes, \, /, \r, \n, \b, \f, \t and other control characters (U+0000 through U+001F).
     * It's the same as JSONValue.escape() only for compatibility here.
     *
     * @param s {@link String} to escape
     * @return JSON-escaped {@link String}
     * @see org.json.simple.JSONValue#escape(String)
     */
    public static String escape(String s) {
        return JSONValue.escape(s);
    }

    public void writeJSONString(Writer out) throws IOException {
        writeJSONString(this, out);
    }

    public String toJSONString() {
        return toJSONString(this);
    }

    public String toString() {
        return toJSONString();
    }

    public String getString(String key) {
        return this.get(key).toString();
    }

    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(this.get(key).toString());
    }

    public byte getByte(String key) {
        return Byte.parseByte(this.get(key).toString());
    }

    public short getShort(String key) {
        return Short.parseShort(this.get(key).toString());
    }

    public int getInt(String key) {
        return Integer.parseInt(this.get(key).toString());
    }

    public long getLong(String key) {
        return Long.parseLong(this.get(key).toString());
    }

    public float getFloat(String key) {
        return Float.parseFloat(this.get(key).toString());
    }

    public double getDouble(String key) {
        return Double.parseDouble(this.get(key).toString());
    }

    public JSONObject child(String key) {
        return (JSONObject) this.get(key);
    }

    public JSONArray array(String key) {
        return (JSONArray) this.get(key);
    }

}
