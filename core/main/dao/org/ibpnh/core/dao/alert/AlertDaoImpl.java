package org.ibpnh.core.dao.alert;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.ibpnh.core.dao.AbstractDao;
import org.ibpnh.core.model.alert.Alert;
import org.ibpnh.core.model.alert.Alert_;
import org.ibpnh.core.model.user.User;
import org.ibpnh.core.model.user.User_;
import org.ibpnh.core.utils.I_DateUtils;
import org.ibpnh.core.vo.alert.AlertVo;

/**
 * Dao for the Parameter's entities.
 * 
 * @author Axel Collard Bovy
 * 
 */
public class AlertDaoImpl extends AbstractDao<Alert, AlertVo> implements
		I_AlertDao {
	
	/**
	 * Date Utils.
	 */
	@Autowired
	private I_DateUtils dateUtils;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractDao#getClazz()
	 */
	@Override
	protected Class<Alert> getClazz() {
		return Alert.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractDao#getVoClazz()
	 */
	@Override
	public Class<AlertVo> getVoClazz() {
		return AlertVo.class;
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
	protected Predicate addFilters(Root<Alert> root, CriteriaBuilder builder,
			Predicate filters, AlertVo vo) {

		if (StringUtils.isNotBlank(vo.getDescription())) {
			filters = builder.and(filters, builder.like(
					builder.lower(root.get(Alert_.description)),
					("%" + vo.getDescription() + "%").toLowerCase()));
		}

		if (StringUtils.isNotBlank(vo.getObjectClassName())) {
			filters = builder.and(
					filters,
					builder.equal(root.get(Alert_.objectClassName),
							vo.getObjectClassName()));
		}

		if (vo.getObjectId() != null) {
			filters = builder.and(filters,
					builder.equal(root.get(Alert_.objectId), vo.getObjectId()));
		}

		if (vo.getPriority() != null) {
			filters = builder.and(filters,
					builder.equal(root.get(Alert_.priority), vo.getPriority()));
		}

		if (vo.getTimestamp() != null) {
			filters = builder.and(
					filters,
					builder.equal(
							builder.function("date", Date.class,
									root.get(Alert_.timestamp)),
							this.getDateUtils().zeroHour(vo.getTimestamp())));
		}

		if (vo.getUser() != null && vo.getUser().getId() != null) {
			Join<Alert, User> user = root.join(Alert_.user);
			filters = builder.and(filters,
					builder.equal(user.get(User_.id), vo.getUser().getId()));
		}

		return filters;
	}

	/**
	 * @return the dateUtils
	 */
	public I_DateUtils getDateUtils() {
		return this.dateUtils;
	}

	/**
	 * @param dateUtils the dateUtils to set
	 */
	public void setDateUtils(I_DateUtils dateUtils) {
		this.dateUtils = dateUtils;
	}	
}
