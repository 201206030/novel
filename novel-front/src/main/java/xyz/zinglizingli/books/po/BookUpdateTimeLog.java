package xyz.zinglizingli.books.po;

import java.util.Date;

public class BookUpdateTimeLog {
    private Integer id;

    private Integer bookCatId;

    private Date lastUpdateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBookCatId() {
        return bookCatId;
    }

    public void setBookCatId(Integer bookCatId) {
        this.bookCatId = bookCatId;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}