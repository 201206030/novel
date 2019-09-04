package xyz.zinglizingli.books.po;

import java.util.Date;

public class ScreenBullet {
    private Long id;

    private Long contentId;

    private String screenBullet;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContentId() {
        return contentId;
    }

    public void setContentId(Long contentId) {
        this.contentId = contentId;
    }

    public String getScreenBullet() {
        return screenBullet;
    }

    public void setScreenBullet(String screenBullet) {
        this.screenBullet = screenBullet == null ? null : screenBullet.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}