package st.addon.drill.provider;

import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.type.ItemStack;
import mindustry.world.blocks.production.Drill;
import mindustry.world.blocks.production.Pump;
import st.addon.content.SContent;
import st.addon.content.provider.AttrProvider;
import st.addon.content.provider.ItemProvider;

@Provider
public class T3Drills extends LayerProvider {
	@Require(cls = DrillPreset.class)
	DrillPreset preset;
	@Import(cls = SContent.class)
	@Require(cls = ItemProvider.class)
	ItemProvider items;
	@Import(cls = SContent.class)
	@Require(cls = AttrProvider.class)
	AttrProvider attrs;
	//钻头
	public Drill 钻头 = new Drill("t3钻头") {{
		consumePower(30f);
		size = 4;
		tier = 6;
		drillTime = 240 / (16 * 1f);
	}};
	//泵
	public Pump 泵 = new Pump("t3泵") {{
		consumePower(15f);
		pumpAmount = 1f;
		liquidCapacity = 200f;
		hasPower = true;
		size = 3;
	}};
	@Override
	public void run() {
		//钻头
		{
			钻头.requirements = ItemStack.with(items.金元素,35,items.木元素,50,items.水元素,25,items.土元素,50);
			preset.inject(钻头, 3);
		}
		//泵
		{
			泵.requirements = ItemStack.with(items.金元素,35,items.木元素,45,items.水元素,20,items.土元素,60);
			preset.inject(泵, 3);
		}
	}
}
