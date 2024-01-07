package st.addon.stransport.controller;


import layer.annotations.Controller;
import layer.annotations.Import;
import layer.annotations.Require;
import layer.extend.LayerController;
import st.addon.content.provider.TechProvider;
import st.ht.quantum.provider.QuantumBlocks;
import st.addon.content.SContent;

@Controller
public class TransportTech extends LayerController {
	@Import(cls = SContent.class)
	@Require(cls = TechProvider.class)
	TechProvider tech;
	@Override
	public void run() {
	
	}
}
