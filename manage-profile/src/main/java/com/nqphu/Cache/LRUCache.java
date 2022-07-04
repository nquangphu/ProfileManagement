
package com.nqphu.Cache;


import com.phu.Profile;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author phu
 */
public class LRUCache {

    public static Map<Integer, Profile> map = new HashMap<>();
    public static Deque<Integer> deque = new LinkedList<>();
    public static int CACHE_CAPACITY = 5;

    public Profile getProfileFromCache(int id) {
        Profile pro = null;
        if (id > 0 && map.containsKey(id)) {
            pro = map.get(id);
            deque.remove(id);
            deque.addFirst(id);
        }
        return pro;
    }

    public void putProfileToCache(Profile pro) {
        int id = pro.getId();
        if (map.containsKey(id)) {
            map.remove(id);
            deque.remove(id);
        } else {
            if (deque.size() == CACHE_CAPACITY) {
                map.remove(deque.getLast());
                deque.removeLast();
            }
        }
        
        map.put(id, pro);
        deque.addFirst(id);
    }
    
    public void removeProfileFromCache (int id) {
        if (id > 0 && this.getProfileFromCache(id) != null) {
            map.remove(id);
            deque.remove(id);
        }
    }
}
