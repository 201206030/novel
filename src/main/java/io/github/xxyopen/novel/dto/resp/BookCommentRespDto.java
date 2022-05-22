package io.github.xxyopen.novel.dto.resp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.github.xxyopen.novel.core.json.serializer.UsernameSerializer;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 小说评论 响应DTO
 * @author xiongxiaoyang
 * @date 2022/5/17
 */
@Data
@Builder
public class BookCommentRespDto {

    private Long commentTotal;

    private List<CommentInfo> comments;

    @Data
    @Builder
    public static class CommentInfo {

        private Long id;

        private String commentContent;

        @JsonSerialize(using = UsernameSerializer.class)
        private String commentUser;

        private Long commentUserId;

        private String commentUserPhoto;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime commentTime;

    }

}
