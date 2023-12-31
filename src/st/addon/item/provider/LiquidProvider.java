package st.addon.item.provider;

import arc.graphics.Color;
import layer.annotations.Provider;
import layer.extend.LayerProvider;
import mindustry.content.Liquids;
import mindustry.type.Liquid;

@Provider
public class LiquidProvider extends LayerProvider {
	public Liquid 纳米元素流体 = new Liquid("纳米元素流体") {{
		heatCapacity = 1.64f;
		color = Color.rgb(255, 255, 255);
	}};
}
