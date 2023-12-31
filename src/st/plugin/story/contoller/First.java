package st.plugin.story.contoller;

import layer.annotations.Controller;
import layer.annotations.Require;
import layer.extend.LayerController;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.type.SectorPreset;
import st.plugin.story.provider.PlanetProvider;
import st.plugin.story.provider.StoryEvent;

@Controller
public class First extends LayerController {
	@Require(cls = PlanetProvider.class)
	PlanetProvider planets;
	@Require(cls = StoryEvent.class)
	StoryEvent story;
	
	@Override
	public void run() {
		new SectorPreset("1104防御节点", planets.五行星, 1) {{
			alwaysUnlocked = true;
			//日志事件
			story.on(this, EventType.SectorLaunchEvent.class, (sector, e) -> {
				
				//Log Info
				System.out.println("HHH");
				System.out.println(Vars.player);
				Vars.player.sendMessage("HHH");
			});
		}};
	}
}
