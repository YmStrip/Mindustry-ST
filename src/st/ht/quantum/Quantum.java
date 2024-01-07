package st.ht.quantum;

import layer.annotations.Module;
import layer.instance.InstanceFactory;
import layer.layer.Logger;
import layer.module.LayerModule;
import st.addon.content.SContent;
import st.dao.DaoSector;
import st.dao.DaoSet;
import st.ht.quantum.controller.QuantumController;
import st.ht.quantum.controller.QuantumTech;
import st.ht.quantum.provider.QuantumBlocks;
import st.ht.quantum.provider.QuantumMap;
import st.ht.quantum.provider.QuantumStore;
import st.ht.quantum.provider.QuantumUI;
import st.provider.place.SPlace;

@Module(name = "quantum")
public class Quantum extends LayerModule {
	public Quantum() {
		imports(
			SContent.class,
			SPlace.class
		);
		controller(
			new QuantumController(),
			new QuantumTech()
		);
		provider(
			new QuantumBlocks(),
			new QuantumUI(),
			new QuantumMap(),
			new QuantumStore()
		);
	}
	
	@Override
	public void handelInstance(InstanceFactory f) {
		f.instance("logger", t -> {
			t.implement(new Logger());
			t.config("name", "Quantum");
		});
		//Vars.state.rules.sector.saveInfo();
		//Vars.state.rules.sector.save.meta.tags.get("quantum")
		//for(var i in Vars.state.rules.sector.save.meta.tags.keys()) {print(i)}
		
		f.instance("daoSector", t -> {
			t
				.implement(new DaoSector())
				.config("name", "quantum")
			;
		});
		f.instance("daoSet", t -> {
			t
				.implement(new DaoSet())
				.config("name", "quantum")
			;
		});
	}
}
