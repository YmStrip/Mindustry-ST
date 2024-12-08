package wool.module.event.entity;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class EventPipe {
	public HashMap<String, ArrayList<EventEmitter.EventEmitterOn<?>>> data = new HashMap<>();
	private ArrayList<EventEmitter.EventEmitterOn<?>> preset(String key) {
		return data.computeIfAbsent(key, k -> new ArrayList<>());
	}
	public abstract void setOnce(String key, EventEmitter.EventEmitterOn<?> on);
	public abstract void set(String key, EventEmitter.EventEmitterOn<?> on);
	public abstract void del(String key, EventEmitter.EventEmitterOn<?> on);
	public void setOnceHook(String key, EventEmitter.EventEmitterOn<?> on) {
		var array = preset(key);
		if (!array.contains(on)) {
			array.add(on);
			set(key, on);
		}
	}
	public void setHook(String key, EventEmitter.EventEmitterOn<?> on) {
		var array = preset(key);
		if (!array.contains(on)) {
			array.add(on);
			set(key, on);
		}
	}
	public void delHook(String key, EventEmitter.EventEmitterOn<?> on) {
		var array = preset(key);
		array.remove(on);
		del(key, on);
		if (array.isEmpty()) this.data.remove(key);
	}
	public void clear() {
		for (var i : data.entrySet()) {
			var key = i.getKey();
			var array = i.getValue();
			for (var j = array.size(); --j >= 0; ) {
				delHook(key, array.get(j));
				array.remove(j);
			}
		}
	}
}

