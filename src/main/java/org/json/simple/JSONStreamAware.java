package org.json.simple;

import java.io.IOException;
import java.io.Writer;

/**
 * Beans that support customized output of JSON text to a writer shall implement this interface.
 *
 * @author FangYidong<fangyidong@yahoo.com.cn>
 * @author l_MrBoom_l<admin@epserv.ru>
 */
public interface JSONStreamAware {
    /**
     * write JSON string to out.
     */
    void writeJSONString(Writer out) throws IOException;
}
