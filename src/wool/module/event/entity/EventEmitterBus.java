package wool.module.event.entity;

public class EventEmitterBus {
	@FunctionalInterface()
	public interface EventEmitterBusOn {
		public void on();
	}

	public EventEmitter event = new EventEmitter();
	public void on(EventEmitterBusOn call) {
		event.on("bus", (object) -> {
			call.on();
		});
	}
	public void once(EventEmitterBusOn call) {
		event.once("bus", (object) -> {
			call.on();
		});
	}
	public void emit() {
		event.emit("bus",null);
	}
}

