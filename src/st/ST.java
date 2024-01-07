package st;


import arc.Core;
import layer.module.ModuleFactory;
import st.addon.turret.STurret;
import st.addon.unit.SUnit;
import st.provider.attack.SAttack;
import st.addon.drill.SDrill;
import st.addon.effect.SEffect;
import st.addon.prod.SProd;
import st.addon.content.SContent;
import st.addon.power.SPower;
import st.addon.stransport.STransport;
import st.addon.wall.SWall;
import st.ht.console.SConsole;
import st.plot.Story;
import st.provider.place.SPlace;
import st.ht.quantum.Quantum;
import st.provider.shop.Shop;

public class ST {
	public static String name = "st";
	
	public static String name(String n) {
		return Core.bundle.get(name + "-" + n);
	}
	
	public static String name(String type, String n) {
		return Core.bundle.get(type + "." + name + "-" + n);
	}
	public static String stat(String name) {
		return name("stat",name);
	}
	public static String item(String name) {
		return name("item",name);
	}
	public static String stage(String name) {
		return name("stage",name);
	}
	public static String block(String name) {
		return name("block",name);
	}
	public static boolean debug = true;
	public static ModuleFactory module;
	
	public static ModuleFactory init() {
		module = new ModuleFactory()
			.include(
				//附加内容
				new SPlace(),
				new Shop(),
				new SConsole(),
				new SAttack(),
				//效果
				//量子网络
				new Quantum(),
				//内容
				new Story(),
				new SUnit(),
				new SWall(),
				new STurret(),
				new STransport(),
				new SDrill(),
				new SEffect(),
				new SProd(),
				new SPower(),
				new SContent()
			)
			.deploy()
		;
		return module;
	}
}
