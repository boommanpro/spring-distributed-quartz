package cn.boommanpro.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.TriggerBuilder;

/**
 * @author boommanpro
 * @date 2020/4/20 9:44
 */
@Slf4j
public class GetScheduleTimeOfCron {

    public static void main(String[] args) {
        System.out.println(getCronSchedule("0/10 * * * * ?"));
    }

    /**
     * 根据Cron表达式获取任务最近5次的执行时间
     *
     * @param cron
     * @return
     */
    public static String getCronSchedule(String cron) {
        String timeSchedule = "";
        if (!CronExpression.isValidExpression(cron)) {
            return "Cron is Illegal!";
        }
        try {
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("Caclulate Date").withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
            Date time0 = trigger.getStartTime();
            Date time1 = trigger.getFireTimeAfter(time0);
            Date time2 = trigger.getFireTimeAfter(time1);
            Date time3 = trigger.getFireTimeAfter(time2);
            Date time4 = trigger.getFireTimeAfter(time3);
            Date time5 = trigger.getFireTimeAfter(time4);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            StringBuilder timeBuilder = new StringBuilder();
            timeBuilder
                    .append(format.format(time1))
                    .append("\n")
                    .append(format.format(time2))
                    .append("\n")
                    .append(format.format(time3))
                    .append("\n")
                    .append(format.format(time4))
                    .append("\n")
                    .append(format.format(time5));
            timeSchedule = timeBuilder.toString();
        } catch (Exception e) {
            timeSchedule = "unKnow Time!";
        }
        return timeSchedule;
    }

    /**
     * 根据Cron表达式获取任务最近 几次的执行时间
     *
     * @param cron  cron表达式
     * @param count 次数
     * @return 最近几次执行的时间
     */
    public static List<String> getCronSchedule(String cron, int count) {
        List<String> retList = new ArrayList<>();
        if (!CronExpression.isValidExpression(cron)) {
            //Cron表达式不正确
            return retList;
        }
        try {
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("Caclulate Date").withSchedule(CronScheduleBuilder.cronSchedule(cron)).build();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = trigger.getStartTime();
            for (int i = 0; i < count; i++) {
                Date time = trigger.getFireTimeAfter(startTime);
                retList.add(format.format(time));
                startTime = time;
            }
        } catch (Exception e) {
            log.error("", e);
        }
        return retList;
    }

}
