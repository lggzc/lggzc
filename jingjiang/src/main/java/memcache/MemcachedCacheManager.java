package memcache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeoutException;

import net.rubyeye.xmemcached.KeyIterator;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

import org.springframework.cache.Cache;

import com.iaccount.framework.cache.memcache.MemcachedCache;

import hhwycache.HhwyCacheManager;

public class MemcachedCacheManager extends HhwyCacheManager{

	private ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap<String, Cache>();  
    private Map<String, Integer> expireMap = new HashMap<String, Integer>();  

    private MemcachedClient memcachedClient;  
  
	public MemcachedCacheManager(){  
    }  

    @Override  
    protected Collection<? extends Cache> loadCaches(){  
        Collection<Cache> values = cacheMap.values();  
        return values;  
    }  

    @Override  
    public Cache getCache(String name){  
        Cache cache = cacheMap.get(name);  
        if (cache == null){  
            Integer expire = expireMap.get(name);  
            if (expire == null){  
                expire = 0;  
                expireMap.put(name, expire);  
            }  
            cache = new MemcachedCache(name, expire.intValue(), memcachedClient);  
            cacheMap.put(name, cache);  
        }  
        return cache;  
    }  

    public void setMemcachedClient(MemcachedClient memcachedClient){  
        this.memcachedClient = memcachedClient;  
    }  

    public void setConfigMap(Map<String, Integer> configMap){  
        this.expireMap = configMap;  
    }  

    public List<String> getAllKeys() {  
    	KeyIterator it;
		try {
			it = memcachedClient.getKeyIterator(AddrUtil.getOneAddress("localhost:11211"));
	    	while(it.hasNext())
	    	{
	    	   String key=it.next();
	    	}
		} catch (MemcachedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
    
}
