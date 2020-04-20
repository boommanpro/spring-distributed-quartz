package cn.boommanpro.common;

import java.time.LocalDateTime;

import cn.boommanpro.util.StringUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author boommanpro
 * @date 2020/4/19 15:19
 */
@Slf4j
@Getter
@Setter
public class LocalDateTimeRange {

    private LocalDateTime start;

    private LocalDateTime end;

    public LocalDateTimeRange(String timeRange) {
        if (StringUtils.isBlank(timeRange)) {
            return;
        }
        String[] split = timeRange.split(",");
        if (split.length != 2) {
            log.info("输入时间范围有误", timeRange);
        }
        try {
            if (StringUtils.isNotBlank(split[0])) {
                start = LocalDateTimeUtil.getDateTimeOfTimestamp(Long.parseLong(split[0]));
            }
            if (StringUtils.isNotBlank(split[1])) {
                end = LocalDateTimeUtil.getDateTimeOfTimestamp(Long.parseLong(split[1]));
            }
        } catch (NumberFormatException e) {
            log.error("时间范围串解析异常", e);
            //trow inner Exception
        }

    }
}
