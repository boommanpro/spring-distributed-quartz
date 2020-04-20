package cn.boommanpro.entity;

import cn.boommanpro.web.serializer.LocalDateTime2LongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author <a href="mailto:boommanpro@gmail.com">BoomManPro</a>
 * @date 2019/5/29 17:24
 * @created by BoomManPro
 */

@Data
@Entity
@Table(name = "quartz_job")
public class QuartzJob implements Serializable {



    public static final String JOB_KEY = "JOB_KEY";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(groups = {Update.class})
    private Long id;

    /**
     * 定时器名称
     */
    @Column(name = "job_name")
    private String jobName;

    /**
     * Bean名称
     */
    @Column(name = "bean_name")
    @NotBlank
    private String beanName;

    /**
     * 方法名称
     */
    @Column(name = "method_name")
    @NotBlank
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
    @NotBlank
    private String cronExpression;

    /**
     * 状态
     */
    @Column(name = "is_pause")
    private Boolean isPause = true;

    /**
     * 备注
     */
    @Column(name = "remark")
    @NotBlank
    private String remark;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @JsonSerialize(using = LocalDateTime2LongSerializer.class)
    private LocalDateTime createTime;

    /**
     * 创建日期
     */
    @Column(name = "update_time")
    @JsonSerialize(using = LocalDateTime2LongSerializer.class)
    private LocalDateTime updateTime;


    public interface Update{}
}