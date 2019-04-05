package entity;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @Author Weiping Li
 * @Version 1.0
 * File Created on 2018/11/17
 *
 * 分页结果
 */
@Getter
@Setter
public class PageResult<T> implements Serializable {

    private Long total; //总记录数

    private List<T> rows; //每页数据

    public PageResult() {
    }

    public PageResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }
}
