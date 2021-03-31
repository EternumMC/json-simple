/*
 * $Id: JSONArray.java,v 1.1 2006/04/15 14:10:48 platform Exp $
 * Created on 2006-4-10
 */
package org.json.simple;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * A JSON array. JSONObject supports java.util.List interface.
 *
 * @author FangYidong<fangyidong @ yahoo.com.cn>
 */
public class JSONArray extends ArrayList<Object> implements JSONAware, JSONStreamAware {
    private static final long serialVersionUID = 3957988303675231981L;

    /**
     * Constructs an empty JSONArray.
     */
    public JSONArray() {
        super();
    }

    /**
     * Constructs a JSONArray containing the elements of the specified
     * collection, in the order they are returned by the collection's iterator.
     *
     * @param c the collection whose elements are to be placed into this JSONArray
     */
    public JSONArray(Collection c) {
        super(c);
    }

    /**
     * Encode a list into JSON text and write it to out.
     * If this list is also a JSONStreamAware or a JSONAware, JSONStreamAware and JSONAware specific behaviours will be ignored at this top level.
     *
     * @param collection Collection to use as input
     * @param out        Writer to use as output
     * @see org.json.simple.JSONValue#writeJSONString(Object, Writer)
     */
    public static void writeJSONString(Collection<Object> collection, Writer out) throws IOException {
        if (collection == null) {
            out.write("null");
            return;
        }

        boolean first = true;
        Iterator<Object> iterator = collection.iterator();

        out.write('[');
        while (iterator.hasNext()) {
            if (first)
                first = false;
            else
                out.write(',');

            Object value = iterator.next();
            if (value == null) {
                out.write("null");
                continue;
            }

            JSONValue.writeJSONString(value, out);
        }
        out.write(']');
    }

    /**
     * Convert a list to JSON text. The result is a JSON array.
     * If this list is also a JSONAware, JSONAware specific behaviours will be omitted at this top level.
     *
     * @param collection Collection to use as input
     * @return JSON text, or "null" if list is null.
     * @see org.json.simple.JSONValue#toJSONString(Object)
     */
    public static String toJSONString(Collection<Object> collection) {
        final StringWriter writer = new StringWriter();

        try {
            writeJSONString(collection, writer);
            return writer.toString();
        } catch (IOException e) {
            // This should never happen for a StringWriter
            throw new RuntimeException(e);
        }
    }

    public static void writeJSONString(byte[] array, Writer out) throws IOException {
        if (array == null) {
            out.write("null");
        } else if (array.length == 0) {
            out.write("[]");
        } else {
            out.write("[");
            out.write(String.valueOf(array[0]));

            for (int i = 1; i < array.length; i++) {
                out.write(",");
                out.write(String.valueOf(array[i]));
            }

            out.write("]");
        }
    }

    public static String toJSONString(byte[] array) {
        final StringWriter writer = new StringWriter();

        try {
            writeJSONString(array, writer);
            return writer.toString();
        } catch (IOException e) {
            // This should never happen for a StringWriter
            throw new RuntimeException(e);
        }
    }

    public static void writeJSONString(short[] array, Writer out) throws IOException {
        if (array == null) {
            out.write("null");
        } else if (array.length == 0) {
            out.write("[]");
        } else {
            out.write("[");
            out.write(String.valueOf(array[0]));

            for (int i = 1; i < array.length; i++) {
                out.write(",");
                out.write(String.valueOf(array[i]));
            }

            out.write("]");
        }
    }

    public static String toJSONString(short[] array) {
        final StringWriter writer = new StringWriter();

        try {
            writeJSONString(array, writer);
            return writer.toString();
        } catch (IOException e) {
            // This should never happen for a StringWriter
            throw new RuntimeException(e);
        }
    }

    public static void writeJSONString(int[] array, Writer out) throws IOException {
        if (array == null) {
            out.write("null");
        } else if (array.length == 0) {
            out.write("[]");
        } else {
            out.write("[");
            out.write(String.valueOf(array[0]));

            for (int i = 1; i < array.length; i++) {
                out.write(",");
                out.write(String.valueOf(array[i]));
            }

            out.write("]");
        }
    }

    public static String toJSONString(int[] array) {
        final StringWriter writer = new StringWriter();

        try {
            writeJSONString(array, writer);
            return writer.toString();
        } catch (IOException e) {
            // This should never happen for a StringWriter
            throw new RuntimeException(e);
        }
    }

    public static void writeJSONString(long[] array, Writer out) throws IOException {
        if (array == null) {
            out.write("null");
        } else if (array.length == 0) {
            out.write("[]");
        } else {
            out.write("[");
            out.write(String.valueOf(array[0]));

            for (int i = 1; i < array.length; i++) {
                out.write(",");
                out.write(String.valueOf(array[i]));
            }

            out.write("]");
        }
    }

