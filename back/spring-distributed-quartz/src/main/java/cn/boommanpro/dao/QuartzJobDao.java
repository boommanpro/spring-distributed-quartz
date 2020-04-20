package cn.boommanpro.dao;


import cn.boommanpro.entity.QuartzJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author <a href="mailto:boommanpro@gmail.com">boomman</a>
 * @date 2019/5/29 17:10
 * @created by boommanpro
 */

public interface QuartzJobDao extends JpaRepository<QuartzJob,Long>, JpaSpecificationExecutor {

    /**
     * 更新状态
     * @param id
     */
    @Transactional(rollbackFor = Exception.class)
    @Modifying
    @Query(value = "update quartz_job set is_pause = 1 where id = ?1",nativeQuery = true)
    void updateIsPause(Long id);

    /**
     * 查询不是启用的任务
     * @return
     */
    List<QuartzJob> findByIsPauseIsFalse();
}
