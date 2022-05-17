package io.github.xxyopen.novel.core.common.util;

import lombok.experimental.UtilityClass;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;

/**
 * 图片验证码工具类
 *
 * @author xiongxiaoyang
 * @date 2022/5/17
 */
@UtilityClass
public class ImgVerifyCodeUtils {

    /**
     * 随机产生只有数字的字符串
     */
    private final String randNumber = "0123456789";

    /**
     * 图片宽
     */
    private final int width = 100;

    /**
     * 图片高
     */
    private final int height = 38;

    private final Random random = new Random();

    /**
     * 获得字体
     */
    private Font getFont() {
        return new Font("Fixed", Font.PLAIN, 23);
    }


    /**
     * 生成校验码图片
     */
    public String genVerifyCodeImg(String verifyCode) throws IOException {
        // BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        // 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
        Graphics g = image.getGraphics();
        //图片大小
        g.fillRect(0, 0, width, height);
        //字体大小
        //字体颜色
        g.setColor(new Color(204, 204, 204));
        // 绘制干扰线
        // 干扰线数量
        int lineSize = 40;
        for (int i = 0; i <= lineSize; i++) {
            drawLine(g);
        }
        // 绘制随机字符
        drawString(g, verifyCode);
        g.dispose();
        //将图片转换成Base64字符串
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(image, "JPEG", stream);
        return Base64.getEncoder().encodeToString(stream.toByteArray());
    }

    /**
     * 绘制字符串
     */
    private void drawString(Graphics g, String verifyCode) {
        for (int i = 1; i <= verifyCode.length(); i++) {
            g.setFont(getFont());
            g.setColor(new Color(random.nextInt(101), random.nextInt(111), random
                    .nextInt(121)));
            g.translate(random.nextInt(3), random.nextInt(3));
            g.drawString(String.valueOf(verifyCode.charAt(i - 1)), 13 * i, 23);
        }
    }

    /**
     * 绘制干扰线
     */
    private void drawLine(Graphics g) {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(13);
        int yl = random.nextInt(15);
        g.drawLine(x, y, x + xl, y + yl);
    }

    /**
     * 获取随机的校验码
     */
    public String getRandomVerifyCode(int num) {
        int randNumberSize = randNumber.length();
        StringBuilder verifyCode = new StringBuilder();
        for (int i = 0; i < num; i++) {
            String rand = String.valueOf(randNumber.charAt(random.nextInt(randNumberSize)));
            verifyCode.append(rand);
        }
        return verifyCode.toString();
    }

}
