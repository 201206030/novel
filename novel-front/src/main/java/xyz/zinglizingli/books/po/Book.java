package xyz.zinglizingli.books.po;


import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Book implements Serializable{

    private Long id;

    private Integer catid;

    private String picUrl;

    private String bookName;

    private String author;

    private String bookDesc;

    private Float score;

    private String bookStatus;

    private Long visitCount;

    private Date updateTime;

    private String updateTimeStr;

    private Integer softCat;

    private String softTag;

    public Integer getSoftCat() {
        return softCat;
    }

    public void setSoftCat(Integer softCat) {
        this.softCat = softCat;
    }

    public String getSoftTag() {
        return softTag;
    }

    public void setSoftTag(String softTag) {
        this.softTag = softTag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCatid() {
        return catid;
    }

    public void setCatid(Integer catid) {
        this.catid = catid;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName == null ? null : bookName.trim();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public String getBookDesc() {
        return bookDesc;
    }

    public void setBookDesc(String bookDesc) {
        this.bookDesc = bookDesc == null ? null : bookDesc.trim();
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public String getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(String bookStatus) {
        this.bookStatus = bookStatus == null ? null : bookStatus.trim();
    }

    public Long getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(Long visitCount) {
        this.visitCount = visitCount;
    }

    public Date getUpdateTime()
    {
        SimpleDateFormat format = new SimpleDateFormat();
        try {
            if(this.updateTimeStr != null) {
                updateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(this.updateTimeStr);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }
}