package com.java2nb.books.service.impl;

import com.java2nb.books.dao.BookContentDao;
import com.java2nb.books.dao.BookDao;
import com.java2nb.books.dao.BookIndexDao;
import com.java2nb.books.domain.BookContentDO;
import com.java2nb.books.domain.BookDO;
import com.java2nb.books.domain.BookIndexDO;
import com.java2nb.books.util.RestTemplateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.java2nb.books.dao.BookCrawlDao;
import com.java2nb.books.domain.BookCrawlDO;
import com.java2nb.books.service.BookCrawlService;
import org.springframework.web.client.RestTemplate;


@Service
public class BookCrawlServiceImpl implements BookCrawlService {

	private boolean isInteruptBiquDaoCrawl;//是否中断笔趣岛爬虫程序

	private boolean isInteruptBiquTaCrawl;//是否中断笔趣塔爬虫程序

	private RestTemplate restTemplate = RestTemplateUtil.getInstance("utf-8");

	@Autowired
	private BookCrawlDao bookCrawlDao;

	@Autowired
	private BookDao bookDao;

	@Autowired
	private BookIndexDao bookIndexDao;

	@Autowired
	private BookContentDao bookContentDao;
	
	@Override
	public BookCrawlDO get(Long id){
		return bookCrawlDao.get(id);
	}
	
	@Override
	public List<BookCrawlDO> list(Map<String, Object> map){
		return bookCrawlDao.list(map);
	}
	
	@Override
	public int count(Map<String, Object> map){
		return bookCrawlDao.count(map);
	}
	
	@Override
	public int save(BookCrawlDO bookCrawl){
		return bookCrawlDao.save(bookCrawl);
	}
	
	@Override
	public int update(BookCrawlDO bookCrawl){
		return bookCrawlDao.update(bookCrawl);
	}
	
	@Override
	public int remove(Long id){
		return bookCrawlDao.remove(id);
	}
	
	@Override
	public int batchRemove(Long[] ids){
		return bookCrawlDao.batchRemove(ids);
	}

	@Override
	public void updateStatus(BookCrawlDO bookCrawl) {
		bookCrawlDao.update(bookCrawl);

		if(bookCrawl.getStatus() == 0){
			switch (bookCrawl.getCrawlWebCode()) {
				case 1: {
					isInteruptBiquDaoCrawl = true;
					break;
				}
				case 2: {
					isInteruptBiquTaCrawl = true;
					break;
				}
			}
		}else{
			crawlBook(bookCrawl.getCrawlWebCode());
		}


	}


