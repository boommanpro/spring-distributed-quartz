package cn.boommanpro.annotation;

import java.lang.annotation.*;

/**
 * @author boommanpro
 * @date 2020/4/18 16:11
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Sorts {

    SortField[] value();
}
