package st.addon.stransport;

import layer.annotations.Module;
import layer.module.LayerModule;
import st.addon.content.SContent;
import st.addon.stransport.controller.TransportTech;
import st.addon.stransport.provider.T1Transports;
import st.addon.stransport.provider.TransportPreset;
import st.ht.quantum.Quantum;
import st.provider.place.SPlace;

@Module
public class STransport extends LayerModule {
	public STransport() {
		imports(
			SPlace.class,
			Quantum.class,
			SContent.class
		);
		provider(
			new TransportPreset(),
			new T1Transports()
		);
		controller(
			new TransportTech()
		);
	}
}
