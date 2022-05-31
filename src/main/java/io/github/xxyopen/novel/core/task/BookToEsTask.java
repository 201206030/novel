package io.github.xxyopen.novel.core.task;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.Time;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import io.github.xxyopen.novel.core.constant.DatabaseConsts;
import io.github.xxyopen.novel.core.constant.EsConsts;
import io.github.xxyopen.novel.dao.entity.BookInfo;
import io.github.xxyopen.novel.dao.mapper.BookInfoMapper;
import io.github.xxyopen.novel.dto.es.EsBookDto;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 小说数据同步到 elasticsearch 任务
 *
 * @author xiongxiaoyang
 * @date 2022/5/23
 */
@ConditionalOnProperty(prefix = "spring.elasticsearch", name = "enable", havingValue = "true")
@Component
@RequiredArgsConstructor
@Slf4j
public class BookToEsTask {

    private final BookInfoMapper bookInfoMapper;

    private final ElasticsearchClient elasticsearchClient;

    /**
     * 每月凌晨做一次全量数据同步
     */
    @SneakyThrows
    @XxlJob("saveToEsJobHandler")
    public ReturnT<String> saveToEs() {

        try {
            QueryWrapper<BookInfo> queryWrapper = new QueryWrapper<>();
            List<BookInfo> bookInfos;
            long maxId = 0;
            for (; ; ) {
                queryWrapper.clear();
                queryWrapper
                        .orderByAsc(DatabaseConsts.CommonColumnEnum.ID.getName())
                        .gt(DatabaseConsts.CommonColumnEnum.ID.getName(), maxId)
                        .gt(DatabaseConsts.BookTable.COLUMN_WORD_COUNT, 0)
                        .last(DatabaseConsts.SqlEnum.LIMIT_30.getSql());
                bookInfos = bookInfoMapper.selectList(queryWrapper);
                if (bookInfos.isEmpty()) {
                    break;
                }
                BulkRequest.Builder br = new BulkRequest.Builder();

                for (BookInfo book : bookInfos) {
                    br.operations(op -> op
                            .index(idx -> idx
                                    .index(EsConsts.BookIndex.INDEX_NAME)
                                    .id(book.getId().toString())
                                    .document(EsBookDto.build(book))
                            )
                    ).timeout(Time.of(t -> t.time("10s")));
                    maxId = book.getId();
                }

                BulkResponse result = elasticsearchClient.bulk(br.build());

                // Log errors, if any
                if (result.errors()) {
                    log.error("Bulk had errors");
                    for (BulkResponseItem item : result.items()) {
                        if (item.error() != null) {
                            log.error(item.error().reason());
                        }
                    }
                }
            }
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ReturnT.FAIL;
        }
    }

}
