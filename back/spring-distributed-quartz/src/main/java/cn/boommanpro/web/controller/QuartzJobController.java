package cn.boommanpro.web.controller;

import cn.boommanpro.common.PageForm;
import cn.boommanpro.common.PageVo;
import cn.boommanpro.common.ResultVo;
import cn.boommanpro.entity.QuartzJob;
import cn.boommanpro.entity.QuartzLog;
import cn.boommanpro.exception.BadRequestException;
import cn.boommanpro.form.QuartzJobQueryForm;
import cn.boommanpro.form.QuartzLogQueryForm;
import cn.boommanpro.service.QuartzJobService;
import cn.boommanpro.service.query.QuartzJobQueryService;
import cn.boommanpro.service.query.QuartzLogQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author <a href="mailto:boommanpro@gmail.com">boomman</a>
 * @date 2019/5/29 17:10
 * @created by boommanpro
 */

@Slf4j
@RestController
@RequestMapping("/api")
public class QuartzJobController {

    private static final String ENTITY_NAME = "quartzJob";

    @Autowired
    private QuartzJobService quartzJobService;

    @Autowired
    private QuartzJobQueryService quartzJobQueryService;

    @Autowired
    private QuartzLogQueryService quartzLogQueryService;

    @GetMapping(value = "/jobs")
    public ResultVo<PageVo<QuartzJob>> getJobs(QuartzJobQueryForm form, PageForm pageable) {
        return ResultVo.success(quartzJobQueryService.queryAll(form, pageable));
    }

    /**
     * 查询定时任务日志
     *
     * @param resources
     * @param pageable
     * @return
     */
    @GetMapping(value = "/jobLogs")
    public ResultVo<PageVo<QuartzLog>> getJobLogs(QuartzLogQueryForm form, PageForm pageable) {
        return ResultVo.success(quartzLogQueryService.queryAll(form, pageable));
    }

    @PostMapping(value = "/jobs")
    public ResultVo<QuartzJob> create(@Validated @RequestBody QuartzJob resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        return ResultVo.success(quartzJobService.create(resources));
    }

    @PutMapping(value = "/jobs")
    public ResultVo<String> update(@Validated(QuartzJob.Update.class) @RequestBody QuartzJob resources) {
        quartzJobService.update(resources);
        return ResultVo.success();
    }

    /**
     * 暂停
     * @param id 任务id
     * @return vo success
     */
    @PutMapping(value = "/jobs/{id}")
    public ResultVo<String> updateIsPause(@PathVariable Long id) {
        quartzJobService.updateIsPause(quartzJobService.findById(id));
        return ResultVo.success();
    }

    @PutMapping(value = "/jobs/exec/{id}")
    public ResultVo<String> execution(@PathVariable Long id) {
        quartzJobService.execution(quartzJobService.findById(id));
        return ResultVo.success();
    }

    @DeleteMapping(value = "/jobs/{id}")
    public ResultVo<String> delete(@PathVariable Long id) {
        quartzJobService.delete(quartzJobService.findById(id));
        return ResultVo.success();
    }

}
