/*
 * $Id: JSONParser.java,v 1.1 2006/04/15 14:10:48 platform Exp $
 * Created on 2006-4-15
 */
package org.json.simple.parser;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * Parser for JSON text. Please note that JSONParser is NOT thread-safe.
 *
 * @author FangYidong<fangyidong@yahoo.com.cn>
 * @author l_MrBoom_l<admin@epserv.ru>
 */
public class JSONParser {
    public static final int S_INIT = 0;
    public static final int S_IN_FINISHED_VALUE = 1;//string,number,boolean,null,object,array
    public static final int S_IN_OBJECT = 2;
    public static final int S_IN_ARRAY = 3;
    public static final int S_PASSED_PAIR_KEY = 4;
    public static final int S_IN_PAIR_VALUE = 5;
    public static final int S_END = 6;
    public static final int S_IN_ERROR = -1;
    private final Yylex lexer = new Yylex(null);
    private LinkedList<Integer> handlerStatusStack;
    private Yytoken token = null;
    private int status = S_INIT;

    private int peekStatus(@NotNull LinkedList<Integer> statusStack) {
        return statusStack.size() == 0 ? -1 : statusStack.getFirst();
    }

    /**
     * Reset the parser to the initial state without resetting the underlying reader.
     */
    public void reset() {
        token = null;
        status = S_INIT;
        handlerStatusStack = null;
    }

    /**
     * Reset the parser to the initial state with a new character reader.
     *
     * @param in The new character reader.
     */
    public void reset(@Nullable Reader in) {
        lexer.yyreset(in);
        reset();
    }

    /**
     * @return The position of the beginning of the current token.
     */
    public int getPosition() {
        return lexer.getPosition();
    }

    @NotNull
    public JSONArray array(@NotNull String s) throws ParseException {
        return (JSONArray) parse(s);
    }

    @NotNull
    public JSONObject object(@NotNull String s) throws ParseException {
        return (JSONObject) parse(s);
    }

    @NotNull
    public Object parse(@NotNull String s) throws ParseException {
        return parse(s, (ContainerFactory) null);
    }

    @NotNull
    public Object parse(@NotNull String s, @Nullable ContainerFactory containerFactory) throws ParseException {
        StringReader in = new StringReader(s);
        try {
            return parse(in, containerFactory);
        } catch (IOException ie) {
            /*
             * Actually it will never happen.
             */
            throw new ParseException(-1, ParseException.ERROR_UNEXPECTED_EXCEPTION, ie);
        }
    }

    @NotNull
    public Object parse(@NotNull Reader in) throws IOException, ParseException {
        return parse(in, (ContainerFactory) null);
    }

