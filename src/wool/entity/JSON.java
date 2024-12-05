package wool.entity;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;


import javax.lang.model.type.NullType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JSON {
	public static HashMap<Object, Object> parse(String data) {
		try {
			var parser = new JSONParser();
			var obj = (JSONObject) parser.parse(data);
			return new HashMap(obj);
		} catch (Exception e) {
			return new HashMap();
		}
	}
	public static String stringify(Object data) {
		return stringify(data, false, "");
	}
	public static String stringify(Object data, NullType br) {
		return stringify(data, true, " ");
	}
	public static String stringify(Object data, NullType br, String split) {
		return stringify(data, true, split);
	}
	public static String stringify(Object data, boolean br) {
		return stringify(data, br, " ");
	}
	public static String stringify(Object data, boolean br, String split) {
		return stringify(data, 1, split, br);
	}

	private static String stringify(Object data, int rep, String split, boolean br) {
		if (data.getClass().isArray()) return stringify((Object[]) data, rep, split, br);
		if (data instanceof ArrayList o) return stringify(o.toArray(), rep, split, br);
		if (data instanceof Map<?, ?> o) return stringify((Map) o, rep, split, br);
		return stringify(object(data), rep, split, br);
	}
	private static HashMap object(Object data) {
		var h = new HashMap<>();
		var fi = data.getClass().getFields();
		for (var i : fi) {
			try {
				i.setAccessible(true);
				var name = i.getName();
				var value = i.get(data);
				if (value instanceof Map<?, ?>) {
					h.put(name, value);
				} else if (value instanceof ArrayList<?> ||value.getClass().isArray()) {
					h.put(name, value);
				} else h.put(name, value);
			} catch (Exception e) {

			}
		}
		return h;
	}

	private static String stringify(Map<Object, Object> data, int rep, String split, boolean br) {
		var str = new ArrayList<String>();
		str.add("{");
		var has = false;
		var first = false;
		for (var entry : data.entrySet()) {
			has = true;
			if (!first) {
				first = true;
				str.add("\n");
			}
			var key = entry.getKey();
			var value = entry.getValue();
			str.add(split.repeat(rep));
			str.add("\"" + key + "\"");
			str.add(":");
			try {
				if (value.getClass().isArray()) {
					str.add(stringify((Object[]) value, rep + 1, split, br));
				} else if (value instanceof ArrayList v) {
					str.add(stringify(v.toArray(), rep + 1, split, br));
				} else if (value instanceof Map v) {
					str.add(stringify(v, rep + 1, split, br));
				} else if (value instanceof String v) {
					str.add("\"" + JSONValue.escape(v) + "\"");
				} else if (value instanceof Number || value instanceof Boolean) {
					str.add(JSONValue.escape(value + ""));
				} else if (value == null) {
					str.add(JSONValue.escape("null"));
				} else {
					str.add(stringify(value, rep + 1, split, br));
				}
			} catch (Exception e) {
				str.add("null");
			}
			str.add("," + (br ? "\n" : ""));
		}
		if (has) {
			str.remove(str.size() - 1);
			if (br) str.add("\n");
		}
		str.add(split.repeat(rep - 1));
		str.add("}");
		return String.join("", str);
	}

	private static String stringify(Object[] data, int rep, String split, boolean br) {
		var str = new ArrayList<String>();
		str.add("[");
		for (int i = -1, l = data.length; ++i < l; ) {
			if (br && (i == 0)) {
				str.add("\n");
			}
			str.add(split.repeat(rep));
			var value = data[i];
			if (value.getClass().isArray()) {
				str.add(split.repeat(rep));
				str.add(stringify((Object[]) value, rep + 1, split, br));
			} else if (value instanceof ArrayList v) {
				str.add(split.repeat(rep));
				str.add(stringify(v.toArray(), rep + 1, split, br));
			} else if (value instanceof Map v) {
				str.add(split.repeat(rep));
				str.add(stringify(v, rep + 1, split, br));
			} else if (value == null) {
				str.add(JSONValue.escape("null"));
			} else if (value instanceof String v) {
				str.add("\"" + JSONValue.escape(v) + "\"");
			} else if (value instanceof Number || value instanceof Boolean) {
				str.add(JSONValue.escape(value + ""));
			} else {
				str.add(stringify(value, rep + 1, split, br));
			}
			str.add((i < l - 1 ? "," : "") + (br ? "\n" : ""));
		}
		str.add(split.repeat(rep - 1));
		str.add("]");
		return String.join("", str);
	}
}