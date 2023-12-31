package st.plugin.place;

import layer.annotations.Module;
import layer.instance.InstanceFactory;
import layer.layer.Logger;
import layer.module.LayerModule;
import st.plugin.place.controller.PlaceEvent;
import st.plugin.place.provider.PlaceMap;

@Module(name = "place")
public class Place extends LayerModule {
	public Place() {
		provider(
			new PlaceMap()
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
