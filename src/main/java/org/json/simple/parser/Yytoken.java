/*
 * $Id: Yytoken.java,v 1.1 2006/04/15 14:10:48 platform Exp $
 * Created on 2006-4-15
 */
package org.json.simple.parser;

/**
 * @author FangYidong<fangyidong@yahoo.com.cn>
 * @author l_MrBoom_l<admin@epserv.ru>
 */
public class Yytoken {
    public static final int TYPE_VALUE = 0;//JSON primitive value: string,number,boolean,null
    public static final int TYPE_LEFT_BRACE = 1;
    public static final int TYPE_RIGHT_BRACE = 2;
    public static final int TYPE_LEFT_SQUARE = 3;
    public static final int TYPE_RIGHT_SQUARE = 4;
    public static final int TYPE_COMMA = 5;
    public static final int TYPE_COLON = 6;
    public static final int TYPE_EOF = -1;//end of file

    public int type;
    public Object value;

    public Yytoken(int type, Object value) {
        this.type = type;
        this.value = value;
    }

    public String toString() {
        switch (type) {
            case TYPE_VALUE:
                return "VALUE(" + value + ")";
            case TYPE_LEFT_BRACE:
                return "LEFT BRACE({)";
            case TYPE_RIGHT_BRACE:
                return "RIGHT BRACE(})";
            case TYPE_LEFT_SQUARE:
                return "LEFT SQUARE([)";
            case TYPE_RIGHT_SQUARE:
                return "RIGHT SQUARE(])";
            case TYPE_COMMA:
                return "COMMA(,)";
            case TYPE_COLON:
                return "COLON(:)";
            case TYPE_EOF:
                return "END OF FILE";
        }
        return "";
    }
}
