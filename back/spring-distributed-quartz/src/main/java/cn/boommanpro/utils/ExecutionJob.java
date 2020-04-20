package cn.boommanpro.utils;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.Executor;

import cn.boommanpro.common.TraceConfig;
import cn.boommanpro.dao.QuartzLogDao;
import cn.boommanpro.entity.QuartzJob;
import cn.boommanpro.entity.QuartzLog;
import cn.boommanpro.service.QuartzJobService;
import cn.boommanpro.util.ThrowableUtil;
import cn.boommanpro.util.spring.ApplicationContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 参考人人开源，https://gitee.com/renrenio/renren-security
 * @author
 * @date 2019-01-07
 */
@Async
@Slf4j
public class ExecutionJob extends QuartzJobBean {



    private Executor executorService =jobExecutor();


    public Executor jobExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setThreadNamePrefix("default-async-");
        executor.setMaxPoolSize(10);
        executor.setTaskDecorator(new MdcTaskDecorator());
        executor.setCorePoolSize(5);
        executor.initialize();
        return executor;
    }


    @Override
    protected void executeInternal(JobExecutionContext context) {
        QuartzJob quartzJob = (QuartzJob) context.getMergedJobDataMap().get(QuartzJob.JOB_KEY);
        String traceId = UUID.randomUUID().toString();
        MDC.put(TraceConfig.TRACE_STRING,traceId);
        // 获取spring bean
        QuartzLogDao quartzLogDao = ApplicationContextUtil.getBean("quartzLogDao");
        QuartzJobService quartzJobService = ApplicationContextUtil.getBean("quartzJobService");
        QuartzManage quartzManage = ApplicationContextUtil.getBean("quartzManage");

        QuartzLog quartzLog = new QuartzLog();
        quartzLog.setTraceId(traceId);
        quartzLog.setJobName(quartzJob.getJobName());
        quartzLog.setBeanName(quartzJob.getBeanName());
        quartzLog.setMethodName(quartzJob.getMethodName());
        quartzLog.setParams(quartzJob.getParams());
        long startTime = System.currentTimeMillis();
        quartzLog.setCronExpression(quartzJob.getCronExpression());
        quartzLog.setCreateTime(LocalDateTime.now());
        try {
            // 执行任务
            log.info("任务准备执行，任务名称：{}", quartzJob.getJobName());
            QuartzRunnable task = new QuartzRunnable(quartzJob.getBeanName(), quartzJob.getMethodName(),
                    quartzJob.getParams());
           executorService.execute(task);
            long times = System.currentTimeMillis() - startTime;
            quartzLog.setTime(times);
            // 任务状态
            quartzLog.setIsSuccess(true);
            log.info("任务执行完毕，任务名称：{} 总共耗时：{} 毫秒", quartzJob.getJobName(), times);
        } catch (Exception e) {
            log.error("任务执行失败，任务名称：{}" + quartzJob.getJobName(), e);
            long times = System.currentTimeMillis() - startTime;
            quartzLog.setTime(times);
            // 任务状态 0：成功 1：失败
            quartzLog.setIsSuccess(false);
            quartzLog.setExceptionDetail(ThrowableUtil.getStackTrace(e));
            //出错就暂停任务
            quartzManage.pauseJob(quartzJob);
            //更新状态
            quartzJobService.updateIsPause(quartzJob);
        } finally {
            quartzLogDao.save(quartzLog);
            MDC.clear();
        }
    }
}
