package st.ht.quantum.entity;

import java.util.HashMap;

//自定义值储存表
public class QuantumValue {
	HashMap<String, Object> data = new HashMap<>();
	
	@Override
	public String toString() {
		try {
			return data+"";
		} catch (Exception e) {
			return "{}";
		}
	}
	public void clear () {
		data.clear();
	}
	public void change(String name,float value) {}
	public void deploy(){}
}
