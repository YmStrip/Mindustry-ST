package st.plot;

import layer.annotations.Module;
import layer.instance.InstanceFactory;
import layer.layer.Logger;
import layer.module.LayerModule;
import st.addon.content.SContent;
import st.plot.contoller.First;
import st.plot.provider.PlanetG;
import st.plot.provider.PlanetProvider;
import st.plot.provider.StoryEvent;

@Module(name = "story")
public class Story extends LayerModule {
	public Story() {
		imports(
			SContent.class
		);
		provider(
			new PlanetProvider(),
			new PlanetG(),
			new StoryEvent()
		);
		controller(
			new First()
		);
	}
	
	@Override
	public void handelInstance(InstanceFactory f) {
		f
			.instance("logger", t -> {
				t
					.implement(new Logger())
					.config("name", "Planet");
			});
	}
}
