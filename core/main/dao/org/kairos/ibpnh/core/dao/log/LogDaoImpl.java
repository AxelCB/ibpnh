package org.kairos.ibpnh.core.dao.log;

import java.util.Date;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.kairos.ibpnh.core.dao.AbstractDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.kairos.ibpnh.core.model.log.Log;
import org.kairos.ibpnh.core.model.log.Log_;
import org.kairos.ibpnh.core.utils.I_DateUtils;
import org.kairos.ibpnh.core.vo.log.LogVo;

/**
 * Implementation of the Log DAO Interface.
 * 
 * @author Axel Collard Bovy
 * 
 */
public class LogDaoImpl extends AbstractDao<Log, LogVo> implements I_LogDao {

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
	protected Class<Log> getClazz() {
		return Log.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see AbstractDao#getVoClazz()
	 */
	@Override
	public Class<LogVo> getVoClazz() {
		return LogVo.class;
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
	protected Predicate addFilters(Root<Log> root, CriteriaBuilder builder,
			Predicate filters, LogVo vo) {

		if (vo.getCreationTimestamp() != null) {
			filters = builder.and(filters, builder.equal(
					builder.function("date", Date.class,
							root.get(Log_.creationTimestamp)),
					this.getDateUtils().zeroHour(vo.getCreationTimestamp())));
		}

		if (StringUtils.isNotBlank(vo.getLogDate())) {
			filters = builder.and(
					filters,
					builder.like(builder.lower(root.get(Log_.logDate)), ("%"
							+ vo.getLogDate() + "%").toLowerCase()));
		}

		if (StringUtils.isNotBlank(vo.getLogLevel())) {
			filters = builder.and(
					filters,
					builder.like(builder.lower(root.get(Log_.logLevel)), ("%"
							+ vo.getLogLevel() + "%").toLowerCase()));
		}
		
		if (StringUtils.isNotBlank(vo.getLogLocation())) {
			filters = builder.and(
					filters,
					builder.like(builder.lower(root.get(Log_.logLocation)), ("%"
							+ vo.getLogLocation() + "%").toLowerCase()));
		}
		
		if (StringUtils.isNotBlank(vo.getLogMessage())) {
			filters = builder.and(
					filters,
					builder.like(builder.lower(root.get(Log_.logMessage)), ("%"
							+ vo.getLogMessage() + "%").toLowerCase()));
		}
		
		if (StringUtils.isNotBlank(vo.getLogStackTrace())) {
			filters = builder.and(
					filters,
					builder.like(builder.lower(root.get(Log_.logStackTrace)), ("%"
							+ vo.getLogStackTrace() + "%").toLowerCase()));
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