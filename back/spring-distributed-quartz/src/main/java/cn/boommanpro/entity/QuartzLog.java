package cn.boommanpro.entity;

import cn.boommanpro.web.serializer.LocalDateTime2LongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.*;

/**
 * @author <a href="mailto:boommanpro@gmail.com">BoomManPro</a>
 * @date 2019/5/29 17:24
 * @created by BoomManPro
 */
@Entity
@Data
@Table(name = "quartz_log")
public class QuartzLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 任务名称
     */
    @Column(name = "job_name")
    private String jobName;


    /**
     * 跟踪id
     */
    @Column(name = "trace_id")
    private String traceId;

    /**
     * Bean名称
     */
    @Column(name = "bean_name")
    private String beanName;

    /**
     * 方法名称
     */
    @Column(name = "method_name")
    private String methodName;

    /**
     * 参数
     */
    @Column(name = "params")
    private String params;

    /**
     * cron表达式
     */
    @Column(name = "cron_expression")
    private String cronExpression;

    /**
     * 状态
     */
    @Column(name = "is_success")
    private Boolean isSuccess;

    /**
     * 异常详细
     */
    @Column(name = "exception_detail",columnDefinition = "text")
    private String exceptionDetail;

    /**
     * 耗时（毫秒）
     */
    private Long time;

    /**
     * 创建日期
     */
    @Column(name = "create_time")
    @JsonSerialize(using = LocalDateTime2LongSerializer.class)
    private LocalDateTime createTime;
}
