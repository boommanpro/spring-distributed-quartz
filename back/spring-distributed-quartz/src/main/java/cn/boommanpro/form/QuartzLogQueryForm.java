package cn.boommanpro.form;

import javax.persistence.Column;

import cn.boommanpro.common.LocalDateTimeRange;
import lombok.Getter;
import lombok.Setter;

/**
 * @author boommanpro
 * @date 2020/4/19 15:17
 */
@Getter
@Setter
public class QuartzLogQueryForm {

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * Bean名称
     */
    private String beanName;
    /**
     * 状态
     */
    private Boolean isSuccess;

    /**
     * 创建日期范围查询
     */
    private LocalDateTimeRange createTimeRange;

}
