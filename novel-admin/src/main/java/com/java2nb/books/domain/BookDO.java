package com.java2nb.books.domain;

import java.io.Serializable;



import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.java2nb.common.jsonserializer.LongToStringSerializer;


import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;



/**
 * 
 * 
 * @author xiongxy
 * @email 1179705413@qq.com
 * @date 2019-11-13 09:27:04
 */
public class BookDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
		//java中的long能表示的范围比js中number大,也就意味着部分数值在js中存不下(变成不准确的值)
	//所以通过序列化成字符串来解决
	@JsonSerialize(using = LongToStringSerializer.class)
			private Long id;
	//
			private Integer catid;
	//
			private String picUrl;
	//
			private String bookName;
	//
			private String author;
	//
			private String bookDesc;
	//
			private Float score;
	//
			private String bookStatus;
	//
		//java中的long能表示的范围比js中number大,也就意味着部分数值在js中存不下(变成不准确的值)
	//所以通过序列化成字符串来解决
	@JsonSerialize(using = LongToStringSerializer.class)
			private Long visitCount;
	//
			@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		private Date updateTime;
	//
			private Integer softCat;
	//
			private String softTag;

	/**
	 * 设置：
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Long getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setCatid(Integer catid) {
		this.catid = catid;
	}
	/**
	 * 获取：
	 */
	public Integer getCatid() {
		return catid;
	}
	/**
	 * 设置：
	 */
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	/**
	 * 获取：
	 */
	public String getPicUrl() {
		return picUrl;
	}
	/**
	 * 设置：
	 */
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	/**
	 * 获取：
	 */
	public String getBookName() {
		return bookName;
	}
	/**
	 * 设置：
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
	/**
	 * 获取：
	 */
	public String getAuthor() {
		return author;
	}
	/**
	 * 设置：
	 */
	public void setBookDesc(String bookDesc) {
		this.bookDesc = bookDesc;
	}
	/**
	 * 获取：
	 */
	public String getBookDesc() {
		return bookDesc;
	}
	/**
	 * 设置：
	 */
	public void setScore(Float score) {
		this.score = score;
	}
	/**
	 * 获取：
	 */
	public Float getScore() {
		return score;
	}
	/**
	 * 设置：
	 */
	public void setBookStatus(String bookStatus) {
		this.bookStatus = bookStatus;
	}
	/**
	 * 获取：
	 */
	public String getBookStatus() {
		return bookStatus;
	}
	/**
	 * 设置：
	 */
	public void setVisitCount(Long visitCount) {
		this.visitCount = visitCount;
	}
	/**
	 * 获取：
	 */
	public Long getVisitCount() {
		return visitCount;
	}
	/**
	 * 设置：
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：
	 */
	public void setSoftCat(Integer softCat) {
		this.softCat = softCat;
	}
	/**
	 * 获取：
	 */
	public Integer getSoftCat() {
		return softCat;
	}
	/**
	 * 设置：
	 */
	public void setSoftTag(String softTag) {
		this.softTag = softTag;
	}
	/**
	 * 获取：
	 */
	public String getSoftTag() {
		return softTag;
	}
}