    public static String toJSONString(long[] array) {
        final StringWriter writer = new StringWriter();

        try {
            writeJSONString(array, writer);
            return writer.toString();
        } catch (IOException e) {
            // This should never happen for a StringWriter
            throw new RuntimeException(e);
        }
    }

    public static void writeJSONString(float[] array, Writer out) throws IOException {
        if (array == null) {
            out.write("null");
        } else if (array.length == 0) {
            out.write("[]");
        } else {
            out.write("[");
            out.write(String.valueOf(array[0]));

            for (int i = 1; i < array.length; i++) {
                out.write(",");
                out.write(String.valueOf(array[i]));
            }

            out.write("]");
        }
    }

    public static String toJSONString(float[] array) {
        final StringWriter writer = new StringWriter();

        try {
            writeJSONString(array, writer);
            return writer.toString();
        } catch (IOException e) {
            // This should never happen for a StringWriter
            throw new RuntimeException(e);
        }
    }

    public static void writeJSONString(double[] array, Writer out) throws IOException {
        if (array == null) {
            out.write("null");
        } else if (array.length == 0) {
            out.write("[]");
        } else {
            out.write("[");
            out.write(String.valueOf(array[0]));

            for (int i = 1; i < array.length; i++) {
                out.write(",");
                out.write(String.valueOf(array[i]));
            }

            out.write("]");
        }
    }

    public static String toJSONString(double[] array) {
        final StringWriter writer = new StringWriter();

        try {
            writeJSONString(array, writer);
            return writer.toString();
        } catch (IOException e) {
            // This should never happen for a StringWriter
            throw new RuntimeException(e);
        }
    }

    public static void writeJSONString(boolean[] array, Writer out) throws IOException {
        if (array == null) {
            out.write("null");
        } else if (array.length == 0) {
            out.write("[]");
        } else {
            out.write("[");
            out.write(String.valueOf(array[0]));

            for (int i = 1; i < array.length; i++) {
                out.write(",");
                out.write(String.valueOf(array[i]));
            }

            out.write("]");
        }
    }

    public static String toJSONString(boolean[] array) {
        final StringWriter writer = new StringWriter();

        try {
            writeJSONString(array, writer);
            return writer.toString();
        } catch (IOException e) {
            // This should never happen for a StringWriter
            throw new RuntimeException(e);
        }
    }

    public static void writeJSONString(char[] array, Writer out) throws IOException {
        if (array == null) {
            out.write("null");
        } else if (array.length == 0) {
            out.write("[]");
        } else {
            out.write("[\"");
            out.write(String.valueOf(array[0]));

            for (int i = 1; i < array.length; i++) {
                out.write("\",\"");
                out.write(String.valueOf(array[i]));
            }

            out.write("\"]");
        }
    }

    public static String toJSONString(char[] array) {
        final StringWriter writer = new StringWriter();

        try {
            writeJSONString(array, writer);
            return writer.toString();
        } catch (IOException e) {
            // This should never happen for a StringWriter
            throw new RuntimeException(e);
        }
    }

    public static void writeJSONString(Object[] array, Writer out) throws IOException {
        if (array == null) {
            out.write("null");
        } else if (array.length == 0) {
            out.write("[]");
        } else {
            out.write("[");
            JSONValue.writeJSONString(array[0], out);

            for (int i = 1; i < array.length; i++) {
                out.write(",");
                JSONValue.writeJSONString(array[i], out);
            }

            out.write("]");
        }
    }

    public static String toJSONString(Object[] array) {
        final StringWriter writer = new StringWriter();

        try {
            writeJSONString(array, writer);
            return writer.toString();
        } catch (IOException e) {
            // This should never happen for a StringWriter
            throw new RuntimeException(e);
        }
    }

    public void writeJSONString(Writer out) throws IOException {
        writeJSONString(this, out);
    }

    public String toJSONString() {
        return toJSONString(this);
    }

    /**
     * Returns a string representation of this array. This is equivalent to
     * calling {@link JSONArray#toJSONString()}.
     */
    public String toString() {
        return toJSONString();
    }

    public String getString(int index) {
        return this.get(index).toString();
    }

    public boolean getBoolean(int index) {
        return Boolean.parseBoolean(this.get(index).toString());
    }

    public byte getByte(int index) {
        return Byte.parseByte(this.get(index).toString());
    }

    public short getShort(int index) {
        return Short.parseShort(this.get(index).toString());
    }

    public int getInt(int index) {
        return Integer.parseInt(this.get(index).toString());
    }

    public long getLong(int index) {
        return Long.parseLong(this.get(index).toString());
    }

    public float getFloat(int index) {
        return Float.parseFloat(this.get(index).toString());
    }

    public double getDouble(int index) {
        return Double.parseDouble(this.get(index).toString());
    }

    public JSONObject child(int index) {
        return (JSONObject) this.get(index);
    }

    public JSONArray array(int index) {
        return (JSONArray) this.get(index);
    }

}
