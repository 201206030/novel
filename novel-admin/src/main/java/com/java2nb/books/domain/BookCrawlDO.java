package com.java2nb.books.domain;

import java.io.Serializable;



import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.java2nb.common.jsonserializer.LongToStringSerializer;





/**
 * 
 * 
 * @author xiongxy
 * @email 1179705413@qq.com
 * @date 2019-11-15 03:42:54
 */
public class BookCrawlDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
		//java中的long能表示的范围比js中number大,也就意味着部分数值在js中存不下(变成不准确的值)
	//所以通过序列化成字符串来解决
	@JsonSerialize(using = LongToStringSerializer.class)
			private Long id;
	//
			private String crawlWebName;
	//
			private String crawlWebUrl;
	//
			private Integer crawlWebCode;
	//
			private Integer status;

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
	public void setCrawlWebName(String crawlWebName) {
		this.crawlWebName = crawlWebName;
	}
	/**
	 * 获取：
	 */
	public String getCrawlWebName() {
		return crawlWebName;
	}
	/**
	 * 设置：
	 */
	public void setCrawlWebUrl(String crawlWebUrl) {
		this.crawlWebUrl = crawlWebUrl;
	}
	/**
	 * 获取：
	 */
	public String getCrawlWebUrl() {
		return crawlWebUrl;
	}
	/**
	 * 设置：
	 */
	public void setCrawlWebCode(Integer crawlWebCode) {
		this.crawlWebCode = crawlWebCode;
	}
	/**
	 * 获取：
	 */
	public Integer getCrawlWebCode() {
		return crawlWebCode;
	}
	/**
	 * 设置：
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 获取：
	 */
	public Integer getStatus() {
		return status;
	}
}
