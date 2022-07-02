/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.nqphu.dao;

import com.nqphu.Cache.LRUCache;
import com.nqphu.model.ProfileModel;
import com.nqphu.utils.ConnectionUtil;
import com.phu.ProfileThrift;
import com.phu.ProfileThriftService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;

/**
 *
 * @author phu
 */
public class ProfileDAO {

    private LRUCache lRUCache;
    private static final ConnectionUtil connectionUtil = ConnectionUtil.create("localhost", "9090");

    public ProfileDAO() {
        lRUCache = new LRUCache();
    }

    public ProfileModel getProfile(int id) {
        ProfileModel pro = lRUCache.getProfileFromCache(id);

        if (pro != null) {
            lRUCache.putProfileToCache(pro);
        } else {
            ProfileThriftService.Client client = connectionUtil.getConnection();
            try { 
                ProfileThrift proThrift = client.getProfile(id);
                pro = convertToProfileModel(proThrift);

                lRUCache.putProfileToCache(pro);
            } catch (TException ex) {
                Logger.getLogger(ProfileDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return pro;
    }

    public Map<Integer, ProfileModel> multiGetProfile(List<Integer> lst_id) throws TException {
        Map<Integer, ProfileThrift> mapProThrift = new HashMap<>();
        Map<Integer, ProfileModel> mapProModel = new HashMap<>();

        ProfileThriftService.Client client = connectionUtil.getConnection();
        mapProThrift = client.multiGetProfile(lst_id);

        ProfileThrift proThrift = null;

        for (int i : lst_id) {
            proThrift = mapProThrift.get(i);
            mapProModel.put(i, convertToProfileModel(proThrift));
        }

        return mapProModel;

    }

    public ProfileModel findByUserNameAndPassword(String username, String password) {
        ProfileThriftService.Client client = connectionUtil.getConnection();
        ProfileModel pro = null;
        try {
            ProfileThrift proThrift = client.findByUserNameAndPassword(username, password);
            pro = convertToProfileModel(proThrift);

            lRUCache.putProfileToCache(pro);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pro;
    }

    public int findByUserName(String username) {
        ProfileThriftService.Client client = connectionUtil.getConnection();
        ProfileModel pro = null;
        int id = -2;
        try {
            id = client.findByUserName(username);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }
    
    public int save(ProfileModel profile) {
        ProfileThriftService.Client client = connectionUtil.getConnection();
        int id = -1;
        try {
            id = client.save(convertToProfileThrift(profile));
            profile.setId(id);

            lRUCache.putProfileToCache(profile);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return id;
        }
    }
    
    public void remove(int id) {
        lRUCache.removeProfileFromCache(id);

        ProfileThriftService.Client client = connectionUtil.getConnection();
        try {
            client.remove(id);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ProfileModel convertToProfileModel(ProfileThrift profileThrift) {
        ProfileModel result = null;

        if (profileThrift != null) {
            int id = profileThrift.getId();
            String username = profileThrift.getUsername();
            String fullname = profileThrift.getFullname();
            String pwd = profileThrift.getPwd();
            String email = profileThrift.getEmail();
            String gender = profileThrift.getGender();
            String dob = profileThrift.getDob();
            String address = profileThrift.getAddress();
            String phone = profileThrift.getPhone();

            result = new ProfileModel(id, username, fullname, pwd, email, gender, dob, address, phone);
        }
        return result;
    }

    public ProfileThrift convertToProfileThrift(ProfileModel profileModel) {
        ProfileThrift result = null;

        if (profileModel != null) {
            int id = profileModel.getId();
            String username = profileModel.getUsername();
            String fullname = profileModel.getFullname();
            String pwd = profileModel.getPwd();
            String email = profileModel.getEmail();
            String gender = profileModel.getGender();
            String dob = profileModel.getDob();
            String address = profileModel.getAddress();
            String phone = profileModel.getPhone();

            result = new ProfileThrift(id, username, fullname, pwd, email, gender, dob, address, phone);
        }
        return result;
    }
}
