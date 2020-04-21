package cn.boommanpro.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * @author boommanpro
 * @date 2020/4/18 15:35
 */
@Getter
@Setter
@ToString
public class PageForm extends PageRequest {

    private int pageNum = 1;

    private int pageSize = 10;

    private Sort sort = Sort.unsorted();

    public PageForm() {
        super(0, 10, Sort.unsorted());
    }

    public PageForm(int pageNum, int pageSize,Sort sort) {
        super(pageNum - 1, pageSize, sort);
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.sort = sort;
    }

    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public int getPageNumber() {
        return pageNum - 1;
    }

    @Override
    public long getOffset() {
        return (pageNum - 1) * pageSize;
    }


}
