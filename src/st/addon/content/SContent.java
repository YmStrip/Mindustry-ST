package st.addon.content;

import layer.annotations.Module;
import layer.instance.InstanceFactory;
import layer.layer.Logger;
import layer.module.LayerModule;
import st.addon.content.controller.AttrController;
import st.addon.content.controller.ItemToolBar;
import st.addon.content.provider.*;

@Module(name = "content")
public class SContent extends LayerModule {
	public SContent() {
		controller(
			new ItemToolBar(),
			new AttrController()
		);
		provider(
			new ValueProvider(),
			new TooltipProvider(),
			new TechProvider(),
			new AttrProvider(),
			new LiquidProvider(),
			new ItemProvider(),
			new FloorProvider()
		);
	}
	
	@Override
	public void handelInstance(InstanceFactory f) {
		f.instance("logger", t -> t
			.implement(new Logger())
			.config("name", "item")
		);
	}
}
