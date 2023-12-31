package st.addon.entity;

import layer.annotations.Module;
import layer.module.LayerModule;
import st.addon.entity.provider.EntityStoreProvider;
import st.addon.item.ITEM;
import st.addon.seffect.SEffect;
import st.addon.tooltip.Tooltip;
import st.addon.ui.UI;
import st.plugin.place.Place;
import st.plugin.quantum.Quantum;

@Module(name = "entity")
public class Entity extends LayerModule {
	public Entity() {
		imports(
			SEffect.class,
			ITEM.class,
			UI.class,
			Quantum.class,
			Tooltip.class,
			Place.class,
			UI.class
		);
		provider(
			new EntityStoreProvider()
		);
	}
}
