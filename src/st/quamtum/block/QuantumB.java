package st.quamtum.block;

import layer.annotations.Module;
import layer.module.LayerModule;
import st.provider.place.SPlace;
import st.provider.ui.UI;
import st.quamtum.block.controller.QuantumBlockTech;
import st.quamtum.block.provider.QuantumBlocks;
import st.addon.content.SContent;
import st.quamtum.provider.Quantum;

@Module(name = "quantum-b")
public class QuantumB extends LayerModule {
	public QuantumB() {
		imports(
			Quantum.class,
			SPlace.class,
			UI.class,
			SContent.class
		);
		controller(
			new QuantumBlockTech()
		);
		provider(
			new QuantumBlocks()
		);
	}
}
