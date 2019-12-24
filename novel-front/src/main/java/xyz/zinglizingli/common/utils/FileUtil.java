package xyz.zinglizingli.common.utils;

import org.apache.commons.codec.Charsets;
import org.apache.http.client.utils.DateUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.io.*;
import java.util.Date;
import java.util.Objects;

/**
 * 文件操作工具类
 * @author 11797
 */
public class FileUtil {

    /**
     * 网络图片转本地
     * */
    public static String network2Local(String picSrc, String picSavePath) throws IOException {
        //本地图片保存
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        ResponseEntity<Resource> resEntity = RestTemplateUtil.getInstance(Charsets.ISO_8859_1).exchange(picSrc, HttpMethod.GET, requestEntity, Resource.class);
        InputStream input = Objects.requireNonNull(resEntity.getBody()).getInputStream();
        Date currentDate = new Date();
        picSrc = "/localPic/" + DateUtils.formatDate(currentDate, "yyyy") + "/" + DateUtils.formatDate(currentDate, "MM") + "/" + DateUtils.formatDate(currentDate, "dd")
                + UUIDUtils.getUUID32()
                + picSrc.substring(picSrc.lastIndexOf("."));
        File picFile = new File(picSavePath + picSrc);
        File parentFile = picFile.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        OutputStream out = new FileOutputStream(picFile);
        byte[] b = new byte[4096];
        for (int n; (n = input.read(b)) != -1; ) {
            out.write(b, 0, n);
        }
        out.close();
        input.close();
        return picSrc;
    }


}
