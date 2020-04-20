package cn.boommanpro.dao;

import cn.boommanpro.entity.QuartzLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author <a href="mailto:boommanpro@gmail.com">boomman</a>
 * @date 2019/5/29 17:10
 * @created by boommanpro
 */

public interface QuartzLogDao extends JpaRepository<QuartzLog,Long>, JpaSpecificationExecutor {

}
