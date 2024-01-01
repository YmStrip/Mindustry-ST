package st.provider.place;

import layer.annotations.Module;
import layer.instance.InstanceFactory;
import layer.layer.Logger;
import layer.module.LayerModule;
import st.provider.place.controller.PlaceEvent;
import st.provider.place.provider.PlaceProvider;

@Module(name = "place")
public class SPlace extends LayerModule {
	public SPlace() {
		provider(
			new PlaceProvider()
		);
		controller(
			new PlaceEvent()
		);
	}
	
	@Override
	public void handelInstance(InstanceFactory f) {
		f.instance("logger", t ->
			t
				.implement(new Logger())
				.config("name", "Place")
		);
	}
}
