package st.addon.wall;

import layer.annotations.Module;
import layer.module.LayerModule;
import st.addon.content.SContent;
import st.addon.wall.provider.TWall;

@Module(name = "wall")
public class SWall extends LayerModule {
	public SWall() {
		imports(
			SContent.class
		);
		provider(
			new TWall()
		);
		controller();
	}
}
