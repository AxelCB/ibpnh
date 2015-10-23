package org.kairos.ibpnh.dao.user;

import com.googlecode.objectify.cmd.Query;
import org.kairos.ibpnh.dao.AbstractDao;
import org.kairos.ibpnh.model.user.E_RoleType;
import org.kairos.ibpnh.model.user.User;
import org.kairos.ibpnh.vo.user.UserVo;

import javax.persistence.NonUniqueResultException;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Dao for the User's entities.
 *
 * Created on 9/25/15 by
 *
 * @author AxelCollardBovy.
 */
public class UserDaoObjectifyImpl extends AbstractDao<User,UserVo> implements I_UserDao{
	@Override
	public UserVo getByUsername(String username) {
		List<User> users = ofy().load().type(this.getClazz()).filter("username =",username).list();
		if(users.size()>1){
			throw new NonUniqueResultException();
		}else{
			return users.get(0);
		}
	}

	@Override
	public List<UserVo> findUsersByRoleTypeName(E_RoleType roleType) {
		List<User> users = ofy().load().type(this.getClazz()).filter("roletype = ",roleType).list();
		return users;
	}

	@Override
	public Boolean checkUsernameUniqueness(String username, Long excludeId) {
		Query query = ofy().load().type(this.getClazz()).filter("username =", username);
		if(excludeId != null){
			query = query.filter("id !=",excludeId);
		}
		List<User> users = query.list();
		return users.size()==0;
	}

	@Override
	public Integer countByE_RoleType(E_RoleType... roleTypes) {
		return ofy().load().type(this.getClazz()).filter("roletype = ",roleTypes).count();
	}

	@Override
	public UserVo getUserByEnablingHash(String enablingHash) {
		return null;
	}

	@Override
	public Class<User> getClazz() {
		return User.class;
	}

	@Override
	public Class<UserVo> getVoClazz() {
		return UserVo.class;
	}
}