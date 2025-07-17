package io.github.xxyopen.novel.core.common.util;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 排序字段和排序方式的安全校验工具类
 * <p>
 * 用于对请求参数中的排序字段（sort）和排序方式（order）进行白名单校验，
 * 防止 SQL 注入攻击，确保传入的字段名和排序方式是系统允许的合法值。
 * <p>
 * 该工具类使用 Lombok 的 @UtilityClass 注解，确保：
 * - 无法被实例化
 * - 所有方法为静态方法
 *
 * @author xiongxiaoyang
 * @date 2025/7/17
 */
@UtilityClass
public class SortWhitelistUtil {

    /**
     * 允许的排序字段白名单集合
     * 包含系统中允许作为排序依据的数据库字段名。
     */
    private final Set<String> allowedColumns = new HashSet<>(
        Arrays.asList( "last_chapter_update_time", "word_count", "visit_count"));

    /**
     * 允许的排序方式白名单集合
     * 支持升序（asc）和降序（desc）两种排序方式。
     */
    private final Set<String> allowedOrders = new HashSet<>(Arrays.asList("asc", "desc"));

    /**
     * 校验并清理排序字段
     * <p>
     * 如果输入的字段在白名单中，则返回小写形式；
     * 否则返回默认字段 "id"。
     *
     * @param input 用户输入的排序字段
     * @return 安全的排序字段名
     */
    public String sanitizeColumn(String input) {
        return allowedColumns.contains(input.toLowerCase()) ? input.toLowerCase() : "id";
    }

    /**
     * 校验并清理排序方式
     * <p>
     * 如果输入的排序方式是 "asc" 或 "desc"，则返回其小写形式；
     * 否则返回默认排序方式 "asc"。
     *
     * @param input 用户输入的排序方式
     * @return 安全的排序方式（asc 或 desc）
     */
    public String sanitizeOrder(String input) {
        return allowedOrders.contains(input.toLowerCase()) ? input.toLowerCase() : "asc";
    }
}