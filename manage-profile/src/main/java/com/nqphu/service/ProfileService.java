package com.nqphu.service;

import com.nqphu.dao.ProfileDAO;
import com.nqphu.model.ProfileModel;
import java.util.List;
import java.util.Map;
import org.apache.thrift.TException;

/**
 *
 * @author phu
 */
public final class ProfileService {

    private ProfileDAO profileDAO;

    public ProfileService() {
        profileDAO = new ProfileDAO();
    }

    public ProfileModel getProfile(int id) {
        return profileDAO.getProfile(id);
    }

    public Map<Integer, ProfileModel> multiGetProfile(List<Integer> lst_id) throws TException {
        return profileDAO.multiGetProfile(lst_id);
    }

    public ProfileModel findByUserNameAndPassword(String username, String password) {
        return profileDAO.findByUserNameAndPassword(username, password);
    }

    public int findByUserName(String username) {
        return profileDAO.findByUserName(username);
    }

    public int save(ProfileModel profile) {
        return profileDAO.save(profile);
    }

    public void remove(int id) {
        profileDAO.remove(id);
    }
}