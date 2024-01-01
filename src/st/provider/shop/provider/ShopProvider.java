package st.provider.shop.provider;

import layer.annotations.Provider;
import layer.extend.LayerProvider;
import mindustry.Vars;
import mindustry.type.Item;
import mindustry.type.Liquid;

import java.util.HashMap;

@Provider
public class ShopProvider extends LayerProvider {
	public HashMap<String, Float> items = new HashMap<>();
	public HashMap<String, Float> liquids = new HashMap<>();
	float getItemPrice(Item item) {
		
		return Math.max(item.explosiveness, 0) + Math.max(item.flammability, 0) + Math.max(item.charge, 0) + Math.max(item.cost, 1);
	}
	
	float getLiquidPrice(Liquid liquid) {
		float temprC = Math.abs(liquid.temperature - 0.5f) * 2f;
		float viscC = Math.abs(liquid.viscosity - 0.5f) * 2f;
		float explosivenessC = Math.max(liquid.explosiveness * 1.2f, 0);
		float flammabilityC = Math.max(liquid.flammability * 1.2f, 0f) * 0.3f;
		float heatCapacityC = Math.max(liquid.heatCapacity * 1.2f, 0f) * 2.5f;
		return (0.5f + temprC + viscC + explosivenessC + flammabilityC + heatCapacityC) / 6;
	}
	
	void init_items() {
		items.clear();
		for (var i : Vars.content.items()) {
			items.put(i.name, getItemPrice(i));
		}
	}
	
	void init_liquids() {
		liquids.clear();
		for (var liquid : Vars.content.liquids()) {
			liquids.put(liquid.name, getLiquidPrice(liquid));
		}
	}
	
	@Override
	public void run() {
		init_items();
		init_liquids();
	}
}
