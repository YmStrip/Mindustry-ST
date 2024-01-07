package st.ht.quantum.entity;

import java.util.Map;

//一个请求列表，用于处理相关
public class QuantumRequest {
	public QuantumData items = new QuantumData();
	public QuantumData liquids = new QuantumData();
	public QuantumData units = new QuantumData();
	
	public float count() {
		var c = 0;
		for (var i : items.data.entrySet()) {
			c += i.getValue();
		}
		for (var i : liquids.data.entrySet()) {
			c += i.getValue();
		}
		for (var i : units.data.entrySet()) {
			c += i.getValue();
		}
		return c;
	}
	
	public boolean empty() {
		return count() <= 0.01f;
	}
}
