package org.ibpnh.core.fx.user.roleType;

import org.ibpnh.core.fx.AbstractFxImpl;
import org.ibpnh.core.fx.FxValidationResponse;
import org.ibpnh.core.fx.I_Fx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ibpnh.core.dao.user.I_UserDao;
import org.ibpnh.core.dao.user.roleType.I_RoleTypeDao;
import org.ibpnh.core.json.JsonResponse;
import org.ibpnh.core.model.E_Priority;
import org.ibpnh.core.services.caching.client.api.I_UserCacheManager;
import org.ibpnh.core.vo.alert.AlertVo;
import org.ibpnh.core.vo.user.RoleTypeVo;
import org.ibpnh.core.vo.user.UserVo;

/**
 * FX for Modifying a RoleType
 * 
 * @author Axel Collard Bovy
 *
 */
public class Fx_ModifyRoleType extends AbstractFxImpl implements I_Fx {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(Fx_ModifyRoleType.class);
	
	@Autowired
	private I_RoleTypeDao dao;
	
	@Autowired
	private I_UserDao userDao;
	
	@Autowired
	private I_UserCacheManager userCacheManager;
	
	/*
	 * (non-Javadoc)
	 * @see AbstractFxImpl#validate()
	 */
	@Override
	protected FxValidationResponse validate() {
		this.logger.debug("executing Fx_ModifyRoleType._validate()");
		
		if (!this.getDao().checkNameUniqueness(this.getEm(), this.getVo().getName(), this.getVo().getId())) {
			

			String jsonResponseMessage = this.getRealMessageSolver()
					.getMessage("fx.roleType.validation.nonUniqueName", new String[]{this.getVo().getName()});
			
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
			this.getRealMessageSolver().getMessage("fx.roleType.alert.description.modified",
				new String[]{this.getVo().getName()}));
	}

	/*
	 * (non-Javadoc)
	 * @see AbstractFxImpl#_execute()
	 */
	@Override
	protected JsonResponse _execute() {
		this.logger.debug("executing Fx_ModifyRoleType._execute()");
		
		try {
			this.getEm().getTransaction().begin();
			
			//we persist the entity
			RoleTypeVo roleTypeVo = this.getDao().persist(this.getEm(), this.getVo());
			this.setVo(roleTypeVo);
			
			//updates every user that has this role
			for (UserVo userVo : this.getUserDao().findUsersByRoleTypeName(this.getEm(), roleTypeVo)) {
				userVo.getRole().copyOrUpdateFromRoleType(roleTypeVo);
				UserVo newUserVo = this.getUserDao().persist(this.getEm(), userVo);
				
				//if the user is in the cache, updates it
				UserVo auxUserVo = this.getUserCacheManager().getUser(newUserVo.getUsername());
				if (auxUserVo != null) {
					newUserVo.setToken(auxUserVo.getToken());
					this.getUserCacheManager().putUser(newUserVo.getToken(), newUserVo);
					this.getUserCacheManager().putUser(newUserVo.getUsername(), newUserVo);
				}
			}
			
			this.getEm().getTransaction().commit();
			
			return JsonResponse.ok(this.getGson().toJson(roleTypeVo),
					this.getRealMessageSolver().getMessage("default.entity.modified.ok",
							new String[]{this.getRealMessageSolver().getMessage("entity.roleType.name", null)}));
		} catch (Exception e) {
			this.logger.error("error executing Fx_ModifyRoleType.execute()", e);
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

	/**
	 * @return the userCacheManager
	 */
	public I_UserCacheManager getUserCacheManager() {
		return this.userCacheManager;
	}

	/**
	 * @param userCacheManager the userCacheManager to set
	 */
	public void setUserCacheManager(I_UserCacheManager userCacheManager) {
		this.userCacheManager = userCacheManager;
	}

}
