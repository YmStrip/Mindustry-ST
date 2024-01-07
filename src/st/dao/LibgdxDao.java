package st.dao;

import arc.util.io.Reads;
import arc.util.io.Writes;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;


public class LibgdxDao {
	public static ObjectMapper mapper = new ObjectMapper();
	
	public static void writeStr(Writes w, String data) {
		if (data == null) data = "";
		byte[] BData = data.getBytes();
		w.i(BData.length);
		if (BData.length > 0) w.b(BData);
	}
	
	public static String readStr(Reads r) {
		var length = r.i();
		if (length > 0) {
			byte[] BData = r.b(length);
			return new String(BData);
		}
		return "";
	}
	
	public static void writeJson(Writes w, Object data) {
		try {
			var d = "" + mapper.writeValueAsString(data);
			//System.out.println(d);
			//System.out.println(Arrays.toString(d.getBytes()));
			writeStr(w, d);
		} catch (Exception e) {
			//System.out.println("写错误：" + e);
			LibgdxDao.writeStr(w, "b");
		}
	}
	
	public static <T> T readJson(Reads r, T def) {
		try {
			var d = readStr(r);
			//System.out.println(d);
			return (T) mapper.readValue(d, def.getClass());
		} catch (Exception e) {
			//System.out.println("读错误：" + e);
			return def;
		}
	}
	
	//数据绑定器[轮子]
	public static class dao {
		public class entry<T> {
			daoS<T> set;
			daoG<T> get;
			daoDef def;
			String name;
			
			public entry<T> name(String name) {
				this.name = name;
				return this;
			}
			
			public entry<T> get(daoG<T> daoG) {
				this.get = daoG;
				return this;
			}
			
			public entry<T> set(daoS<T> daoS) {
				this.set = daoS;
				return this;
			}
			
			public entry<T> def(daoDef daoDef) {
				this.def = daoDef;
				return this;
			}
			
			public entry(String name) {
				this.name = name;
			}
		}
		
		public ArrayList<entry> data = new ArrayList();
		
		@FunctionalInterface
		public interface daoS<T> {
			T get();
		}
		
		@FunctionalInterface
		public interface daoG<T> {
			void get(T T);
		}
		
		@FunctionalInterface
		public interface daoDef {
			void get();
		}
		
		@FunctionalInterface
		public interface prod<T> {
			void get(entry<T> T);
		}
		
		public dao f(String name, prod<Float> c) {
			var s = new entry<Float>(name);
			c.get(s);
			data.add(s);
			return this;
		}
		
		public dao i(String name, prod<Integer> c) {
			var s = new entry<Integer>(name);
			c.get(s);
			data.add(s);
			return this;
		}
		
		public dao bool(String name, prod<Boolean> c) {
			var s = new entry<Boolean>(name);
			c.get(s);
			data.add(s);
			return this;
		}
		
		public dao any(String name, prod<Object> c) {
			var s = new entry<Object>(name);
			c.get(s);
			data.add(s);
			return this;
		}
		
		public void read(Reads read) {
			var d = readJson(read, new HashMap<>());
			data.forEach(i -> {
				try {
					i.get.get(d.get(i.name));
				} catch (Exception e) {
					i.def.get();
				}
			});
		}
		
		public void write(Writes write) {
			var u = new HashMap<>();
			for (var i : data) {
				u.put(i.name, i.set.get());
			}
			writeJson(write, u);
		}
	}
}
