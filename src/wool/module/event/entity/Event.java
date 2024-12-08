package wool.module.event.entity;

public class Event<T> {
	public String key = "event";
	public void emit(EventEmitter emitter, T value) {
		emitter.emit(this.key, value);
	}
	public void on(EventEmitter emitter, EventEmitter.EventEmitterOn<T> on) {
		emitter.on(this.key, on);
	}
	public void on(EventEmitter emitter, EventEmitter.EventEmitterOn<T> on, EventEmitterBus bus) {
		emitter.on(this.key, on, bus);
	}
	public void once(EventEmitter emitter, EventEmitter.EventEmitterOn<T> on) {
		emitter.once(this.key, on);
	}
	public void once(EventEmitter emitter, EventEmitter.EventEmitterOn<T> on, EventEmitterBus bus) {
		emitter.once(this.key, on, bus);
	}
	public void off(EventEmitter emitter, EventEmitter.EventEmitterOn<T> on) {
		emitter.off(this.key, on);
	}
	public void off(EventEmitter emitter) {
		emitter.off(this.key);
	}
}

