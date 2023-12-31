package st.addon.item.provider;

import layer.annotations.Provider;
import layer.extend.LayerProvider;
import mindustry.world.meta.Attribute;

@Provider
public class AttrProvider extends LayerProvider {
	public Attribute 以太 = Attribute.add("以太");
}
