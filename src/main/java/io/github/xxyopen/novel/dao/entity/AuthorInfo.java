package io.github.xxyopen.novel.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 作者信息
 * </p>
 *
 * @author xiongxiaoyang
 * @date 2022/05/11
 */
@TableName("author_info")
public class AuthorInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 邀请码
     */
    private String inviteCode;

    /**
     * 笔名
     */
    private String penName;

    /**
     * 手机号码
     */
    private String telPhone;

    /**
     * QQ或微信账号
     */
    private String chatAccount;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 作品方向;0-男频 1-女频
     */
    private Integer workDirection;

    /**
     * 0：正常;1-封禁
     */
    private Integer status;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getPenName() {
        return penName;
    }

    public void setPenName(String penName) {
        this.penName = penName;
    }

    public String getTelPhone() {
        return telPhone;
    }

    public void setTelPhone(String telPhone) {
        this.telPhone = telPhone;
    }

    public String getChatAccount() {
        return chatAccount;
    }

    public void setChatAccount(String chatAccount) {
        this.chatAccount = chatAccount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getWorkDirection() {
        return workDirection;
    }

    public void setWorkDirection(Integer workDirection) {
        this.workDirection = workDirection;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "AuthorInfo{" +
        "id=" + id +
        ", userId=" + userId +
        ", inviteCode=" + inviteCode +
        ", penName=" + penName +
        ", telPhone=" + telPhone +
        ", chatAccount=" + chatAccount +
        ", email=" + email +
        ", workDirection=" + workDirection +
        ", status=" + status +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
