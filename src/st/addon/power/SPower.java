package st.addon.power;

import layer.annotations.Module;
import layer.module.LayerModule;
import st.addon.item.ITEM;
import st.addon.power.contoller.PowerTech;
import st.addon.power.provider.PowerPreset;
import st.addon.power.provider.Powers;
import st.addon.tech.STech;
import st.addon.tooltip.Tooltip;
import st.plugin.value.SValue;

@Module(name = "power")
public class SPower extends LayerModule {
	public SPower() {
		imports(
			SValue.class,
			STech.class,
			ITEM.class,
			Tooltip.class
		);
		provider(
			new PowerPreset(),
			new Powers()
		);
		controller(
			new PowerTech()
		);
	}
}
