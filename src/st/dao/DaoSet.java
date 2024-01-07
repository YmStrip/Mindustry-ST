package st.dao;

import arc.Core;
import arc.func.Prov;
import arc.util.serialization.Json;
import layer.annotations.Config;
import layer.annotations.Provider;
import st.ST;

import java.util.ArrayList;
import java.util.HashMap;

@Provider
public class DaoSet extends Dao {
	@Config
	public String name = "set";
	
	public String name(String n) {
		return ST.name + "-" + name + "-" + n;
	}
	
	public String key() {
		return "__key__";
	}
	
	@Override
	public String[] keys() {
		return keys.keySet().toArray(new String[0]);
	}
	
	public HashMap<String, Boolean> keys = new HashMap<>();
	
	public void saveKeys() {
		Core.settings.putJson(key(), keys);
	}
	
	@Override
	public <T> void set(String name, T d) {
		Core.settings.put(name(name), d);
		keys.put(name, true);
		saveKeys();
	}
	
	@Override
	public <T> T get(String name, Prov<T> def) {
		try {
			return (T) Core.settings.getString(name(name), (String) def.get());
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public <T> void del(String name) {
		Core.settings.remove(name(name));
		keys.remove(name);
		saveKeys();
	}
	
	@Override
	public <T> void clear() {
		keys.forEach((name, d) -> {
			Core.settings.remove(name(name));
		});
		keys.clear();
		saveKeys();
	}
	
	@Override
	public void run() {
		try {
			keys = getJson(key());
			if (keys == null) keys = new HashMap<>();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