    /**
     * Parse JSON text into java object from the input source.
     *
     * @param in Reader to use
     * @param containerFactory Use this factory to create your own JSON object and JSON array containers.
     * @return Instance of the following:
     * org.json.simple.JSONObject,
     * org.json.simple.JSONArray,
     * java.lang.String,
     * java.lang.Number,
     * java.lang.Boolean,
     * null
     */
    @SuppressWarnings("unchecked")
    @NotNull
    public Object parse(@NotNull Reader in, @Nullable ContainerFactory containerFactory) throws IOException, ParseException {
        reset(in);
        LinkedList<Integer> statusStack = new LinkedList<>();
        LinkedList<Object> valueStack = new LinkedList<>();

        do {
            nextToken();
            switch (status) {
                case S_INIT:
                    switch (token.type) {
                        case Yytoken.TYPE_VALUE -> {
                            status = S_IN_FINISHED_VALUE;
                            statusStack.addFirst(status);
                            valueStack.addFirst(token.value);
                        }
                        case Yytoken.TYPE_LEFT_BRACE -> {
                            status = S_IN_OBJECT;
                            statusStack.addFirst(status);
                            valueStack.addFirst(createObjectContainer(containerFactory));
                        }
                        case Yytoken.TYPE_LEFT_SQUARE -> {
                            status = S_IN_ARRAY;
                            statusStack.addFirst(status);
                            valueStack.addFirst(createArrayContainer(containerFactory));
                        }
                        default -> status = S_IN_ERROR;
                    }//inner switch
                    break;

                case S_IN_FINISHED_VALUE:
                    if (token.type == Yytoken.TYPE_EOF)
                        return valueStack.removeFirst();
                    else
                        throw new ParseException(getPosition(), ParseException.ERROR_UNEXPECTED_TOKEN, token);
                case S_IN_OBJECT:
                    switch (token.type) {
                        case Yytoken.TYPE_COMMA:
                            break;
                        case Yytoken.TYPE_VALUE:
                            if (token.value instanceof String key) {
                                valueStack.addFirst(key);
                                status = S_PASSED_PAIR_KEY;
                                statusStack.addFirst(status);
                            } else {
                                status = S_IN_ERROR;
                            }
                            break;
                        case Yytoken.TYPE_RIGHT_BRACE:
                            if (valueStack.size() > 1) {
                                statusStack.removeFirst();
                                valueStack.removeFirst();
                                status = peekStatus(statusStack);
                            } else {
                                status = S_IN_FINISHED_VALUE;
                            }
                            break;
                        default:
                            status = S_IN_ERROR;
                            break;
                    }//inner switch
                    break;

                case S_PASSED_PAIR_KEY:
                    switch (token.type) {
                        case Yytoken.TYPE_COLON:
                            break;
                        case Yytoken.TYPE_VALUE:
                            statusStack.removeFirst();
                            String key = (String) valueStack.removeFirst();
                            Map<String, Object> parent = (Map<String, Object>) valueStack.getFirst();
                            parent.put(key, token.value);
                            status = peekStatus(statusStack);
                            break;
                        case Yytoken.TYPE_LEFT_SQUARE:
                            statusStack.removeFirst();
                            key = (String) valueStack.removeFirst();
                            parent = (Map<String, Object>) valueStack.getFirst();
                            List<?> newArray = createArrayContainer(containerFactory);
                            parent.put(key, newArray);
                            status = S_IN_ARRAY;
                            statusStack.addFirst(status);
                            valueStack.addFirst(newArray);
                            break;
                        case Yytoken.TYPE_LEFT_BRACE:
                            statusStack.removeFirst();
                            key = (String) valueStack.removeFirst();
                            parent = (Map<String, Object>) valueStack.getFirst();
                            Map<?, ?> newObject = createObjectContainer(containerFactory);
                            parent.put(key, newObject);
                            status = S_IN_OBJECT;
                            statusStack.addFirst(status);
                            valueStack.addFirst(newObject);
                            break;
                        default:
                            status = S_IN_ERROR;
                    }
                    break;

                case S_IN_ARRAY:
                    switch (token.type) {
                        case Yytoken.TYPE_COMMA:
                            break;
                        case Yytoken.TYPE_VALUE:
                            List<Object> val = (List<Object>) valueStack.getFirst();
                            val.add(token.value);
                            break;
                        case Yytoken.TYPE_RIGHT_SQUARE:
                            if (valueStack.size() > 1) {
                                statusStack.removeFirst();
                                valueStack.removeFirst();
                                status = peekStatus(statusStack);
                            } else {
                                status = S_IN_FINISHED_VALUE;
                            }
                            break;
                        case Yytoken.TYPE_LEFT_BRACE:
                            val = (List<Object>) valueStack.getFirst();
                            Map<?, ?> newObject = createObjectContainer(containerFactory);
                            val.add(newObject);
                            status = S_IN_OBJECT;
                            statusStack.addFirst(status);
                            valueStack.addFirst(newObject);
                            break;
                        case Yytoken.TYPE_LEFT_SQUARE:
                            val = (List<Object>) valueStack.getFirst();
                            List<?> newArray = createArrayContainer(containerFactory);
                            val.add(newArray);
                            status = S_IN_ARRAY;
                            statusStack.addFirst(status);
                            valueStack.addFirst(newArray);
                            break;
                        default:
                            status = S_IN_ERROR;
                    }//inner switch
                    break;
                case S_IN_ERROR:
                    throw new ParseException(getPosition(), ParseException.ERROR_UNEXPECTED_TOKEN, token);
            }//switch
            if (status == S_IN_ERROR) {
                throw new ParseException(getPosition(), ParseException.ERROR_UNEXPECTED_TOKEN, token);
            }
        } while (token.type != Yytoken.TYPE_EOF);

        throw new ParseException(getPosition(), ParseException.ERROR_UNEXPECTED_TOKEN, token);
    }

