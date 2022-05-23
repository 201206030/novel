package io.github.xxyopen.novel.core.constant;

import lombok.Getter;

/**
 * elasticsearch 相关常量
 *
 * @author xiongxiaoyang
 * @date 2022/5/23
 */
public class EsConsts {

    /**
     * ES 索引枚举类
     */
    @Getter
    public enum IndexEnum {

        BOOK("book");

        IndexEnum(String name) {
            this.name = name;
        }

        private String name;

    }
}