	private void crawlBook(int status){
		for (int i = 1; i <= 7; i++) {

			int finalI = i;
			new Thread(
					() -> {

						try {
							switch (status) {
								case 1: {
									crawBiqudaoBooks(finalI);
									break;
								}
								case 2: {
									crawBiquTaBooks(finalI);
									break;
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
			).start();
		}

	}

	private void crawBiquTaBooks(int i) {
		String baseUrl = "https://m.biquta.com";
		String catBookListUrlBase = baseUrl + "/class/";
		//拼接分类URL
		int page = 1;//起始页码
		int totalPage = page;
		String catBookListUrl = catBookListUrlBase + i + "/" + page + ".html";
		String forObject = getByHttpClient(catBookListUrl);
		if (forObject != null) {
			//匹配分页数<input type="text" class="page_txt" value="1/3019" size="5" name="txtPage" id="txtPage" />
			Pattern pattern = Pattern.compile("value=\"(\\d+)/(\\d+)\"");
			Matcher matcher = pattern.matcher(forObject);
			boolean isFind = matcher.find();
			System.out.println("匹配分页数" + isFind);
			if (isFind) {
				int currentPage = Integer.parseInt(matcher.group(1));
				totalPage = Integer.parseInt(matcher.group(2));
				//解析第一页书籍的数据
				Pattern bookPatten = Pattern.compile("href=\"/(\\d+_\\d+)/\"");
				parseBiquTaBook(bookPatten, forObject, i, baseUrl);
				while (currentPage < totalPage) {
					if(isInteruptBiquTaCrawl){
						break;
					}

					catBookListUrl = catBookListUrlBase + i + "/" + (currentPage + 1) + ".html";
					forObject = getByHttpClient(catBookListUrl);
					if (forObject != null) {
						//匹配分页数
						matcher = pattern.matcher(forObject);
						isFind = matcher.find();

						if (isFind) {
							currentPage = Integer.parseInt(matcher.group(1));
							totalPage = Integer.parseInt(matcher.group(2));
							parseBiquTaBook(bookPatten, forObject, i, baseUrl);
						}
					} else {
						currentPage++;
					}
				}
			}
		}
	}

	private void parseBiquTaBook(Pattern bookPatten, String forObject, int catNum, String baseUrl) {
		Matcher matcher2 = bookPatten.matcher(forObject);
		boolean isFind = matcher2.find();
		Pattern scorePatten = Pattern.compile("<div\\s+class=\"score\">(\\d+\\.\\d+)分</div>");
		Matcher scoreMatch = scorePatten.matcher(forObject);
		boolean scoreFind = scoreMatch.find();

		Pattern bookNamePatten = Pattern.compile("<p class=\"title\">([^/]+)</p>");
		Matcher bookNameMatch = bookNamePatten.matcher(forObject);
		boolean isBookNameMatch = bookNameMatch.find();

		Pattern authorPatten = Pattern.compile(">作者：([^/]+)<");
		Matcher authoreMatch = authorPatten.matcher(forObject);
		boolean isFindAuthor = authoreMatch.find();


		System.out.println("匹配书籍url" + isFind);

		System.out.println("匹配分数" + scoreFind);
		while (isFind && scoreFind && isBookNameMatch && isFindAuthor) {
			if(isInteruptBiquTaCrawl){
				break;
			}

			try {
				Float score = Float.parseFloat(scoreMatch.group(1));

				/*if (score < lowestScore) {//数据库空间有限，暂时爬取8.0分以上的小说
					// Thread.sleep(1000 * 60 * 60 * 24);//因为爬的是龙虎榜，所以遇到第一个8分以下的，之后的都是8分以下的
					continue;
				}*/

				String bookName = bookNameMatch.group(1);
				String author = authoreMatch.group(1);
                /*//查询该书籍是否存在
                boolean isExsit = bookService.isExsitBook(bookName, author);
                if (isExsit) {
                    continue;
                }*/

				//System.out.println(new Date()+bookName + "：");

				String bokNum = matcher2.group(1);
				String bookUrl = baseUrl + "/" + bokNum + "/";

				String body = getByHttpClient(bookUrl);
				if (body != null) {
					Pattern statusPatten = Pattern.compile("状态：([^/]+)</li>");
					Matcher statusMatch = statusPatten.matcher(body);
					if (statusMatch.find()) {
						String status = statusMatch.group(1);
						Pattern updateTimePatten = Pattern.compile("更新：(\\d+-\\d+-\\d+\\s\\d+:\\d+:\\d+)</a>");
						Matcher updateTimeMatch = updateTimePatten.matcher(body);
						if (updateTimeMatch.find()) {
							String updateTimeStr = updateTimeMatch.group(1);
							SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
							Date updateTime = format.parse(updateTimeStr);
							Pattern picPatten = Pattern.compile("<img src=\"([^>]+)\"\\s+onerror=\"this.src=");
							Matcher picMather = picPatten.matcher(body);
							if (picMather.find()) {
								String picSrc = picMather.group(1);

								Pattern descPatten = Pattern.compile("class=\"review\">([^<]+)</p>");
								Matcher descMatch = descPatten.matcher(body);
								if (descMatch.find()) {
									String desc = descMatch.group(1);


									BookDO book = new BookDO();
									book.setAuthor(author);
									book.setCatid(catNum);
									book.setBookDesc(desc);
									book.setBookName(bookName);
									book.setScore(score > 10 ? 8.0f : score);
									book.setPicUrl(picSrc);
									book.setBookStatus(status);
									book.setUpdateTime(updateTime);

									List<BookIndexDO> indexList = new ArrayList<>();
									List<BookContentDO> contentList = new ArrayList<>();

									//读取目录
									Pattern indexPatten = Pattern.compile("<a\\s+href=\"(/du/\\d+_\\d+/)\">查看完整目录</a>");
									Matcher indexMatch = indexPatten.matcher(body);
									if (indexMatch.find()) {
										String indexUrl = baseUrl + indexMatch.group(1);
										String body2 = getByHttpClient(indexUrl);
										if (body2 != null) {
											Pattern indexListPatten = Pattern.compile("<a\\s+style=\"\"\\s+href=\"(/\\d+_\\d+/\\d+\\.html)\">([^/]+)</a>");
											Matcher indexListMatch = indexListPatten.matcher(body2);

											boolean isFindIndex = indexListMatch.find();

											int indexNum = 0;
											//查询该书籍已存在目录号
											List<Integer> hasIndexNum = queryIndexCountByBookNameAndBAuthor(bookName, author);

											while (isFindIndex) {
												if(isInteruptBiquTaCrawl){
													break;
												}

												if (!hasIndexNum.contains(indexNum)) {

													String contentUrl = baseUrl + indexListMatch.group(1);
													String indexName = indexListMatch.group(2);


													//查询章节内容
													String body3 = getByHttpClient(contentUrl);
													if (body3 != null) {
														Pattern contentPattten = Pattern.compile("章节错误,点此举报(.*)加入书签，方便阅读");
														String start = "『章节错误,点此举报』";
														String end = "『加入书签，方便阅读』";
														String content = body3.substring(body3.indexOf(start) + start.length(), body3.indexOf(end));
														//TODO插入章节目录和章节内容
														BookIndexDO bookIndex = new BookIndexDO();
														bookIndex.setIndexName(indexName);
														bookIndex.setIndexNum(indexNum);
														indexList.add(bookIndex);
														BookContentDO bookContent = new BookContentDO();
														bookContent.setContent(content);
														bookContent.setIndexNum(indexNum);
														contentList.add(bookContent);
														//System.out.println(indexName);


													} else {
														break;
													}
												}
												indexNum++;
												isFindIndex = indexListMatch.find();
											}

											if (indexList.size() == contentList.size() && indexList.size() > 0) {
												saveBookAndIndexAndContent(book, indexList, contentList);
											}
										}

									}


								}


							}
						}


					}

				}

			} catch (Exception e) {

				e.printStackTrace();

			} finally {
				matcher2.find();
				isFind = matcher2.find();//需要找两次，应为有两个一样的路径匹配
				scoreFind = scoreMatch.find();
				isBookNameMatch = bookNameMatch.find();
				isFindAuthor = authoreMatch.find();


			}


		}
	}


	private void crawBiqudaoBooks(final int i) {
		String baseUrl = "https://m.biqudao.com";
		String catBookListUrlBase = baseUrl + "/bqgelhb/";
		//拼接分类URL
		int page = 1;//起始页码
		int totalPage = page;
		String catBookListUrl = catBookListUrlBase + i + "/" + page + ".html";
		String forObject = getByHttpClient(catBookListUrl);
		if (forObject != null) {
			//匹配分页数<input type="text" class="page_txt" value="1/3019" size="5" name="txtPage" id="txtPage" />
			Pattern pattern = Pattern.compile("value=\"(\\d+)/(\\d+)\"");
			Matcher matcher = pattern.matcher(forObject);
			boolean isFind = matcher.find();
			System.out.println("匹配分页数" + isFind);
			if (isFind) {
				int currentPage = Integer.parseInt(matcher.group(1));
				totalPage = Integer.parseInt(matcher.group(2));
				//解析第一页书籍的数据
				Pattern bookPatten = Pattern.compile("href=\"/(bqge\\d+)/\"");
				parseBiqudaoBook(bookPatten, forObject, i, baseUrl);
				while (currentPage < totalPage) {

					if(isInteruptBiquDaoCrawl){
						break;
					}

					catBookListUrl = catBookListUrlBase + i + "/" + (currentPage + 1) + ".html";
					forObject = getByHttpClient(catBookListUrl);
					if (forObject != null) {
						//匹配分页数
						matcher = pattern.matcher(forObject);
						isFind = matcher.find();

						if (isFind) {
							currentPage = Integer.parseInt(matcher.group(1));
							totalPage = Integer.parseInt(matcher.group(2));
							parseBiqudaoBook(bookPatten, forObject, i, baseUrl);
						}
					} else {
						currentPage++;
					}
				}
			}
		}

	}

	private void parseBiqudaoBook(Pattern bookPatten, String forObject, int catNum, String baseUrl) {

		Matcher matcher2 = bookPatten.matcher(forObject);
		boolean isFind = matcher2.find();
		Pattern scorePatten = Pattern.compile("<div\\s+class=\"score\">(\\d+\\.\\d+)分</div>");
		Matcher scoreMatch = scorePatten.matcher(forObject);
		boolean scoreFind = scoreMatch.find();

		Pattern bookNamePatten = Pattern.compile("<p class=\"title\">([^/]+)</p>");
		Matcher bookNameMatch = bookNamePatten.matcher(forObject);
		boolean isBookNameMatch = bookNameMatch.find();

		Pattern authorPatten = Pattern.compile(">作者：([^<]+)<");
		Matcher authoreMatch = authorPatten.matcher(forObject);
		boolean isFindAuthor = authoreMatch.find();


		System.out.println("匹配书籍url" + isFind);

		System.out.println("匹配分数" + scoreFind);
		while (isFind && scoreFind && isBookNameMatch && isFindAuthor) {

			try {
				if(isInteruptBiquDaoCrawl){
					break;
				}


				Float score = Float.parseFloat(scoreMatch.group(1));

				/*if (score < lowestScore) {//数据库空间有限，暂时爬取8.0分以上的小说
					Thread.sleep(1000 * 60 * 60 * 24);//因为爬的是龙虎榜，所以遇到第一个8分以下的，之后的都是8分以下的
					continue;
				}*/

				String bookName = bookNameMatch.group(1);
				String author = authoreMatch.group(1);
                /*//查询该书籍是否存在
                boolean isExsit = bookService.isExsitBook(bookName, author);
                if (isExsit) {
                    continue;
                }*/

				//System.out.println(new Date()+bookName + "：");

				String bokNum = matcher2.group(1);
				String bookUrl = baseUrl + "/" + bokNum + "/";

				String body = getByHttpClient(bookUrl);
				if (body != null) {
					Pattern statusPatten = Pattern.compile("状态：([^/]+)</li>");
					Matcher statusMatch = statusPatten.matcher(body);
					if (statusMatch.find()) {
						String status = statusMatch.group(1);
						Pattern updateTimePatten = Pattern.compile("更新：(\\d+-\\d+-\\d+\\s\\d+:\\d+:\\d+)</a>");
						Matcher updateTimeMatch = updateTimePatten.matcher(body);
						if (updateTimeMatch.find()) {
							String updateTimeStr = updateTimeMatch.group(1);
							SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
							Date updateTime = format.parse(updateTimeStr);
							Pattern picPatten = Pattern.compile("<img src=\"([^>]+)\"\\s+onerror=\"this.src=");
							Matcher picMather = picPatten.matcher(body);
							if (picMather.find()) {
								String picSrc = picMather.group(1);

								Pattern descPatten = Pattern.compile("class=\"review\">([^<]+)</p>");
								Matcher descMatch = descPatten.matcher(body);
								if (descMatch.find()) {
									String desc = descMatch.group(1);


									BookDO book = new BookDO();
									book.setAuthor(author);
									book.setCatid(catNum);
									book.setBookDesc(desc);
									book.setBookName(bookName);
									book.setScore(score > 10 ? 8.0f : score);
									book.setPicUrl(picSrc);
									book.setBookStatus(status);
									book.setUpdateTime(updateTime);

									List<BookIndexDO> indexList = new ArrayList<>();
									List<BookContentDO> contentList = new ArrayList<>();

									//读取目录
									Pattern indexPatten = Pattern.compile("<a\\s+href=\"(/bqge\\d+/all\\.html)\">查看完整目录</a>");
									Matcher indexMatch = indexPatten.matcher(body);
									if (indexMatch.find()) {
										String indexUrl = baseUrl + indexMatch.group(1);
										String body2 = getByHttpClient(indexUrl);
										if (body2 != null) {
											Pattern indexListPatten = Pattern.compile("<a[^/]+style[^/]+href=\"(/bqge\\d+/\\d+\\.html)\">([^/]+)</a>");
											Matcher indexListMatch = indexListPatten.matcher(body2);

											boolean isFindIndex = indexListMatch.find();

											int indexNum = 0;
											//查询该书籍已存在目录号
											List<Integer> hasIndexNum = queryIndexCountByBookNameAndBAuthor(bookName, author);

											while (isFindIndex) {
												if(isInteruptBiquDaoCrawl){
													break;
												}
												if (!hasIndexNum.contains(indexNum)) {

													String contentUrl = baseUrl + indexListMatch.group(1);
													String indexName = indexListMatch.group(2);


													//查询章节内容
													String body3 = getByHttpClient(contentUrl);
													if (body3 != null) {
														Pattern contentPattten = Pattern.compile("章节错误,点此举报(.*)加入书签，方便阅读");
														String start = "『章节错误,点此举报』";
														String end = "『加入书签，方便阅读』";
														String content = body3.substring(body3.indexOf(start) + start.length(), body3.indexOf(end));
														//TODO插入章节目录和章节内容
														BookIndexDO bookIndex = new BookIndexDO();
														bookIndex.setIndexName(indexName);
														bookIndex.setIndexNum(indexNum);
														indexList.add(bookIndex);
														BookContentDO bookContent = new BookContentDO();
														bookContent.setContent(content);
														bookContent.setIndexNum(indexNum);
														contentList.add(bookContent);
														//System.out.println(indexName);


													} else {
														break;
													}
												}
												indexNum++;
												isFindIndex = indexListMatch.find();
											}

											if (indexList.size() == contentList.size() && indexList.size() > 0) {
												saveBookAndIndexAndContent(book, indexList, contentList);
											}
										}

									}


								}


							}
						}


					}

				}

			} catch (Exception e) {

				e.printStackTrace();

			} finally {
				matcher2.find();
				isFind = matcher2.find();//需要找两次，应为有两个一样的路径匹配
				scoreFind = scoreMatch.find();
				isBookNameMatch = bookNameMatch.find();
				isFindAuthor = authoreMatch.find();
			}


		}

	}

	public void saveBookAndIndexAndContent(BookDO book, List<BookIndexDO> bookIndex, List<BookContentDO> bookContent) {
		boolean isUpdate = false;
		Long bookId = -1l;
		book.setBookName(book.getBookName().trim());
		book.setAuthor(book.getAuthor().trim());
		Map<String,Object> bookExample = new HashMap<>();
		bookExample.put("bookName",book.getBookName());
		bookExample.put("author",book.getAuthor());
		List<BookDO> books = bookDao.list(bookExample);
		if (books.size() > 0) {
			//更新
			bookId = books.get(0).getId();
			book.setId(bookId);
			bookDao.update(book);
			isUpdate = true;

		} else {
			if (book.getVisitCount() == null) {
				Long visitCount = generateVisiteCount(book.getScore());
				book.setVisitCount(visitCount);
			}
			//插入
			int rows = bookDao.save(book);
			if (rows > 0) {
				bookId = book.getId();
			}


		}

		if (bookId >= 0) {
			//查询目录已存在数量
           /* BookIndexExample bookIndexExample = new BookIndexExample();
            bookIndexExample.createCriteria().andBookIdEqualTo(bookId);
            int indexCount = bookIndexMapper.countByExample(bookIndexExample);*/


			List<BookIndexDO> newBookIndexList = new ArrayList<>();
			List<BookContentDO> newContentList = new ArrayList<>();
			for (int i = 0; i < bookIndex.size(); i++) {
				BookContentDO bookContentItem = bookContent.get(i);
				if (!bookContentItem.getContent().contains("正在手打中，请稍等片刻，内容更新后，需要重新刷新页面，才能获取最新更新")) {


					BookIndexDO bookIndexItem = bookIndex.get(i);
					bookIndexItem.setBookId(bookId);
					bookContentItem.setBookId(bookId);
					//bookContentItem.setIndexId(bookIndexItem.getId());暂时使用bookId和IndexNum查询content
					bookContentItem.setIndexNum(bookIndexItem.getIndexNum());

					newBookIndexList.add(bookIndexItem);
					newContentList.add(bookContentItem);
				}
			}

			if (newBookIndexList.size() > 0) {
				bookIndexDao.insertBatch(newBookIndexList);

				bookContentDao.insertBatch(newContentList);
			}




		}


	}

	private Long generateVisiteCount(Float score) {
		int baseNum = (int) (Math.pow(score * 10, (int) (score - 5)) / 2);
		return Long.parseLong(baseNum + new Random().nextInt(1000) + "");
	}

	private String getByHttpClient(String catBookListUrl) {
		try {
            /*HttpClient httpClient = new DefaultHttpClient();
            // 设置请求和传输超时时间
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000)
                    .setRedirectsEnabled(false) // 不自动重定向
                    .build();
            HttpGet getReq = new HttpGet(catBookListUrl);
            getReq.setConfig(requestConfig);
            getReq.setHeader("user-agent", "Mozilla/5.0 (iPad; CPU OS 11_0 like Mac OS X) AppleWebKit/604.1.34 (KHTML, like Gecko) Version/11.0 Mobile/15A5341f Safari/604.1");
            HttpResponse execute = httpClient.execute(getReq);
            if (execute.getStatusLine().getStatusCode() == HttpStatus.OK.value()) {
                HttpEntity entity = execute.getEntity();
                return EntityUtils.toString(entity, "utf-8");
            } else {
                return null;
            }*/
			//经测试restTemplate比httpClient效率高出很多倍，所有选择restTemplate
			ResponseEntity<String> forEntity = restTemplate.getForEntity(catBookListUrl, String.class);
			if (forEntity.getStatusCode() == HttpStatus.OK) {
				return forEntity.getBody();
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 查询该书籍目录数量
	 */
	private List<Integer> queryIndexCountByBookNameAndBAuthor(String bookName, String author) {
		List<Integer> result = new ArrayList<>();
		Map<String,Object> bookExample = new HashMap<>();
		bookExample.put("bookName",bookName);
		bookExample.put("author",author);
		List<BookDO> books = bookDao.list(bookExample);
		if (books.size() > 0) {

			Long bookId = books.get(0).getId();
			Map<String,Object> bookIndexExample = new HashMap<>();
			bookExample.put("bookId",bookId);
			List<BookIndexDO> bookIndices = bookIndexDao.list(bookIndexExample);
			if (bookIndices != null && bookIndices.size() > 0) {
				for (BookIndexDO bookIndex : bookIndices) {
					result.add(bookIndex.getIndexNum());
				}
			}

		}

		return result;

	}
}
