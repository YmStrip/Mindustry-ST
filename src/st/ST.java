package st;


import layer.module.ModuleFactory;
import st.addon.attack.SAttack;
import st.addon.effect.SEffect;
import st.addon.prod.SProd;
import st.quamtum.block.QuantumB;
import st.addon.content.SContent;
import st.addon.power.SPower;
import st.provider.story.Story;
import st.provider.ui.UI;
import st.provider.command.Command;
import st.provider.place.SPlace;
import st.quamtum.provider.Quantum;
import st.provider.shop.Shop;

public class ST {
	public static String name = "st";
	public static boolean debug = true;
	public static ModuleFactory module;
	
	public static ModuleFactory init() {
		module = new ModuleFactory()
			.include(
				//附加内容
				new SPlace(),
				new Shop(),
				new UI(),
				new Command(),
				//效果
				//量子网络
				new QuantumB(),
				new Quantum(),
				//内容
				new Story(),
				new SEffect(),
				new SProd(),
				new SPower(),
				new SAttack(),
				new SContent()
			)
			.deploy()
		;
		return module;
	}
}
