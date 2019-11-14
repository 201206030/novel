package com.java2nb.books.domain;

import java.io.Serializable;



import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.java2nb.common.jsonserializer.LongToStringSerializer;





/**
 * 
 * 
 * @author xiongxy
 * @email 1179705413@qq.com
 * @date 2019-11-13 09:28:15
 */
public class BookIndexDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
		//java中的long能表示的范围比js中number大,也就意味着部分数值在js中存不下(变成不准确的值)
	//所以通过序列化成字符串来解决
	@JsonSerialize(using = LongToStringSerializer.class)
			private Long id;
	//
		//java中的long能表示的范围比js中number大,也就意味着部分数值在js中存不下(变成不准确的值)
	//所以通过序列化成字符串来解决
	@JsonSerialize(using = LongToStringSerializer.class)
			private Long bookId;
	//
			private Integer indexNum;
	//
			private String indexName;

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
	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}
	/**
	 * 获取：
	 */
	public Long getBookId() {
		return bookId;
	}
	/**
	 * 设置：
	 */
	public void setIndexNum(Integer indexNum) {
		this.indexNum = indexNum;
	}
	/**
	 * 获取：
	 */
	public Integer getIndexNum() {
		return indexNum;
	}
	/**
	 * 设置：
	 */
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	/**
	 * 获取：
	 */
	public String getIndexName() {
		return indexName;
	}
}
