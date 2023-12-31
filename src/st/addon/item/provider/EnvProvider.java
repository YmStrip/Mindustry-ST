package st.addon.item.provider;

import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.world.blocks.environment.OreBlock;

@Provider
public class EnvProvider extends LayerProvider {
	@Require(cls = ItemProvider.class)
	ItemProvider items;
	OreBlock 金元素结晶;
	OreBlock 木元素结晶;
	OreBlock 水元素结晶;
	OreBlock 火元素结晶;
	OreBlock 土元素结晶;
	OreBlock 光元素结晶;
	OreBlock 暗元素结晶;
	
	@Override
	public void run() {
		
		金元素结晶 = new OreBlock("金元素结晶",items.金元素){{
			allowCorePlacement = true;
			oreDefault = false;
			oreThreshold = 0.882f;
			oreScale = 3.380953f;
			alwaysUnlocked = true;
		}};
		木元素结晶 = new OreBlock("木元素结晶",items.木元素){{
			allowCorePlacement = true;
			oreDefault = false;
			oreThreshold = 0.882f;
			oreScale = 2.380953f;
			alwaysUnlocked = true;
		}};
		水元素结晶 = new OreBlock("水元素结晶",items.水元素){{
			allowCorePlacement = true;
			oreDefault = false;
			oreThreshold = 0.882f;
			oreScale = 1.80953f;
			alwaysUnlocked = true;
		}};
		火元素结晶 = new OreBlock("火元素结晶",items.火元素){{
			allowCorePlacement = true;
			oreDefault = false;
			oreThreshold = 0.882f;
			oreScale = 1.180953f;
			alwaysUnlocked = true;
		}};
		土元素结晶 = new OreBlock("土元素结晶",items.土元素){{
			allowCorePlacement = true;
			oreDefault = false;
			oreThreshold = 0.882f;
			oreScale = 4.380953f;
			alwaysUnlocked = true;
		}};
		光元素结晶 = new OreBlock("光元素结晶",items.光元素){{
			allowCorePlacement = true;
			oreDefault = false;
			oreThreshold = 0.882f;
			oreScale = 4.380953f;
			alwaysUnlocked = true;
		}};
		暗元素结晶 = new OreBlock("暗元素结晶",items.暗元素){{
			allowCorePlacement = true;
			oreDefault = false;
			oreThreshold = 0.882f;
			oreScale = 4.380953f;
			alwaysUnlocked = true;
		}};
	}
}
