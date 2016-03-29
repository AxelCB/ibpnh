package org.ibpnh.core.fx.user.roleType;

import org.ibpnh.core.fx.AbstractFxImpl;
import org.ibpnh.core.fx.FxValidationResponse;
import org.ibpnh.core.fx.I_Fx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.ibpnh.core.dao.user.roleType.I_RoleTypeDao;
import org.ibpnh.core.json.JsonResponse;
import org.ibpnh.core.model.E_Priority;
import org.ibpnh.core.vo.alert.AlertVo;
import org.ibpnh.core.vo.user.RoleTypeVo;

/**
 * FX for Creating a RoleType
 * 
 * @author Axel Collard Bovy
 *
 */
public class Fx_CreateRoleType extends AbstractFxImpl implements I_Fx {

	/**
	 * Logger.
	 */
	private Logger logger = LoggerFactory.getLogger(Fx_CreateRoleType.class);
	
	@Autowired
	private I_RoleTypeDao dao;
	
	/*
	 * (non-Javadoc)
	 * @see AbstractFxImpl#validate()
	 */
	@Override
	protected FxValidationResponse validate() {
		this.logger.debug("executing Fx_CreateRoleType._validate()");
			
		if (!this.getDao().checkNameUniqueness(this.getEm(), this.getVo().getName(), null)) {
			

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
			this.getRealMessageSolver().getMessage("fx.roleType.alert.description.created",
				new String[]{this.getVo().getName()}));
	}

	/*
	 * (non-Javadoc)
	 * @see AbstractFxImpl#_execute()
	 */
	@Override
	protected JsonResponse _execute() {
		this.logger.debug("executing Fx_CreateRoleType._execute()");
		
		try {
			this.getEm().getTransaction().begin();
			
			//we persist the entity
			RoleTypeVo roleTypeVo = this.getDao().persist(this.getEm(), this.getVo());
			this.setVo(roleTypeVo);
			
			this.getEm().getTransaction().commit();
			
			return JsonResponse.ok(this.getGson().toJson(roleTypeVo),
					this.getRealMessageSolver().getMessage("default.entity.created.ok",
							new String[]{this.getRealMessageSolver().getMessage("entity.roleType.name", null)}));
		} catch (Exception e) {
			this.logger.error("error executing Fx_CreateRoleType.execute()", e);
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

}
