package st.addon.item.controller;

import layer.annotations.Controller;
import layer.annotations.Require;
import layer.extend.LayerController;
import mindustry.content.Blocks;
import st.addon.item.provider.AttrProvider;

@Controller
public class AttrController extends LayerController {
	@Require(cls = AttrProvider.class)
	AttrProvider attrs;
	
	public void 以太() {
		//核心区 0.8f
		Blocks.coreZone.attributes.set(attrs.以太, 10f);
		Blocks.water.attributes.set(attrs.以太, 1f);
		Blocks.sandWater.attributes.set(attrs.以太, 2.5f);
		Blocks.taintedWater.attributes.set(attrs.以太, 0.5f);
		Blocks.darksandWater.attributes.set(attrs.以太, 2.5f);
		Blocks.deepTaintedWater.attributes.set(attrs.以太, 0.5f);
		Blocks.deepwater.attributes.set(attrs.以太, 3f);
	}
	
	@Override
	public void run() {
		以太();
	}
}
