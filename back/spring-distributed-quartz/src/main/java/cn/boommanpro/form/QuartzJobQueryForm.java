package cn.boommanpro.form;

import javax.persistence.Column;

import cn.boommanpro.common.LocalDateTimeRange;
import lombok.Getter;
import lombok.Setter;

/**
 * @author boommanpro
 * @date 2020/4/19 17:21
 */
@Getter
@Setter
public class QuartzJobQueryForm {

    /**
     * 任务名称
     */
    private String jobName;

    /**
     * Bean名称
     */
    private String beanName;
    /**
     * 暂停
     */
    private Boolean isPause;

    /**
     * 创建日期范围查询
     */
    private LocalDateTimeRange createTimeRange;

    /**
     * 更新日期范围查询
     */
    private LocalDateTimeRange updateTimeRange;
}
