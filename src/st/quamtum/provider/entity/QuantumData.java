package st.quamtum.provider.entity;

import java.util.HashMap;

public class QuantumData {
	
	public QuantumData() {
	}
	//tmd
	public float max = 999999999999f;
	public float min = 0;
	
	public void updateRange() {
		data.forEach((name, d) -> {
			if (d < min) {
				data.put(name, min);
				return;
			}
			if (d > max) {
				data.put(name, max);
				return;
			}
		});
	}
	
	public void setMin(float f) {
		min = f;
		updateRange();
	}
	
	public void setMax(float f) {
		max = f;
		updateRange();
	}
	
	
	public HashMap<String, Float> data = new HashMap<>();
	
	public Float get(String name) {
		return data.getOrDefault(name, min);
	}
	
	public void set(String name, Float def) {
		if (def > max) def = max;
		if (def < min) def = min;
		float vs = data.getOrDefault(name, min);
		data.put(name, def);
	}
	
	public void add(String name, Float def) {
		var v = data.get(name);
		if (v == null) v = min;
		float vs = v + def;
		if (vs > max) vs = max;
		if (vs < min) vs = min;
		data.put(name, vs);
	}
	
	public float take(String name, float count) {
		float n = get(name);
		if (n < count) {
			del(name);
			return n;
		} else {
			add(name, -count);
			return count;
		}
	}
	
	public void del(String name) {
		float vs = Math.min(data.getOrDefault(name, min),min);
		data.put(name,vs);
	}
	
	
	public void clear() {
		data.forEach((name, d) -> {
			del(name);
		});
	}
	
	public void change(String name, float ch) {
	
	}
	
	public void deploy() {
	
	}
}
