package cn.boommanpro.service;


import cn.boommanpro.entity.QuartzJob;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

/**
 * @author <a href="mailto:boommanpro@gmail.com">boomman</a>
 * @date 2019/5/29 17:10
 * @created by boommanpro
 */

@CacheConfig(cacheNames = "quartzJob")
public interface QuartzJobService {

    /**
     * create
     * @param resources
     * @return
     */
    QuartzJob create(QuartzJob resources);

    /**
     * update
     * @param resources
     * @return
     */
    void update(QuartzJob resources);

    /**
     * del
     * @param quartzJob
     */
    void delete(QuartzJob quartzJob);

    /**
     * findById
     * @param id
     * @return
     */
    QuartzJob findById(Long id);

    /**
     * 更改定时任务状态
     * @param quartzJob
     */
    void updateIsPause(QuartzJob quartzJob);

    /**
     * 立即执行定时任务
     * @param quartzJob
     */
    void execution(QuartzJob quartzJob);
}
