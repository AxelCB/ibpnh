package org.kairos.ibpnh.core.fx.user.roleType;

import org.kairos.ibpnh.core.fx.AbstractFxImpl;
import org.kairos.ibpnh.core.fx.FxValidationResponse;
import org.kairos.ibpnh.core.fx.I_Fx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.kairos.ibpnh.core.dao.user.I_UserDao;
import org.kairos.ibpnh.core.dao.user.roleType.I_RoleTypeDao;
import org.kairos.ibpnh.core.json.JsonResponse;
import org.kairos.ibpnh.core.model.E_Priority;
import org.kairos.ibpnh.core.vo.alert.AlertVo;
import org.kairos.ibpnh.core.vo.user.RoleTypeVo;

/**
 * FX for Deleting a RoleType
 * 
 * @author Axel Collard Bovy
 *
 */
public class Fx_DeleteRoleType extends AbstractFxImpl implements I_Fx {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(Fx_DeleteRoleType.class);
	
	@Autowired
	private I_RoleTypeDao dao;
	
	@Autowired
	private I_UserDao userDao;
	
	/*
	 * (non-Javadoc)
	 * @see AbstractFxImpl#validate()
	 */
	@Override
	protected FxValidationResponse validate() {
		this.logger.debug("executing Fx_DeleteRoleType._validate()");
		
		if (this.getUserDao().findUsersByRoleTypeName(this.getEm(), this.getVo()).size() > 0) {
			

			String jsonResponseMessage = this.getRealMessageSolver()
					.getMessage("fx.roleType.validation.referencedRoleType", new String[]{this.getVo().getName()});
			
			return FxValidationResponse.error(jsonResponseMessage);
		}
		
		return FxValidationResponse.ok();
	}

	/*
	 * (non-Javadoc)
	 * @see AbstractFxImpl#_completeAlert(AlertVo)
	 */
	@Override
	protected void _completeAlert(AlertVo alertVo) {
		alertVo.setPriority(E_Priority.HIGH);
		
		alertVo.setDescription(
			this.getRealMessageSolver().getMessage("fx.roleType.alert.description.deleted",
				new String[]{this.getVo().getName()}));
	}

	/*
	 * (non-Javadoc)
	 * @see AbstractFxImpl#_execute()
	 */
	@Override
	protected JsonResponse _execute() {
		this.logger.debug("executing Fx_DeleteRoleType._execute()");
		
		try {
			this.getEm().getTransaction().begin();
			
			//we delete the entity
			this.getDao().delete(this.getEm(), this.getVo());
			
			this.getEm().getTransaction().commit();
			
			return JsonResponse.ok(this.getGson().toJson(""),
					this.getRealMessageSolver().getMessage("default.entity.deleted.ok",
							new String[]{this.getRealMessageSolver().getMessage("entity.roleType.name", null)}));
		} catch (Exception e) {
			this.logger.error("error executing Fx_DeleteRoleType.execute()", e);
			try {
				this.getEm().getTransaction().rollback();
			} catch (Exception e1) {
				this.logger.error("error rollbacking transaction", e);
			}

			return this.unexpectedErrorResponse();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see AbstractFxImpl#getVo()
	 */
	@Override
	public RoleTypeVo getVo() {
		return (RoleTypeVo) super.getVo();
	}

	/**
	 * @return the dao
	 */
	public I_RoleTypeDao getDao() {
		return this.dao;
	}

	/**
	 * @param dao the dao to set
	 */
	public void setDao(I_RoleTypeDao dao) {
		this.dao = dao;
	}

	/**
	 * @return the userDao
	 */
	public I_UserDao getUserDao() {
		return this.userDao;
	}

	/**
	 * @param userDao the userDao to set
	 */
	public void setUserDao(I_UserDao userDao) {
		this.userDao = userDao;
	}

}
