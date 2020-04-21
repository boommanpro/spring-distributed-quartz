package cn.boommanpro.web.resolver;

import java.util.Optional;

import cn.boommanpro.annotation.SortField;
import cn.boommanpro.annotation.Sorts;
import cn.boommanpro.common.PageForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author wangqimeng
 * @date 2020/4/21 10:17
 */
@Slf4j
@Component
public class JpaSortsMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        SortField sortField = parameter.getParameterAnnotation(SortField.class);
        Sorts sorts = parameter.getParameterAnnotation(Sorts.class);
        return   // sortField
                (sortField != null && PageForm.class.isAssignableFrom(parameter.getParameterType())) ||
                        (sorts != null && PageForm.class.isAssignableFrom(parameter.getParameterType()));
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        SortField sortField = parameter.getParameterAnnotation(SortField.class);
        Sorts sorts = parameter.getParameterAnnotation(Sorts.class);
        int pageNum = Optional.ofNullable(webRequest.getParameter("pageNum")).map(s -> Integer.valueOf(s)).orElse(1);
        int pageSize = Optional.ofNullable(webRequest.getParameter("pageSize")).map(s -> Integer.valueOf(s)).orElse(10);
        return new PageForm(pageNum, pageSize, getSort(sorts, sortField));
    }

    private Sort getSort(Sorts sorts, SortField sortField) {
        return sorts != null ? getSort(sorts) : getSort(sortField);
    }

    private Sort getSort(Sorts sorts) {
        Sort sort = Sort.unsorted();
        for (SortField sortField : sorts.value()) {
            sort = sort.and(getSort(sortField));
        }
        return sort;
    }

    private Sort getSort(SortField sortField) {
        if (sortField.sort().length == 0) {
            return Sort.unsorted();
        }
        return Sort.by(sortField.direction(), sortField.sort());
    }
}
