package com.taskManagement.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class CacheInspect {

	@Autowired
	private CacheManager cacheManager;
	
	public void inspectCache(Long userId) {
		System.out.println("userId"+userId);
		Cache usersCache=cacheManager.getCache("users");
		if(usersCache!=null) {
			System.out.println("Inspecting users cache....");
			System.out.println(usersCache.getName());
			System.out.println(usersCache.get(userId));
			Cache.ValueWrapper v=usersCache.get(userId);
			if(v!=null)
				System.out.println("cache content for key 1:"+v.get());
			else
				System.out.println("No cache entry for key 1:");
		}
		else
			System.out.println("'USERS' CACHE IS NOT INITIALIZED");
	}
}
