package st.plugin.quantum;

import layer.annotations.Module;
import layer.instance.InstanceFactory;
import layer.layer.Logger;
import layer.module.LayerModule;
import st.dao.DaoMapDB;
import st.plugin.quantum.controller.QuantumWorldEvent;
import st.plugin.quantum.provider.QuantumMapTile;
import st.plugin.quantum.provider.QuantumWorld;

@Module(name = "quantum")
public class Quantum extends LayerModule {
	public Quantum() {
		controller(
			new QuantumWorldEvent()
		);
		provider(
			new QuantumMapTile(),
			new QuantumWorld()
		);
	}
	
	@Override
	public void handelInstance(InstanceFactory f) {
		var database = DaoMapDB.defineDB("quantum.db");
		f.instance("logger", t -> {
			t.implement(new Logger());
			t.config("name", "Quantum");
		});
		f.instance("sector",t->{
			t
				.implement(new DaoMapDB())
				.config("name","sector")
				.config("db",database)
			;
		});
	}
}
