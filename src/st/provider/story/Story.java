package st.provider.story;

import layer.annotations.Module;
import layer.instance.InstanceFactory;
import layer.layer.Logger;
import layer.module.LayerModule;
import st.provider.story.contoller.First;
import st.provider.story.provider.PlanetG;
import st.provider.story.provider.PlanetProvider;
import st.provider.story.provider.StoryEvent;

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
