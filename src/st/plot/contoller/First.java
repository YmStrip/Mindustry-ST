package st.plot.contoller;

import arc.Events;
import layer.annotations.Controller;
import layer.annotations.Import;
import layer.annotations.Require;
import layer.extend.LayerController;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.type.SectorPreset;
import st.addon.content.SContent;
import st.addon.content.provider.TechProvider;
import st.plot.provider.PlanetProvider;
import st.plot.provider.StoryEvent;

@Controller
public class First extends LayerController {
	@Require(cls = PlanetProvider.class)
	PlanetProvider planets;
	@Require(cls = StoryEvent.class)
	StoryEvent story;
	@Import(cls = SContent.class)
	@Require(cls = TechProvider.class)
	TechProvider tech;
	
	public void map_tech() {
		tech
			.tech(废弃空间站)
			.parent(tech.root)
			.inChild(孢子矿洞)
			.child(元素沼泽)
			.child(幽暗裂谷)
		;
	}
	
	public SectorPreset 废弃空间站;
	public SectorPreset 元素沼泽;
	public SectorPreset 孢子矿洞;
	public SectorPreset 幽暗裂谷;
	
	@Override
	public void run() {
		Events.on(EventType.ContentInitEvent.class, r -> {
			map_tech();
		});
		
		废弃空间站 = new SectorPreset("1-废弃空间站", planets.五行星, 1) {{
			alwaysUnlocked = true;
			//日志事件
			story.on(this, EventType.SectorLaunchEvent.class, (sector, e) -> {
				
				//Log Info
				System.out.println("HHH");
				System.out.println(Vars.player);
				Vars.player.sendMessage("HHH");
			});
		}};
		元素沼泽 = new SectorPreset("1-元素沼泽", planets.五行星, 97) {{
		}};
		孢子矿洞 = new SectorPreset("1-孢子矿洞", planets.五行星, 94) {{
		}};
		幽暗裂谷 = new SectorPreset("1-幽暗裂谷", planets.五行星, 92) {{
		}};
	}
}
