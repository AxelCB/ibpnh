package org.ibpnh.core.dao.function;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.ibpnh.core.dao.AbstractDao;
import org.ibpnh.core.model.user.Function;
import org.ibpnh.core.model.user.Function_;
import org.ibpnh.core.vo.user.FunctionVo;

/**
 * Implementation of the Function DAO Interface
 * 
 * @author fgonzalez
 *
 */
public class FunctionDaoImpl extends AbstractDao<Function, FunctionVo> implements I_FunctionDao {

	/*
	 * (non-Javadoc)
	 * @see org.ibpnh.core.dao.AbstractDao#getClazz()
	 */
	@Override
	protected Class<Function> getClazz() {
		return Function.class;
	}

	/*
	 * (non-Javadoc)
	 * @see org.ibpnh.core.dao.AbstractDao#getVoClazz()
	 */
	@Override
	public Class<FunctionVo> getVoClazz() {
		return FunctionVo.class;
	}

	/*
	 * (non-Javadoc)
	 * @see org.ibpnh.core.dao.AbstractDao#addFilters(javax.persistence.criteria.Root, javax.persistence.criteria.CriteriaBuilder, javax.persistence.criteria.Predicate, org.ibpnh.core.vo.AbstractVo)
	 */
	@Override
	protected Predicate addFilters(Root<Function> root,
			CriteriaBuilder builder, Predicate filters, FunctionVo vo) {

		if (StringUtils.isNotBlank(vo.getActionName())) {
			filters = builder.and(filters,
				builder.like(
					builder.lower(root.get(Function_.actionName).as(String.class)),
					("%" + vo.getActionName() + "%").toLowerCase()));
		}
		
		if (StringUtils.isNotBlank(vo.getDescription())) {
			filters = builder.and(filters,
				builder.like(
					builder.lower(root.get(Function_.description).as(String.class)),
					("%" + vo.getDescription() + "%").toLowerCase()));
		}
		
		if (StringUtils.isNotBlank(vo.getMenuName())) {
			filters = builder.and(filters,
				builder.like(
					builder.lower(root.get(Function_.menuName).as(String.class)),
					("%" + vo.getMenuName() + "%").toLowerCase()));
		}
		
		if (StringUtils.isNotBlank(vo.getName())) {
			filters = builder.and(filters,
				builder.like(
					builder.lower(root.get(Function_.name).as(String.class)),
					("%" + vo.getName() + "%").toLowerCase()));
		}
		
		if (StringUtils.isNotBlank(vo.getSubmenuName())) {
			filters = builder.and(filters,
				builder.like(
					builder.lower(root.get(Function_.submenuName).as(String.class)),
					("%" + vo.getSubmenuName() + "%").toLowerCase()));
		}
		
		if (StringUtils.isNotBlank(vo.getUri())) {
			filters = builder.and(filters,
				builder.like(
					builder.lower(root.get(Function_.uri).as(String.class)),
					("%" + vo.getUri() + "%").toLowerCase()));
		}
		
		return filters;
	}
	
}
