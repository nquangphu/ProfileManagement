package com.nqphu.service;

import com.nqphu.dao.ProfileDAO;
import com.phu.MapResult;
import com.phu.Profile;
import com.phu.ProfileResult;
import java.util.List;
import org.apache.thrift.TException;

public final class ProfileService {

    private ProfileDAO profileDAO;

    public ProfileService() {
        profileDAO = new ProfileDAO();
    }

    public ProfileResult getProfile(int id) {
        return profileDAO.getProfile(id);
    }

    public MapResult multiGetProfile(List<Integer> lst_id) throws TException {
        return profileDAO.multiGetProfile(lst_id);
    }

    public ProfileResult findByUserNameAndPassword(String username, String password) {
        return profileDAO.findByUserNameAndPassword(username, password);
    }

    public int findByUserName(String username) {
        return profileDAO.findByUserName(username);
    }

    public int save(Profile profile) {
        return profileDAO.save(profile);
    }

    public void remove(int id) {
        profileDAO.remove(id);
    }
}