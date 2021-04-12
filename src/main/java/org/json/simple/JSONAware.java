package org.json.simple;

/**
 * Beans that support customized output of JSON text shall implement this interface.
 *
 * @author FangYidong<fangyidong@yahoo.com.cn>
 * @author l_MrBoom_l<admin@epserv.ru>
 */
public interface JSONAware {
    /**
     * @return JSON text
     */
    String toJSONString();
}
