package cn.boommanpro.service.query;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import cn.boommanpro.dao.QuartzJobDao;
import cn.boommanpro.entity.QuartzJob;
import cn.boommanpro.form.QuartzJobQueryForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * @author <a href="mailto:boommanpro@gmail.com">boomman</a>
 * @date 2019/5/29 17:10
 * @created by boommanpro
 */

@Service
@CacheConfig(cacheNames = "quartzJob")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class QuartzJobQueryService {

    @Autowired
    private QuartzJobDao quartzJobDao;

    public Page<QuartzJob> queryAll(QuartzJobQueryForm quartzJob, Pageable pageable) {
        return quartzJobDao.findAll(new Spec(quartzJob), pageable);
    }

    static class Spec implements Specification<QuartzJobQueryForm> {

        private final QuartzJobQueryForm form;

        public Spec(QuartzJobQueryForm form) {
            this.form = form;
        }

        @Override
        public Predicate toPredicate(Root<QuartzJobQueryForm> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<>();

            if (!ObjectUtils.isEmpty(form.getBeanName())) {
                //模糊查询
                list.add(cb.like(root.get("beanName").as(String.class), "%" + form.getBeanName() + "%"));
            }
            if (!ObjectUtils.isEmpty(form.getJobName())) {
                //模糊查询
                list.add(cb.like(root.get("jobName").as(String.class), "%" + form.getJobName() + "%"));
            }
            if (!ObjectUtils.isEmpty(form.getIsPause())) {
                list.add(cb.equal(root.get("isPause"), form.getIsPause()));
            }

            if (!ObjectUtils.isEmpty(form.getCreateTimeRange()) && !ObjectUtils.isEmpty(form.getCreateTimeRange().getStart())) {
                list.add(cb.greaterThan(root.get("createTime"), form.getCreateTimeRange().getStart()));
            }
            if (!ObjectUtils.isEmpty(form.getCreateTimeRange()) && !ObjectUtils.isEmpty(form.getCreateTimeRange().getEnd())) {
                list.add(cb.lessThan(root.get("createTime"), form.getCreateTimeRange().getStart()));
            }

            if (!ObjectUtils.isEmpty(form.getUpdateTimeRange()) && !ObjectUtils.isEmpty(form.getUpdateTimeRange().getStart())) {
                list.add(cb.greaterThan(root.get("updateTime"), form.getUpdateTimeRange().getStart()));
            }
            if (!ObjectUtils.isEmpty(form.getUpdateTimeRange()) && !ObjectUtils.isEmpty(form.getUpdateTimeRange().getEnd())) {
                list.add(cb.lessThan(root.get("updateTime"), form.getUpdateTimeRange().getStart()));
            }
            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        }
    }
}
