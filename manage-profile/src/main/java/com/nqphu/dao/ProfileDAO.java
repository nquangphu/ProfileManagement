
package com.nqphu.dao;

import com.nqphu.Cache.LRUCache;
import com.nqphu.utils.ConnectionUtil;
import com.phu.MapResult;
import com.phu.Profile;
import com.phu.ProfileResult;
import com.phu.ProfileThriftService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;

public class ProfileDAO {

    private LRUCache lRUCache;
    private static final ConnectionUtil connectionUtil = ConnectionUtil.create("localhost", "9090");

    public ProfileDAO() {
        lRUCache = new LRUCache();
    }

    public ProfileResult getProfile(int id) {
        Profile pro = lRUCache.getProfileFromCache(id);

        if (pro != null) {
            lRUCache.putProfileToCache(pro);
            return new ProfileResult(pro, 0);
        } else {
            try {
                ProfileThriftService.Client client = connectionUtil.getConnection();
                ProfileResult proResult = client.getProfile(id);
                if (proResult.getError() == 0) {
                    lRUCache.putProfileToCache(proResult.getProfile());
                }
                
                return proResult;
            } catch (TException ex) {
                Logger.getLogger(ProfileDAO.class.getName()).log(Level.SEVERE, null, ex);
                
                return new ProfileResult(null, 1);
                
            }
        }
        
    }

    public MapResult multiGetProfile(List<Integer> lst_id) {
        try {
            ProfileThriftService.Client client = connectionUtil.getConnection();
            MapResult mapResult = client.multiGetProfile(lst_id);
            
            return mapResult;
        } catch (TException ex) {
            Logger.getLogger(ProfileDAO.class.getName()).log(Level.SEVERE, null, ex);
            return new MapResult(null, 1);
        }

    }

    public ProfileResult findByUserNameAndPassword(String username, String password) {
        try {
            ProfileThriftService.Client client = connectionUtil.getConnection();
            ProfileResult result = client.findByUserNameAndPassword(username, password);
            
            if (result.getError() == 0) {
                lRUCache.putProfileToCache(result.getProfile());
            }
            
            return result;
        } catch (TException ex) {
            Logger.getLogger(ProfileDAO.class.getName()).log(Level.SEVERE, null, ex);
            return new ProfileResult(null, 1);
        }
            
    }

    public int findByUserName(String username) {
        try {
            ProfileThriftService.Client client = connectionUtil.getConnection();
            int id = client.findByUserName(username);
            
            return id;
        } catch (TException ex) {
            Logger.getLogger(ProfileDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }

    }
    
    public int save(Profile profile) {
        try {
            ProfileThriftService.Client client = connectionUtil.getConnection();
            int id = client.save(profile);
            profile.setId(id);

            lRUCache.putProfileToCache(profile);
            
            return id;
        } catch (TException ex) {
            Logger.getLogger(ProfileDAO.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }

    }
    
    public void remove(int id) {
        try {
            lRUCache.removeProfileFromCache(id);
            
            ProfileThriftService.Client client = connectionUtil.getConnection();
            client.remove(id);
        } catch (TException ex) {
            Logger.getLogger(ProfileDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


}
