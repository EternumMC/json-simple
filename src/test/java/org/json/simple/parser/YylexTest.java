package org.json.simple.parser;

import junit.framework.TestCase;

import java.io.StringReader;

public class YylexTest extends TestCase {

    public void testYylex() throws Exception {
        String s = "\"\\/\"";
        System.out.println(s);
        StringReader in = new StringReader(s);
        Yylex lexer = new Yylex(in);
        Yytoken token = lexer.yylex();
        assertEquals(Yytoken.TYPE_VALUE, token.type);
        assertEquals("/", token.value);

        s = "\"abc\\/\\r\\b\\n\\t\\f\\\\\"";
        System.out.println(s);
        in = new StringReader(s);
        lexer = new Yylex(in);
        token = lexer.yylex();
        assertEquals(Yytoken.TYPE_VALUE, token.type);
        assertEquals("abc/\r\b\n\t\f\\", token.value);

        s = "[\t \n\r\n{ \t \t\n\r}";
        System.out.println(s);
        in = new StringReader(s);
        lexer = new Yylex(in);
        token = lexer.yylex();
        assertEquals(Yytoken.TYPE_LEFT_SQUARE, token.type);
        token = lexer.yylex();
        assertEquals(Yytoken.TYPE_LEFT_BRACE, token.type);
        token = lexer.yylex();
        assertEquals(Yytoken.TYPE_RIGHT_BRACE, token.type);

        s = "\b\f{";
        System.out.println(s);
        in = new StringReader(s);
        lexer = new Yylex(in);
        ParseException err = null;
        try {
            lexer.yylex();
        } catch (ParseException e) {
            err = e;
            System.out.println("error:" + err);
            assertEquals(ParseException.ERROR_UNEXPECTED_CHAR, e.getErrorType());
            assertEquals(0, e.getPosition());
            assertEquals('\b', e.getUnexpectedObject());
        }
        assertNotNull(err);

        s = "{a : b}";
        System.out.println(s);
        in = new StringReader(s);
        lexer = new Yylex(in);
        err = null;
        try {
            lexer.yylex();
            lexer.yylex();
        } catch (ParseException e) {
            err = e;
            System.out.println("error:" + err);
            assertEquals(ParseException.ERROR_UNEXPECTED_CHAR, e.getErrorType());
            assertEquals('a', e.getUnexpectedObject());
            assertEquals(1, e.getPosition());
        }
        assertNotNull(err);
    }

}
