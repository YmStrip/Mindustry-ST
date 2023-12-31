package st.plugin.story;

import layer.annotations.Module;
import layer.instance.InstanceFactory;
import layer.layer.Logger;
import layer.module.LayerModule;
import st.plugin.story.contoller.First;
import st.plugin.story.provider.PlanetG;
import st.plugin.story.provider.PlanetProvider;
import st.plugin.story.provider.StoryEvent;

@Module(name = "story")
public class Story extends LayerModule {
	public Story() {
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
