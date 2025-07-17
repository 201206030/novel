package io.github.xxyopen.novel.core.annotation;

import java.lang.annotation.*;

/**
 * 自定义注解，用于标记需要进行排序字段（sort）和排序方式（order）校验的方法参数。
 * <p>
 * 在接收到请求参数时，可通过 AOP 对标注了该注解的参数进行统一处理， 校验并防止 SQL 注入等安全问题。
 *
 * @author xiongxiaoyang
 * @date 2025/7/17
 */
@Target(ElementType.PARAMETER) // 表示该注解只能用于方法参数上
@Retention(RetentionPolicy.RUNTIME) // 注解在运行时依然可用，便于 AOP 或其他框架读取
@Documented // 该注解将被包含在生成的 Javadoc 中
public @interface ValidateSortOrder {

}
