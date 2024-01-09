package st.addon.stransport.controller;


import arc.Events;
import layer.annotations.Controller;
import layer.annotations.Import;
import layer.annotations.Require;
import layer.extend.LayerController;
import mindustry.content.Blocks;
import mindustry.game.EventType;
import st.addon.content.provider.TechProvider;
import st.addon.stransport.provider.T1Transports;
import st.ht.quantum.provider.QuantumBlocks;
import st.addon.content.SContent;

@Controller
public class TransportTech extends LayerController {
	@Import(cls = SContent.class)
	@Require(cls = TechProvider.class)
	TechProvider tech;
	@Require(cls = T1Transports.class)
	T1Transports t1;
	
	public void tech_mod() {
		tech
			.tech(t1.超导卸载器)
			.parent(Blocks.unloader);
	}
	
	@Override
	public void run() {
		Events.on(EventType.ContentInitEvent.class, e -> {
			tech_mod();
		});
	}
}
