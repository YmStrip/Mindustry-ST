package st.addon.item;

import layer.annotations.Module;
import layer.instance.InstanceFactory;
import layer.layer.Logger;
import layer.module.LayerModule;
import st.addon.entity.Entity;
import st.addon.item.controller.AttrController;
import st.addon.item.controller.ItemToolBar;
import st.addon.item.controller.ItemWallController;
import st.addon.item.provider.AttrProvider;
import st.addon.item.provider.EnvProvider;
import st.addon.item.provider.ItemProvider;
import st.addon.item.provider.LiquidProvider;
import st.addon.tooltip.Tooltip;

@Module(name = "item")
public class ITEM extends LayerModule {
	public ITEM() {
		imports(
			Tooltip.class,
			Entity.class
		);
		controller(
			new ItemWallController(),
			new ItemToolBar(),
			new AttrController()
		);
		provider(
			new AttrProvider(),
			new LiquidProvider(),
			new ItemProvider(),
			new EnvProvider()
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