    private void nextToken() throws IOException, ParseException {
        token = lexer.yylex();
        if (token == null)
            token = new Yytoken(Yytoken.TYPE_EOF, null);
    }

    @NotNull
    private Map<Object, Object> createObjectContainer(@Nullable ContainerFactory containerFactory) {
        if (containerFactory == null)
            return new JSONObject();
        Map<Object, Object> m = containerFactory.createObjectContainer();

        return m == null ? new JSONObject() : m;
    }

    @NotNull
    private List<?> createArrayContainer(@Nullable ContainerFactory containerFactory) {
        if (containerFactory == null)
            return new JSONArray();
        List<?> l = containerFactory.createArrayContainer();

        return l == null ? new JSONArray() : l;
    }

    public void parse(@NotNull String s, @NotNull ContentHandler contentHandler) throws ParseException {
        parse(s, contentHandler, false);
    }

    public void parse(@NotNull String s, @NotNull ContentHandler contentHandler, boolean isResume) throws ParseException {
        StringReader in = new StringReader(s);
        try {
            parse(in, contentHandler, isResume);
        } catch (IOException ie) {
            /*
             * Actually it will never happen.
             */
            throw new ParseException(-1, ParseException.ERROR_UNEXPECTED_EXCEPTION, ie);
        }
    }

    public void parse(@NotNull Reader in, @NotNull ContentHandler contentHandler) throws IOException, ParseException {
        parse(in, contentHandler, false);
    }

