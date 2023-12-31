package st.addon.tooltip.contoller;

import arc.Events;
import layer.annotations.Controller;
import layer.annotations.Require;
import layer.extend.LayerController;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.type.Item;
import mindustry.world.Block;
import mindustry.world.meta.StatCat;


import st.addon.entity.entity.StBlock;
import st.addon.entity.entity.StItem;
import st.addon.tooltip.provider.ToolTipBar;

import java.util.HashMap;
import java.util.Objects;

@Controller
public class ToolTipController extends LayerController {
	@Require(cls = ToolTipBar.class)
	ToolTipBar toolTipBar;
	public HashMap<String, Boolean> isInit = new HashMap<>();
	
	public String name(Item item) {
		return "item-" + item.name;
	}
	
	public String name(Block item) {
		return "block-" + item.name;
	}
	
	public boolean isInit(Item item) {
		if (item instanceof StItem) return true;
		if (!Objects.equals(item.minfo.mod.name, "ST")) return true;
		return isInit.get(name(item));
	}
	
	public boolean isInit(Block block) {
		if (block instanceof StBlock) return true;
		if (!Objects.equals(block.minfo.mod.name, "ST")) return true;
		return isInit.get(name(block));
	}
	
	public void inject() {
		Vars.content.blocks().forEach((data) -> {
			if (isInit(data)) return;
			var Cat = new StatCat("st");
			//注入方块
			toolTipBar.showTechLevel(1, data.stats, Cat);
			isInit.put(name(data), true);
		});
	}
	
	
	@Override
	public void run() {
		//Inject ST TOOLBAR
		Events.on(EventType.ContentInitEvent.class, e -> {
			//注入所有block/unit
			//inject();
		});
	}
}
