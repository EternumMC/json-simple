/*
 * $Id: JSONArray.java,v 1.1 2006/04/15 14:10:48 platform Exp $
 * Created on 2006-4-10
 */
package org.json.simple;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import org.jetbrains.annotations.UnmodifiableView;

import java.io.IOException;
import java.io.Serial;
import java.io.StringWriter;
import java.io.Writer;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;

/**
 * A JSON array. JSONObject supports java.util.List interface.
 *
 * @author FangYidong<fangyidong@yahoo.com.cn>
 * @author l_MrBoom_l<admin@epserv.ru>
 */
public class JSONArray extends ArrayList<Object> implements JSONAware, JSONStreamAware {
    @Serial
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
    public JSONArray(@NotNull Collection<?> c) {
        super(c);
    }

    /**
     * Constructs a JSONArray containing the specified elements.
     *
     * @param elements the elements are to be placed into this JSONArray
     */
    public JSONArray(@NotNull Object... elements) {
        Collections.addAll(this, elements);
    }

    /**
     * Encode a list into JSON text and write it to out.
     * If this list is also a JSONStreamAware or a JSONAware, JSONStreamAware and JSONAware specific behaviours will be ignored at this top level.
     *
     * @param collection Collection to use as input
     * @param out        Writer to use as output
     * @see JSONValue#writeJSONString(Object, Writer)
     */
    public static void writeJSONString(@Nullable Collection<?> collection, @NotNull Writer out) throws IOException {
        if (collection == null) {
            out.write("null");
            return;
        }

        boolean first = true;
        Iterator<?> iterator = collection.iterator();

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
     * @see JSONValue#toJSONString(Object)
     */
    @NotNull
    public static String toJSONString(@Nullable Collection<Object> collection) {
        final StringWriter writer = new StringWriter();

        try {
            writeJSONString(collection, writer);
            return writer.toString();
        } catch (IOException e) {
            // This should never happen for a StringWriter
            throw new RuntimeException(e);
        }
    }

    public static void writeJSONString(byte[] array, @NotNull Writer out) throws IOException {
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

    @NotNull
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

    public static void writeJSONString(short[] array, @NotNull Writer out) throws IOException {
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

    @NotNull
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

    public static void writeJSONString(int[] array, @NotNull Writer out) throws IOException {
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

    @NotNull
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

    public static void writeJSONString(long[] array, @NotNull Writer out) throws IOException {
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

    @NotNull
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

    public static void writeJSONString(float[] array, @NotNull Writer out) throws IOException {
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

    @NotNull
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

    public static void writeJSONString(double[] array, @NotNull Writer out) throws IOException {
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

    @NotNull
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

    public static void writeJSONString(boolean[] array, @NotNull Writer out) throws IOException {
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

    @NotNull
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

    public static void writeJSONString(char[] array, @NotNull Writer out) throws IOException {
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

    @NotNull
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

    public static void writeJSONString(@Nullable Object[] array, @NotNull Writer out) throws IOException {
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

    @NotNull
    public static String toJSONString(@Nullable Object[] array) {
        final StringWriter writer = new StringWriter();

        try {
            writeJSONString(array, writer);
            return writer.toString();
        } catch (IOException e) {
            // This should never happen for a StringWriter
            throw new RuntimeException(e);
        }
    }

    public void writeJSONString(@NotNull Writer out) throws IOException {
        writeJSONString(this, out);
    }

    @NotNull
    public String toJSONString() {
        return toJSONString(this);
    }

    /**
     * Returns a string representation of this array. This is equivalent to
     * calling {@link org.json.simple.JSONArray#toJSONString()}.
     */
    @NotNull
    public String toString() {
        return toJSONString();
    }

    @Nullable
    public String getString(int index) {
        return String.valueOf(this.get(index));
    }

    public boolean getBoolean(int index) {
        return Boolean.parseBoolean(this.getString(index));
    }

    public byte getByte(int index) {
        return Byte.parseByte(Objects.requireNonNullElse(this.getString(index), "null"));
    }

    public short getShort(int index) {
        return Short.parseShort(Objects.requireNonNullElse(this.getString(index), "null"));
    }

    public int getInt(int index) {
        return Integer.parseInt(Objects.requireNonNullElse(this.getString(index), "null"));
    }

    public long getLong(int index) {
        return Long.parseLong(Objects.requireNonNullElse(this.getString(index), "null"));
    }

    public float getFloat(int index) {
        return Float.parseFloat(Objects.requireNonNullElse(this.getString(index), "null"));
    }

    public double getDouble(int index) {
        return Double.parseDouble(Objects.requireNonNullElse(this.getString(index), "null"));
    }

    @Nullable
    public JSONObject child(int index) {
        return (JSONObject) this.get(index);
    }

    @Nullable
    public JSONArray array(int index) {
        return (JSONArray) this.get(index);
    }

    @Unmodifiable
    public static JSONArray of() {
        return new UnmodifiableJSONArray();
    }

    @UnmodifiableView
    public static JSONArray of(Object... elements) {
        return new UnmodifiableJSONArray(elements);
    }

    private static final class UnmodifiableJSONArray extends JSONArray {

        private UnmodifiableJSONArray(Object... elements) { super(Arrays.asList(elements)); }

        @Override
        public boolean add(Object o) { throw new UnsupportedOperationException(); }
        @Override
        public boolean addAll(Collection<?> c) { throw new UnsupportedOperationException(); }
        @Override
        public void clear() { throw new UnsupportedOperationException(); }
        @Override
        public boolean remove(Object o) { throw new UnsupportedOperationException(); }
        @Override
        public boolean removeAll(Collection<?> c) { throw new UnsupportedOperationException(); }
        @Override
        public boolean removeIf(Predicate<? super Object> filter) { throw new UnsupportedOperationException(); }
        @Override
        public boolean retainAll(Collection<?> c) { throw new UnsupportedOperationException(); }

        @Override
        public void add(int index, Object o) { throw new UnsupportedOperationException(); }
        @Override
        public boolean addAll(int index, Collection<?> c) { throw new UnsupportedOperationException(); }
        @Override
        public Object remove(int index) { throw new UnsupportedOperationException(); }
        @Override
        public void replaceAll(UnaryOperator<Object> operator) { throw new UnsupportedOperationException(); }
        @Override
        public Object set(int index, Object o) {
            throw new UnsupportedOperationException();
        }
        @Override
        public void sort(Comparator<? super Object> c) { throw new UnsupportedOperationException(); }
    }

    public static Collector<Object, JSONArray, JSONArray> collector() {
        return new JSONArrayCollector();
    }

    private static class JSONArrayCollector implements Collector<Object, JSONArray, JSONArray> {
        private static final BinaryOperator<JSONArray> combiner = (left, right) -> {
            if (left.size() < right.size()) {
                right.addAll(left);
                return right;
            } else {
                left.addAll(right);
                return left;
            }
        };
        private static final Set<Characteristics> characteristics = Collections.unmodifiableSet(EnumSet.of(Collector.Characteristics.UNORDERED));

        @Override
        public Supplier<JSONArray> supplier() {
            return JSONArray::new;
        }

        @Override
        public BiConsumer<JSONArray, Object> accumulator() {
            return JSONArray::add;
        }

        @Override
        public BinaryOperator<JSONArray> combiner() {
            return combiner;
        }

        @Override
        public Function<JSONArray, JSONArray> finisher() {
            return JSONArray.class::cast;
        }

        @Override
        public Set<Characteristics> characteristics() {
            return characteristics;
        }
    }

}
