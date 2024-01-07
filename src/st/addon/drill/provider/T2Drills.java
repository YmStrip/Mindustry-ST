package st.addon.drill.provider;

import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.content.Items;
import mindustry.type.ItemStack;
import mindustry.world.blocks.production.Drill;
import mindustry.world.blocks.production.Pump;
import st.addon.content.SContent;
import st.addon.content.provider.AttrProvider;
import st.addon.content.provider.ItemProvider;

@Provider
public class T2Drills extends LayerProvider {
	@Require(cls = DrillPreset.class)
	DrillPreset preset;
	@Import(cls = SContent.class)
	@Require(cls = ItemProvider.class)
	ItemProvider items;
	@Import(cls = SContent.class)
	@Require(cls = AttrProvider.class)
	AttrProvider attrs;
	//钻头
	public Drill 钻头 = new Drill("t2钻头") {{
		consumePower(9f);
		size = 3;
		tier = 5;
		drillTime = 240 / (9 * 0.5f);
	}};
	//泵
	public Pump 泵 = new Pump("t2泵") {{
		consumePower(8f);
		pumpAmount = 0.4f;
		liquidCapacity = 200f;
		hasPower = true;
		size = 3;
	}};
	
	@Override
	public void run() {
		//钻头
		{
			钻头.requirements = ItemStack.with(items.纳米碳管, 200, items.超导体, 50, items.铬纳尔, 50, items.晶金, 50);
			preset.inject(钻头, 2);
		}
		//泵
		{
			泵.requirements = ItemStack.with(items.纳米碳管, 150, items.超导体, 50, items.铬纳尔, 100, items.晶金, 50);
			preset.inject(泵, 2);
		}
	}
}
