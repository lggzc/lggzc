package memcache;
import java.util.ArrayList;
import java.util.HashSet;  
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;  
import java.util.concurrent.TimeoutException;  
  





import net.rubyeye.xmemcached.MemcachedClient;  
import net.rubyeye.xmemcached.exception.MemcachedException;  
  





import org.slf4j.Logger;  
import org.slf4j.LoggerFactory;  

import com.danga.MemCached.MemCachedClient;
  
public class MemCache {  
      
    private static Logger log = LoggerFactory.getLogger(MemCache.class);  
  
    private Set<String> keySet = new HashSet<String>();  
    private final String name;  
    private final int expire;  
    private final MemcachedClient memcachedClient;  
  
    public MemCache(String name, int expire, MemcachedClient memcachedClient) {  
        this.name = name;  
        this.expire = expire;  
        this.memcachedClient = memcachedClient;  
    }  
  
    public Object get(String key) {  
        Object value = null;  
        try {  
            key = this.getKey(key);  
            value = memcachedClient.get(key);  
        } catch (TimeoutException e) {  
            log.warn("获取 Memcached 缓存超时", e);  
        } catch (InterruptedException e) {  
            log.warn("获取 Memcached 缓存被中断", e);  
        } catch (MemcachedException e) {  
            log.warn("获取 Memcached 缓存错误", e);  
        }  
        return value;  
    }  
  
    public void put(String key, Object value) {  
        if (value == null)  
            return;  
        try{  
            key = this.getKey(key);  
            memcachedClient.setWithNoReply(key, expire, value);  
            keySet.add(key);  
        }catch (InterruptedException e){  
            log.warn("更新 Memcached 缓存被中断", e);  
        }catch (MemcachedException e){  
            log.warn("更新 Memcached 缓存错误", e);  
        }  
    }  
  
    public void clear(){  
        for (String key : keySet){  
            try{  
                memcachedClient.deleteWithNoReply(this.getKey(key));  
            }catch (InterruptedException e){  
                log.warn("删除 Memcached 缓存被中断", e);  
            }catch (MemcachedException e){  
                log.warn("删除 Memcached 缓存错误", e);  
            }  
        }  
    }  
  
    public void delete(String key){  
        try{  
            key = this.getKey(key);  
            memcachedClient.deleteWithNoReply(key);  
        }catch (InterruptedException e){  
            log.warn("删除 Memcached 缓存被中断", e);  
        }catch (MemcachedException e){  
            log.warn("删除 Memcached 缓存错误", e);  
        }  
    }  
  
    private String getKey(String key){  
        return name + "_" + key;  
    }  
    
    public List<String> getAllKeys(MemCachedClient memCachedClient) {  
    	log.info("开始获取没有挂掉服务器中所有的key.......");  
        List<String> list = new ArrayList<String>();  
        Map<String, Map<String, String>> items = memCachedClient.statsItems();  
        for (Iterator<String> itemIt = items.keySet().iterator(); itemIt.hasNext();) {  
            String itemKey = itemIt.next();  
            Map<String, String> maps = items.get(itemKey);  
            for (Iterator<String> mapsIt = maps.keySet().iterator(); mapsIt.hasNext();) {  
                String mapsKey = mapsIt.next();  
                   String mapsValue = maps.get(mapsKey);  
                   if (mapsKey.endsWith("number")) {  //memcached key 类型  item_str:integer:number_str  
                    String[] arr = mapsKey.split(":");  
                       int slabNumber = Integer.valueOf(arr[1].trim());  
                       int limit = Integer.valueOf(mapsValue.trim());  
                       Map<String, Map<String, String>> dumpMaps = memCachedClient.statsCacheDump(slabNumber, limit);  
                       for (Iterator<String> dumpIt = dumpMaps.keySet().iterator(); dumpIt.hasNext();) {  
                           String dumpKey = dumpIt.next();  
                           Map<String, String> allMap = dumpMaps.get(dumpKey);  
                           for (Iterator<String> allIt = allMap.keySet().iterator(); allIt.hasNext();) {  
                               String allKey = allIt.next();  
                               list.add(allKey.trim());  
      
                           }  
                       }  
                   }  
            }  
        }  
        log.info("获取没有挂掉服务器中所有的key完成.......");  
        return list;  
    }  
}