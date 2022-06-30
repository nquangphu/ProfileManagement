package com.nqphu.service;

import com.nqphu.Cache.LRUCache;
import com.nqphu.model.ProfileModel;
import com.nqphu.utils.ConnectionUtil;
import com.phu.ProfileThrift;
import com.phu.ProfileThriftService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.thrift.TException;

/**
 *
 * @author phu
 */
public final class ProfileService {

    private LRUCache lRUCache;
    private static final ConnectionUtil connectionUtil = ConnectionUtil.create("localhost", "9090");

    public ProfileService() {
        lRUCache = new LRUCache();
    }

    public ProfileModel getProfile(int id) {

        ProfileModel pro = lRUCache.getProfileFromCache(id);
        if (pro != null) {
            lRUCache.putProfileToCache(pro);
            return pro;
        } else {
            ProfileThriftService.Client client = connectionUtil.getConnection();
            try {
                ProfileThrift proThrift = client.getProfile(id);
                pro = convertToProfileModel(proThrift);

                lRUCache.putProfileToCache(pro);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                return pro;
            }
        }
    }

    public Map<Integer, ProfileModel> multiGetProfile(List<Integer> lst_id) throws TException {
        Map<Integer, ProfileModel> result = new HashMap<>();

        for (int i : lst_id) {
            result.put(i, this.getProfile(i));
        }

        return result;

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
