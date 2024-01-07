package st.dao;

import arc.func.Prov;
import com.fasterxml.jackson.databind.ObjectMapper;
import layer.annotations.LayerClass;
import layer.extend.Layer;

import java.util.HashMap;

@LayerClass
public class Dao extends Layer {
	public ObjectMapper mapper = new ObjectMapper();
	
	public String getStr(String name) {
		var res = get(name);
		if (res == null) return null;
		return res + "";
	}
	
	public String getStr(String name, Prov<String> def) {
		var res = get(name);
		if (res == null) return def.get();
		return res + "";
	}
	
	public void setStr(String name, String d) {
		set(name, d);
	}
	
	public boolean getBool(String name) {
		var res = get(name);
		if (res == null) return false;
		if (res instanceof String) return Boolean.parseBoolean(res + "");
		try {
			return (boolean) res;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean getBool(String name, Prov<Boolean> def) {
		var res = get(name);
		if (res == null) return def.get();
		if (res instanceof String) return Boolean.parseBoolean(res + "");
		try {
			return (boolean) res;
		} catch (Exception e) {
			return def.get();
		}
	}
	
	public void setBool(String name, boolean b) {
		set(name, b);
	}
	
	public float getFloat(String name) {
		var res = get(name);
		if (res == null) return 0;
		if (res instanceof String) {
			try {
				return Float.parseFloat(res + "");
			} catch (Exception e) {
				return 0;
			}
		}
		try {
			return (float) res;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public float getFloat(String name, Prov<Float> def) {
		var res = get(name);
		if (res == null) return def.get();
		if (res instanceof String) {
			try {
				return Float.parseFloat(res + "");
			} catch (Exception e) {
				return 0;
			}
		}
		try {
			return (float) res;
		} catch (Exception e) {
			return def.get();
		}
	}
	
	public void setFloat(String name, Float f) {
		set(name, f);
	}
	
	public float getInt(String name) {
		try {
			return Integer.parseInt(get(name) + "");
		} catch (Exception e) {
			return 0;
		}
	}
	
	public float getInt(String name, Prov<Float> def) {
		try {
			return Integer.parseInt(get(name) + "");
		} catch (Exception e) {
			return def.get();
		}
	}
	
	public void setInt(String name, int f) {
		set(name, f);
	}
	
	public <T> void set(String name, T d) {
	}
	
	public <T> T get(String name) {
		return get(name, () -> {
			return null;
		});
	}
	
	public <T> T get(String name, Prov<T> def) {
		return null;
	}
	
	public <T> void del(String name) {
	}
	
	public <T> int size() {
		return 0;
	}
	
	public <T> String[] keys() {
		return new String[]{};
	}
	
	public <T> void clear() {
	}
	
	public <T> HashMap getJson(String name) {
		return getJson(name, HashMap.class, () -> null);
	}
	
	public <T> T getJson(String name, Class<T> cls) {
		return getJson(name, cls, () -> null);
	}
	
	public <T> T getJson(String name, Class<T> cls, Prov<T> def) {
		try {
			var d = get(name);
			if (d == null) return def.get();
			var res = mapper.readValue((String) d, cls);
			return res;
		} catch (Exception e) {
			System.out.println(e);
			return def.get();
		}
	}
	
	public <T> void setJson(String name, Object data) {
		try {
			set(name, mapper.writeValueAsString(data));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