    /**
     * Stream processing of JSON text.
     *
     * @param in Reader to use
     * @param contentHandler {@link ContentHandler} to use
     * @param isResume Indicates if it continues previous parsing operation. If set to true, resume parsing the old
     *                 stream, and parameter 'in' will be ignored. If this method is called for the first time in this
     *                 instance, isResume will be ignored.
     * @see ContentHandler
     */
    public void parse(@NotNull Reader in, @NotNull ContentHandler contentHandler, boolean isResume) throws IOException, ParseException {
        if (!isResume) {
            reset(in);
            handlerStatusStack = new LinkedList<>();
        } else {
            if (handlerStatusStack == null) {
                reset(in);
                handlerStatusStack = new LinkedList<>();
            }
        }

        LinkedList<Integer> statusStack = handlerStatusStack;

        try {
            do {
                switch (status) {
                    case S_INIT:
                        contentHandler.startJSON();
                        nextToken();
                        switch (token.type) {
                            case Yytoken.TYPE_VALUE -> {
                                status = S_IN_FINISHED_VALUE;
                                statusStack.addFirst(status);
                                if (!contentHandler.primitive(token.value))
                                    return;
                            }
                            case Yytoken.TYPE_LEFT_BRACE -> {
                                status = S_IN_OBJECT;
                                statusStack.addFirst(status);
                                if (!contentHandler.startObject())
                                    return;
                            }
                            case Yytoken.TYPE_LEFT_SQUARE -> {
                                status = S_IN_ARRAY;
                                statusStack.addFirst(status);
                                if (!contentHandler.startArray())
                                    return;
                            }
                            default -> status = S_IN_ERROR;
                        }//inner switch
                        break;

                    case S_IN_FINISHED_VALUE:
                        nextToken();
                        if (token.type == Yytoken.TYPE_EOF) {
                            contentHandler.endJSON();
                            status = S_END;
                            return;
                        } else {
                            status = S_IN_ERROR;
                            throw new ParseException(getPosition(), ParseException.ERROR_UNEXPECTED_TOKEN, token);
                        }

                    case S_IN_OBJECT:
                        nextToken();
                        switch (token.type) {
                            case Yytoken.TYPE_COMMA:
                                break;
                            case Yytoken.TYPE_VALUE:
                                if (token.value instanceof String key) {
                                    status = S_PASSED_PAIR_KEY;
                                    statusStack.addFirst(status);
                                    if (!contentHandler.startObjectEntry(key))
                                        return;
                                } else {
                                    status = S_IN_ERROR;
                                }
                                break;
                            case Yytoken.TYPE_RIGHT_BRACE:
                                if (statusStack.size() > 1) {
                                    statusStack.removeFirst();
                                    status = peekStatus(statusStack);
                                } else {
                                    status = S_IN_FINISHED_VALUE;
                                }
                                if (!contentHandler.endObject())
                                    return;
                                break;
                            default:
                                status = S_IN_ERROR;
                                break;
                        }//inner switch
                        break;

                    case S_PASSED_PAIR_KEY:
                        nextToken();
                        switch (token.type) {
                            case Yytoken.TYPE_COLON:
                                break;
                            case Yytoken.TYPE_VALUE:
                                statusStack.removeFirst();
                                status = peekStatus(statusStack);
                                if (!contentHandler.primitive(token.value))
                                    return;
                                if (!contentHandler.endObjectEntry())
                                    return;
                                break;
                            case Yytoken.TYPE_LEFT_SQUARE:
                                statusStack.removeFirst();
                                statusStack.addFirst(S_IN_PAIR_VALUE);
                                status = S_IN_ARRAY;
                                statusStack.addFirst(status);
                                if (!contentHandler.startArray())
                                    return;
                                break;
                            case Yytoken.TYPE_LEFT_BRACE:
                                statusStack.removeFirst();
                                statusStack.addFirst(S_IN_PAIR_VALUE);
                                status = S_IN_OBJECT;
                                statusStack.addFirst(status);
                                if (!contentHandler.startObject())
                                    return;
                                break;
                            default:
                                status = S_IN_ERROR;
                        }
                        break;

                    case S_IN_PAIR_VALUE:
                        /*
                         * S_IN_PAIR_VALUE is just a marker to indicate the end of an object entry, it doesn't process any token,
                         * therefore delay consuming token until next round.
                         */
                        statusStack.removeFirst();
                        status = peekStatus(statusStack);
                        if (!contentHandler.endObjectEntry())
                            return;
                        break;

                    case S_IN_ARRAY:
                        nextToken();
                        switch (token.type) {
                            case Yytoken.TYPE_COMMA:
                                break;
                            case Yytoken.TYPE_VALUE:
                                if (!contentHandler.primitive(token.value))
                                    return;
                                break;
                            case Yytoken.TYPE_RIGHT_SQUARE:
                                if (statusStack.size() > 1) {
                                    statusStack.removeFirst();
                                    status = peekStatus(statusStack);
                                } else {
                                    status = S_IN_FINISHED_VALUE;
                                }
                                if (!contentHandler.endArray())
                                    return;
                                break;
                            case Yytoken.TYPE_LEFT_BRACE:
                                status = S_IN_OBJECT;
                                statusStack.addFirst(status);
                                if (!contentHandler.startObject())
                                    return;
                                break;
                            case Yytoken.TYPE_LEFT_SQUARE:
                                status = S_IN_ARRAY;
                                statusStack.addFirst(status);
                                if (!contentHandler.startArray())
                                    return;
                                break;
                            default:
                                status = S_IN_ERROR;
                        }//inner switch
                        break;

                    case S_END:
                        return;

                    case S_IN_ERROR:
                        throw new ParseException(getPosition(), ParseException.ERROR_UNEXPECTED_TOKEN, token);
                }//switch
                if (status == S_IN_ERROR) {
                    throw new ParseException(getPosition(), ParseException.ERROR_UNEXPECTED_TOKEN, token);
                }
            } while (token.type != Yytoken.TYPE_EOF);
        } catch (Throwable t) {
            status = S_IN_ERROR;
            throw t;
        }

        status = S_IN_ERROR;
        throw new ParseException(getPosition(), ParseException.ERROR_UNEXPECTED_TOKEN, token);
    }
}
