package st.dao;

import arc.util.io.Reads;
import arc.util.io.Writes;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;

public class LibgdxDao {
	public static ObjectMapper objectMapper = new ObjectMapper();
	
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
			var d = "" + objectMapper.writeValueAsString(data);
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
			return (T) objectMapper.readValue(d, def.getClass());
		} catch (Exception e) {
			//System.out.println("读错误：" + e);
			return def;
		}
	}
}
