package io.github.xxyopen.novel.core.common.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link SortWhitelistUtil} 排序白名单校验单元测试
 *
 * @author cgrs5722
 * @date 2026/10/20
 */
class SortWhitelistUtilTest {

    /**
     * 白名单内的排序字段应返回其小写形式
     */
    @Test
    void sanitizeColumnShouldReturnLowercaseWhenInWhitelist() {
        assertThat(SortWhitelistUtil.sanitizeColumn("word_count")).isEqualTo("word_count");
        assertThat(SortWhitelistUtil.sanitizeColumn("LAST_CHAPTER_UPDATE_TIME")).isEqualTo("last_chapter_update_time");
    }

    /**
     * 白名单外的排序字段（含 SQL 注入尝试与空串）应回退为默认字段 id
     */
    @Test
    void sanitizeColumnShouldFallbackToIdWhenNotInWhitelist() {
        assertThat(SortWhitelistUtil.sanitizeColumn("create_time")).isEqualTo("id");
        assertThat(SortWhitelistUtil.sanitizeColumn("id; drop table book")).isEqualTo("id");
        assertThat(SortWhitelistUtil.sanitizeColumn("")).isEqualTo("id");
    }

    /**
     * 合法排序方式应返回其小写形式
     */
    @Test
    void sanitizeOrderShouldReturnLowercaseWhenValid() {
        assertThat(SortWhitelistUtil.sanitizeOrder("asc")).isEqualTo("asc");
        assertThat(SortWhitelistUtil.sanitizeOrder("DESC")).isEqualTo("desc");
    }

    /**
     * 非法排序方式（含 SQL 注入尝试与空串）应回退为默认排序方式 asc
     */
    @Test
    void sanitizeOrderShouldFallbackToAscWhenInvalid() {
        assertThat(SortWhitelistUtil.sanitizeOrder("random")).isEqualTo("asc");
        assertThat(SortWhitelistUtil.sanitizeOrder("asc; drop table book")).isEqualTo("asc");
        assertThat(SortWhitelistUtil.sanitizeOrder("")).isEqualTo("asc");
    }
}
