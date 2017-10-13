package hhwycache;
import java.util.List;

import org.springframework.cache.Cache;

import com.danga.MemCached.MemCachedClient;

public interface HhwyCache extends Cache{
	
	public List<String> getAllKeys(MemCachedClient memCachedClient);
	}
