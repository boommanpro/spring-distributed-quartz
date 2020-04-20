package cn.boommanpro.common;

import java.util.LinkedList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

/**
 * @author boommanpro
 * @date 2020/4/18 15:37
 */
@Data
@NoArgsConstructor
public class PageVo<T> {

    private Long total;

    private Integer pageNum;

    private Integer pageSize;

    private List<T> list = new LinkedList<>();

    public PageVo(Long total, Integer jpaPageNum, Integer pageSize, List<T> list) {
        this.total = total;
        this.pageNum = jpaPageNum + 1;
        this.pageSize = pageSize;
        this.list = list;
    }

    public static <T> PageVo<T> of(long total, int jpaPageNum, int pageSize, List<T> list) {
        return new PageVo<>(total, jpaPageNum, pageSize, list);
    }

    public static <T> PageVo<T> of(List<T> list) {
        return of(-1, -1, -1, list);
    }

    public static <T> PageVo<T> of(Page<T> page) {
        return page == null ? of(new LinkedList<>()) : of(page.getTotalElements(), page.getNumber(), page.getSize(), page.getContent());
    }
}