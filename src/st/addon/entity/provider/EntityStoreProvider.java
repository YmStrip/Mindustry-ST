package st.addon.entity.provider;

import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import st.addon.entity.entity.EntityStore;
import st.addon.item.ITEM;
import st.addon.item.provider.ItemProvider;
import st.addon.seffect.SEffect;
import st.addon.attack.provider.EffectProvider;
import st.addon.tooltip.Tooltip;
import st.addon.tooltip.provider.ToolTipBar;
import st.addon.ui.UI;
import st.addon.ui.provider.views.AtomicSynthesizerUI;
import st.addon.ui.provider.views.MessageUI;
import st.addon.ui.provider.views.QuantumNetworkDashUI;
import st.plugin.place.Place;
import st.plugin.place.provider.PlaceMap;
import st.plugin.quantum.Quantum;
import st.plugin.quantum.provider.QuantumMapTile;
import st.plugin.quantum.provider.QuantumWorld;

@Provider
public class EntityStoreProvider extends LayerProvider {
	public EntityStore store = new EntityStore();
	@Import(cls = Quantum.class)
	@Require(cls = QuantumMapTile.class)
	QuantumMapTile quantumMap;
	@Import(cls = Quantum.class)
	@Require(cls = QuantumWorld.class)
	QuantumWorld quantumWorld;
	@Import(cls = Tooltip.class)
	@Require(cls = ToolTipBar.class)
	ToolTipBar toolTipBar;
	@Import(cls = Place.class)
	@Require(cls = PlaceMap.class)
	PlaceMap placeMap;
	@Import(cls = UI.class)
	@Require(cls = AtomicSynthesizerUI.class)
	AtomicSynthesizerUI atomicSynthesizerUI;
	@Import(cls = UI.class)
	@Require(cls = QuantumNetworkDashUI.class)
	QuantumNetworkDashUI quantumNetworkDashUI;
	@Import(cls = UI.class)
	@Require(cls = MessageUI.class)
	MessageUI messageUI;
	@Import(cls = ITEM.class)
	@Require(cls = ItemProvider.class)
	ItemProvider items;
	@Import(cls = SEffect.class)
	@Require(cls = EffectProvider.class)
	EffectProvider effects;
	@Override
	public void run() {
		store.quantumMap = quantumMap;
		store.quantumWorld = quantumWorld;
		store.toolTipBar = toolTipBar;
		store.placeMap = placeMap;
		store.atomicSynthesizerUI = atomicSynthesizerUI;
		store.quantumNetworkDashUI = quantumNetworkDashUI;
		store.messageUI = messageUI;
		store.items = items;
		store.effects = effects;
	}
}
