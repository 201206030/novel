package io.github.xxyopen.novel.manager.redis;

import io.github.xxyopen.novel.core.constant.CacheConsts;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.HexFormat;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

/**
 * 小说访问量去重管理类
 */
@Component
@RequiredArgsConstructor
public class BookVisitDedupManager {

    private static final String VISIT_DEDUP_KEY_PREFIX = CacheConsts.REDIS_CACHE_PREFIX
        + "bookVisitDedupCache:";

    private static final Duration VISIT_DEDUP_TTL = Duration.ofHours(24);

    private static final DefaultRedisScript<Long> REMOVE_IF_VALUE_MATCHES_SCRIPT =
        new DefaultRedisScript<>(
            "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end",
            Long.class);

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 为同一本书的访客原子预留一次 24 小时内的计数机会。
     *
     * @return 预留标记；若访客已计数则返回 null
     */
    public String reserveVisit(Long bookId, String visitorId) {
        String key = buildKey(bookId, visitorId);
        String reservation = UUID.randomUUID().toString();
        Boolean reserved = stringRedisTemplate.opsForValue()
            .setIfAbsent(key, reservation, VISIT_DEDUP_TTL);
        return Boolean.TRUE.equals(reserved) ? reservation : null;
    }

    /**
     * 数据库更新失败时，只删除当前请求创建的预留标记。
     */
    public void releaseVisit(Long bookId, String visitorId, String reservation) {
        stringRedisTemplate.execute(REMOVE_IF_VALUE_MATCHES_SCRIPT,
            List.of(buildKey(bookId, visitorId)), reservation);
    }

    private String buildKey(Long bookId, String visitorId) {
        return VISIT_DEDUP_KEY_PREFIX + bookId + ":" + sha256(visitorId);
    }

    private String sha256(String value) {
        try {
            byte[] digest = MessageDigest.getInstance("SHA-256")
                .digest(value.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(digest);
        } catch (NoSuchAlgorithmException exception) {
            throw new IllegalStateException("SHA-256 is unavailable", exception);
        }
    }

}
