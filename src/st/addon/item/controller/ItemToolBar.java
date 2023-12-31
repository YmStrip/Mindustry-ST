package st.addon.item.controller;

import arc.Events;
import layer.annotations.Controller;
import layer.annotations.Import;
import layer.annotations.Require;
import layer.extend.LayerController;
import mindustry.game.EventType;
import st.addon.item.provider.ItemProvider;
import st.addon.tooltip.Tooltip;
import st.addon.tooltip.provider.ToolTipBar;

@Controller
public class ItemToolBar extends LayerController {
	@Require(cls = ItemProvider.class)
	ItemProvider itemProvider;
	@Import(cls = Tooltip.class)
	@Require(cls = ToolTipBar.class)
	ToolTipBar toolTipBar;
	@Override
	public void run() {
		Events.on(EventType.ContentInitEvent.class, e -> {
			toolTipBar.init(itemProvider.纳米碳管);
			toolTipBar.init(itemProvider.反物质);
			toolTipBar.init(itemProvider.金元素);
			toolTipBar.init(itemProvider.木元素);
			toolTipBar.init(itemProvider.水元素);
			toolTipBar.init(itemProvider.火元素);
			toolTipBar.init(itemProvider.土元素);
			toolTipBar.init(itemProvider.光元素);
			toolTipBar.init(itemProvider.暗元素);
		});
	}
}
