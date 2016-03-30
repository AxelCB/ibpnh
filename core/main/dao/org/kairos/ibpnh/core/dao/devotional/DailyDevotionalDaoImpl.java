package org.kairos.ibpnh.core.dao.devotional;

import org.kairos.ibpnh.core.dao.AbstractDao;
import org.kairos.ibpnh.core.model.devotional.DailyDevotional;
import org.kairos.ibpnh.core.model.devotional.DailyDevotional_;
import org.kairos.ibpnh.core.utils.I_DateUtils;
import org.kairos.ibpnh.core.vo.devotional.DailyDevotionalVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

/**
 * Dao for the Daily Devotional's entities.
 * 
 * @author Axel Collard Bovy
 * 
 */
public class DailyDevotionalDaoImpl extends AbstractDao<DailyDevotional, DailyDevotionalVo> implements I_DailyDevotionalDao {

	/**
	 * Logger
	 */
	private Logger logger = LoggerFactory.getLogger(DailyDevotionalDaoImpl.class);

	@Autowired
	private I_DateUtils dateUtils;

	/*
         * (non-Javadoc)
         *
         * @see AbstractDao#getClazz()
         */
	@Override
	protected Class<DailyDevotional> getClazz() {
		return DailyDevotional.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractDao#getVoClazz()
	 */
	@Override
	public Class<DailyDevotionalVo> getVoClazz() {
		return DailyDevotionalVo.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * AbstractDao#addFilters(javax.persistence.criteria
	 * .Root, javax.persistence.criteria.CriteriaBuilder,
	 * javax.persistence.criteria.Predicate, AbstractVo)
	 */
	@Override
	protected Predicate addFilters(Root<DailyDevotional> root, CriteriaBuilder builder,
			Predicate filters, DailyDevotionalVo vo) {

//		if (StringUtils.isNotBlank(vo.getName())) {
//			filters = builder.and(
//					filters,
//					builder.like(builder.lower(root.get(DailyDevotional_.name)), ("%"
//							+ vo.getName() + "%").toLowerCase()));
//		}
//
//		if (StringUtils.isNotBlank(vo.getSurname())) {
//			filters = builder.and(
//					filters,
//					builder.like(builder.lower(root.get(DailyDevotional_.surname)), ("%"
//							+ vo.getSurname() + "%").toLowerCase()));
//		}
//
//		if (vo.getDocumentType() != null) {
//			filters = builder.and(
//					filters,
//					builder.equal(root.get(DailyDevotional_.documentType),
//							vo.getDocumentType()));
//		}
//
//		if (StringUtils.isNotBlank(vo.getDocumentNumber())) {
//			filters = builder.and(filters, builder.like(
//					builder.lower(root.get(DailyDevotional_.documentNumber)),
//					("%" + vo.getDocumentNumber() + "%").toLowerCase()));
//		}
//
//		if (StringUtils.isNotBlank(vo.getEmail())) {
//			filters = builder.and(
//					filters,
//					builder.like(builder.lower(root.get(DailyDevotional_.email)), ("%"
//							+ vo.getEmail() + "%").toLowerCase()));
//		}
//
//		if (StringUtils.isNotBlank(vo.getCellphoneNumber())) {
//			filters = builder.and(filters, builder.like(
//					builder.lower(root.get(DailyDevotional_.cellphoneNumber)),
//					("%" + vo.getCellphoneNumber() + "%").toLowerCase()));
//		}
//
//		if (StringUtils.isNotBlank(vo.getPhoneNumber())) {
//			filters = builder.and(filters, builder.like(
//					builder.lower(root.get(DailyDevotional_.phoneNumber)),
//					("%" + vo.getPhoneNumber() + "%").toLowerCase()));
//		}

		return filters;
	}

	@Override
	public DailyDevotionalVo getByDate(EntityManager em,Date date) {
		this.logger.debug("getting Daily Devotional by date: {}",this.getDateUtils().formateDate(date));

		// builds the query
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<DailyDevotional> query = builder.createQuery(this.getClazz());
		Root<DailyDevotional> root = query.from(this.getClazz());

		Predicate filters = builder.conjunction();

		// filters by date
		filters = builder.and(filters, builder.equal(root.get(DailyDevotional_.date), date));
		// filters by the deleted flag
		filters = builder.and(filters, builder.equal(root.get(DailyDevotional_.deleted).as(Boolean.class), Boolean.FALSE));

		query.where(filters);

		try {
			// fetch the daily devotional
			DailyDevotional dailyDevotional= em.createQuery(query).getSingleResult();

			return this.map(dailyDevotional);
		} catch (NoResultException e) {
			// there was no daily devotional with required date
			return null;
		}
	}

	@Override
	public Boolean checkDateUniqueness(EntityManager em,Date date, Long excludeId) {
		DailyDevotionalVo dailyDevotionalVo=this.getByDate(em,date);
		return dailyDevotionalVo==null || dailyDevotionalVo.getId().equals(excludeId);
	}

	@Override
	public List<DailyDevotionalVo> listLastDevotionals(EntityManager em,Long amount, Date date) {
		this.logger.debug("getting Daily Devotional by date: {}",this.getDateUtils().formateDate(date));

		// builds the query
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<DailyDevotional> query = builder.createQuery(this.getClazz());
		Root<DailyDevotional> root = query.from(this.getClazz());

		Predicate filters = builder.conjunction();

		// filters by the deleted flag
		filters = builder.and(filters, builder.equal(root.get(DailyDevotional_.deleted).as(Boolean.class), Boolean.FALSE));

		query.where(filters);
		try {
			// fetch the daily devotionals
			List<DailyDevotional> dailyDevotionals= em.createQuery(query).setMaxResults(amount.intValue()).getResultList();

			return this.map(dailyDevotionals);
		} catch (NoResultException e) {
			// there was no daily devotionals
			return null;
		}
	}

	public I_DateUtils getDateUtils() {
		return dateUtils;
	}

	public void setDateUtils(I_DateUtils dateUtils) {
		this.dateUtils = dateUtils;
	}
}
