package st;


import layer.module.ModuleFactory;
import mindustry.world.blocks.defense.ForceProjector;
import st.addon.attack.SAttack;
import st.addon.block_io.BlockIO;
import st.addon.block_prod.BlockProd;
import st.addon.entity.Entity;
import st.addon.item.ITEM;
import st.addon.power.SPower;
import st.addon.power.provider.Powers;
import st.addon.tech.STech;
import st.plugin.story.Story;
import st.addon.seffect.SEffect;
import st.addon.tooltip.Tooltip;
import st.addon.ui.UI;
import st.plugin.command.Command;
import st.plugin.place.Place;
import st.plugin.quantum.Quantum;
import st.plugin.shop.Shop;
import st.plugin.value.SValue;

public class ST {
	public static String name = "st";
	public static boolean debug = true;
	public static ModuleFactory module;
	
	public static ModuleFactory init() {
		module = new ModuleFactory()
			.include(
				//最高优先级,注册store
				new Entity(),
				//Bar模块
				new Tooltip(),
				//最大放置模块
				new Place(),
				//商店模块
				new Shop(),
				//量子网络模块
				new Quantum(),
				//UI模块
				new UI(),
				//指令模块
				new Command(),
				//星球
				new Story(),
				//物品
				new ITEM(),
				//输入输出方块
				new BlockIO(),
				//生产方块
				new BlockProd(),
				//最低优先级-静态效果
				new SPower(),
				new SAttack(),
				new SEffect(),
				new SValue(),
				new STech()
			)
			.deploy()
		;
		return module;
	}
}
