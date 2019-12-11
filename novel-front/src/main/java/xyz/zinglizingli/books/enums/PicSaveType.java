package xyz.zinglizingli.books.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 图片保存类型
 * @author 11797
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum PicSaveType {
    /**
     * 使用网络图片，不保存
     * */
    NETWORK(1),

    /**
     * 本地保存
     * */
    LOCAL(2);

    private int value;
}
