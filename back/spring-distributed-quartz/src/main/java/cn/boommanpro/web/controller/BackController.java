package cn.boommanpro.web.controller;

import cn.boommanpro.annotation.SortField;
import cn.boommanpro.common.GetScheduleTimeOfCron;
import cn.boommanpro.common.PageForm;
import cn.boommanpro.common.ResultVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author boommanpro
 * @date 2020/4/18 15:46
 */
@Slf4j
@RestController
@RequestMapping("back")
public class BackController {

    @GetMapping("pageForm")
    public ResultVo<PageForm> pageForm(@SortField("id") PageForm page) {
        log.info("Page:[{}]", page);
        return ResultVo.success(page);
    }

    /**
     * 根据cron表达式
     * 获取近五次执行时间
     */
    @GetMapping("cronRecent")
    public ResultVo<String> cronRecent(@RequestParam String cron) {
        log.info("接收到的cron表达式:[{}]", cron);
        return ResultVo.success(GetScheduleTimeOfCron.getCronSchedule(cron));
    }
}
