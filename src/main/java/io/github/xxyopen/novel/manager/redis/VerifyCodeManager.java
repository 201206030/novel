package io.github.xxyopen.novel.manager.redis;

import io.github.xxyopen.novel.core.common.util.ImgVerifyCodeUtils;
import io.github.xxyopen.novel.core.constant.CacheConsts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;
import java.util.Objects;

/**
 * 验证码 管理类
 *
 * @author xiongxiaoyang
 * @date 2022/5/12
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class VerifyCodeManager {

    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 生成图片验证码，并放入缓存中
     */
    public String genImgVerifyCode(String sessionId) throws IOException {
        String verifyCode = ImgVerifyCodeUtils.getRandomVerifyCode(4);
        String img = ImgVerifyCodeUtils.genVerifyCodeImg(verifyCode);
        stringRedisTemplate.opsForValue().set(CacheConsts.IMG_VERIFY_CODE_CACHE_KEY + sessionId,
                verifyCode, Duration.ofMinutes(5));
        return img;
    }

    /**
     * 校验图片验证码
     */
    public boolean imgVerifyCodeOk(String sessionId, String verifyCode) {
        return Objects.equals(stringRedisTemplate.opsForValue().get(CacheConsts.IMG_VERIFY_CODE_CACHE_KEY + sessionId), verifyCode);
    }

    /**
     * 删除验证码
     */
    public void removeImgVerifyCode(String sessionId) {
        stringRedisTemplate.delete(CacheConsts.IMG_VERIFY_CODE_CACHE_KEY + sessionId);
    }

}
