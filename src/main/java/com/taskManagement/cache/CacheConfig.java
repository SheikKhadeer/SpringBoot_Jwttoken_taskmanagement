package com.taskManagement.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {
@Bean
public CacheManager cacheManager() {
	
	ConcurrentMapCacheManager c= new ConcurrentMapCacheManager("users","tasks");
	System.out.println("CACHE MANAGER INITILAIZED");
	Cache cache= c.getCache("users");
	if(cache!=null)
		System.out.println("initial cahce content :"+ cache);
	return c;
}
}
