package st.plot.contoller;

import layer.annotations.Controller;
import layer.annotations.Import;
import layer.annotations.Require;
import layer.extend.LayerController;
import mindustry.content.TechTree;
import st.addon.content.SContent;
import st.addon.content.provider.TechProvider;
import st.plot.provider.PlanetProvider;

@Controller
public class PlanetTech extends LayerController {
	@Require(cls = PlanetProvider.class)
	PlanetProvider planets;
	@Import(cls = SContent.class)
	@Require(cls = TechProvider.class)
	TechProvider tech;
	
	@Override
	public void run() {
		planets.五行星.techNode = tech.root.techNode;
	}
}
