package cn.boommanpro.service.query;

import cn.boommanpro.entity.QuartzLog;
import cn.boommanpro.dao.QuartzLogDao;
import cn.boommanpro.form.QuartzLogQueryForm;
import cn.boommanpro.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:boommanpro@gmail.com">boomman</a>
 * @date 2019/5/29 17:10
 * @created by boommanpro
 */

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class QuartzLogQueryService {

    @Autowired
    private QuartzLogDao quartzLogDao;

    public Page<QuartzLog> queryAll(QuartzLogQueryForm form, Pageable pageable) {
        return quartzLogDao.findAll(new Spec(form), pageable);
    }

    static class Spec implements Specification<QuartzLogQueryForm> {

        private final QuartzLogQueryForm form;

        public Spec(QuartzLogQueryForm form) {
            this.form = form;
        }

        @Override
        public Predicate toPredicate(Root<QuartzLogQueryForm> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

            List<Predicate> list = new ArrayList<>();
            if (!ObjectUtils.isEmpty(form.getBeanName())) {
                //模糊查询
                list.add(cb.like(root.get("beanName").as(String.class), "%" + form.getBeanName() + "%"));
            }
            if (!ObjectUtils.isEmpty(form.getJobName())) {
                //模糊查询
                list.add(cb.like(root.get("jobName").as(String.class), "%" + form.getJobName() + "%"));
            }
            if (!ObjectUtils.isEmpty(form.getCreateTimeRange()) && !ObjectUtils.isEmpty(form.getCreateTimeRange().getStart())) {
                //模糊查询
                list.add(cb.greaterThan(root.get("createTime"), form.getCreateTimeRange().getStart()));
            }
            if (!ObjectUtils.isEmpty(form.getCreateTimeRange()) && !ObjectUtils.isEmpty(form.getCreateTimeRange().getEnd())) {
                //模糊查询
                list.add(cb.lessThan(root.get("createTime"), form.getCreateTimeRange().getStart()));
            }
            if (!ObjectUtils.isEmpty(form.getIsSuccess())) {
                list.add(cb.equal(root.get("isSuccess").as(Boolean.class), form.getIsSuccess()));
            }
            Predicate[] p = new Predicate[list.size()];
            return cb.and(list.toArray(p));
        }
    }
}
