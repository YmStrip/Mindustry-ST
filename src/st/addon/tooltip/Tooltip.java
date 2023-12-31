package st.addon.tooltip;

import layer.annotations.Module;
import layer.module.LayerModule;
import st.addon.tooltip.provider.ToolTipBar;

@Module(name = "tooltip")
public class Tooltip extends LayerModule {
	public Tooltip() {
		provider(
			new ToolTipBar()
		);
	}
}
