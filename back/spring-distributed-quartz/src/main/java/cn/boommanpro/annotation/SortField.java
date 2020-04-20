package cn.boommanpro.annotation;

import java.lang.annotation.*;

import org.springframework.data.domain.Sort;

/**
 * @author boommanpro
 * @date 2020/4/18 16:17
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface SortField {

    /**
     * Alias for {@link #sort()} to make a declaration configuring fields only more concise.
     *
     * @return
     */
    String[] value() default {};

    /**
     * The properties to sort by by default. If unset, no sorting will be applied at all.
     *
     * @return
     */
    String[] sort() default {};

    /**
     * The direction to sort by. Defaults to {@link org.springframework.data.domain.Sort.Direction#ASC}.
     *
     * @return
     */
    Sort.Direction direction() default Sort.Direction.ASC;
}
