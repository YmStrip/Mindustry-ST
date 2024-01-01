package st.addon.effect.controller;

import layer.annotations.Controller;
import layer.annotations.Import;
import layer.annotations.Require;
import layer.extend.LayerController;
import mindustry.content.Blocks;
import st.addon.content.SContent;
import st.addon.content.provider.TechProvider;
import st.addon.effect.provider.T1Effect;

@Controller
public class EffectTech extends LayerController {
	@Import(cls = SContent.class)
	@Require(cls = TechProvider.class)
	TechProvider tech;
	@Require(cls = T1Effect.class)
	T1Effect t1;
	
	@Override
	public void run() {
		tech
			.tech(t1.前哨核心)
			.parent(Blocks.coreShard);
	}
}
