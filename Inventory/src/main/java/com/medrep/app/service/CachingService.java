package com.medrep.app.service;

import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.medrep.app.util.Util;

@Service
public class CachingService {
	private HashMap<String, Object> cache = new HashMap<String, Object>();

	public void put(String key, Object obj) {
		if (cache == null)
			cache = new HashMap<String, Object>();
		cache.put(key, obj);
	}

	public Object get(String key) {
		if (Util.isEmpty(cache))
			return null;
		return cache.get(key);

	}

	public void clear() {
		cache.clear();
	}

}
