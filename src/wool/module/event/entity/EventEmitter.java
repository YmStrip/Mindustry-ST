package wool.module.event.entity;

import java.util.ArrayList;
import java.util.HashMap;

public class EventEmitter {
	@FunctionalInterface
	public interface EventEmitterOn<T> {
		void call(Object copy);
	}
	public ArrayList<EventEmitterOn<?>> presetOn(String key) {
		return onList.computeIfAbsent(key, k -> new ArrayList<>());
	}
	public ArrayList<EventEmitterOn<?>> presetOnce(String key) {
		return onceList.computeIfAbsent(key, k -> new ArrayList<>());
	}
	public HashMap<String, ArrayList<EventEmitterOn<?>>> onList = new HashMap<>();
	public HashMap<String, ArrayList<EventEmitterOn<?>>> onceList = new HashMap<>();
	public ArrayList<EventPipe> pipeList = new ArrayList<>();
	public void onHook(String key, EventEmitterOn<?> on) {
	}
	public <T> EventEmitterOn<T> on(String key, EventEmitterOn<T> on) {
		var array = presetOn(key);
		if (array.contains(on)) return on;
		array.add(on);
		onHook(key, on);
		return on;
	}
	public <T> EventEmitterOn<T> on(String key, EventEmitterOn<T> on, EventEmitterBus bus) {
		on(key, on);
		bus.once(() -> {
			off(key, on);
		});
		return on;
	}
	public void onceHook(String key, EventEmitterOn<?> on) {
	}
	public <T> EventEmitterOn<T> once(String key, EventEmitterOn<T> on) {
		var array = presetOnce(key);
		if (array.contains(on)) return on;
		array.add(on);
		onceHook(key, on);
		return on;
	}
	public <T> EventEmitterOn<T> once(String key, EventEmitterOn<T> on, EventEmitterBus bus) {
		once(key, on);
		bus.once(() -> {
			off(key, on);
		});
		return on;
	}
	public void emit(String key, Object value) {
		var once = onceList.get(key);
		var on = onList.get(key);
		if (once != null) {
			for (int i = once.size(); --i >= 0; ) {
				var e = once.get(i);
				e.call(value);
				off(key, e);
			}
		}
		if (on != null) {
			for (int i = on.size(); --i >= 0; ) {
				var e = on.get(i);
				e.call(value);
			}
		}
	}
	public static String key(Object o) {
		return System.identityHashCode(o) + "";
	}
	public void pipe(EventPipe pipe) {
		if (pipeList.contains(pipe)) return;
		for (var i : this.onList.entrySet()) {
			var key = key(i);
			for (var j : i.getValue()) {
				pipe.setHook(key, j);
			}
		}
		for (var i : this.onceList.entrySet()) {
			var key = key(i);
			for (var j : i.getValue()) {
				pipe.setOnceHook(key, j);
			}
		}
	}
	public void offHook(String key, EventEmitterOn<?> on) {
	}
	public void off(EventPipe pipe) {
		if (this.pipeList.contains(pipe)) {
			pipe.clear();
			this.pipeList.remove(pipe);
		}
	}
	public void off(String key, EventEmitterOn<?> on) {
		if (onceList.get(key) != null) {
			var array = presetOnce(key);
			array.remove(on);
		}
		if (onList.get(key) != null) {
			var array = presetOn(key);
			array.remove(on);
		}
		offHook(key, on);
	}
	public void off(String key) {
		if (onceList.get(key) != null) {
			var array = presetOnce(key);
			for (var i = array.size(); --i >= 0; ) {
				offHook(key, array.get(i));
				array.remove(i);
			}
		}
		if (onList.get(key) != null) {
			var array = presetOn(key);
			for (var i = array.size(); --i >= 0; ) {
				offHook(key, array.get(i));
				array.remove(i);
			}
		}
	}
	public void clear() {
		for (var i : this.onList.entrySet()) {
			var key = i.getKey();
			off(key);
		}
		for (var i : this.onceList.entrySet()) {
			var key = i.getKey();
			off(key);
		}
	}
	public void clearPipe() {
		for (var i = this.pipeList.size(); --i >= 0; ) {
			this.pipeList.get(i).clear();
			this.pipeList.remove(i);
		}
	}
}

