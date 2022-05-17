package io.github.xxyopen.novel.core.wrapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * XSS 过滤处理
 *
 * @author xiongxiaoyang
 * @date 2022/5/17
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    private final Map<String,String> replaceRule = new HashMap<>();

    public XssHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
        replaceRule.put("<", "&lt;");
        replaceRule.put(">", "&gt;");
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] values = super.getParameterValues(name);
        if (values != null) {
            int length = values.length;
            String[] escapeValues = new String[length];
            for (int i = 0; i < length; i++) {
                String raw = values[i];
                int index = i;
                replaceRule.forEach((k, v)-> escapeValues[index] = raw.replaceAll(k, v));
            }
            return escapeValues;
        }
        return new String[0];
    }
}
