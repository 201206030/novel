package io.github.xxyopen.novel.service.impl;

import io.github.xxyopen.novel.manager.VerifyCodeManager;
import io.github.xxyopen.novel.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 文件相关服务实现类
 *
 * @author xiongxiaoyang
 * @date 2022/5/17
 */
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final VerifyCodeManager verifyCodeManager;

    @Override
    public String getImgVerifyCode(String userKey) throws IOException {
        return verifyCodeManager.genImgVerifyCode(userKey);
    }
}
