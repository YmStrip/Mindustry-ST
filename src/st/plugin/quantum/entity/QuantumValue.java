package st.plugin.quantum.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

public class QuantumValue {
	HashMap<String, Object> data = new HashMap<>();
	
	@Override
	public String toString() {
		try {
			return new ObjectMapper().writeValueAsString(data);
		} catch (JsonProcessingException e) {
			return "{}";
		}
	}
	public void clear () {
		data.clear();
	}
	public void change(String name,float value) {}
	public void deploy(){}
}
