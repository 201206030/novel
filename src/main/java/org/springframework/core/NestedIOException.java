package org.springframework.core;

import java.io.IOException;

/**
 * 兼容 mybatis-plus 3.5.1
 * mybatis-plus 的 MybatisSqlSessionFactoryBean 中使用到了这个异常
 * Spring 6 开始移除了该异常
 *
 * @author xiongxiaoyang
 * @date 2022/5/12
 */
public class NestedIOException extends IOException {

}
