package io.github.xxyopen.novel.service;

import java.io.IOException;

/**
 * 文件相关服务类
 *
 * @author xiongxiaoyang
 * @date 2022/5/17
 */
public interface FileService {

    /**
     * 获取图片验证码
     * @param userKey 请求用户的标识，表明该验证码属于谁
     * @return Base64编码的图片
     * */
    String getImgVerifyCode(String userKey) throws IOException;
}
