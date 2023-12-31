package st.addon.entity.entity;

import st.addon.item.provider.ItemProvider;
import st.addon.attack.provider.EffectProvider;
import st.addon.tooltip.provider.ToolTipBar;
import st.addon.ui.provider.views.AtomicSynthesizerUI;
import st.addon.ui.provider.views.MessageUI;
import st.addon.ui.provider.views.QuantumNetworkDashUI;
import st.plugin.place.provider.PlaceMap;
import st.plugin.quantum.provider.QuantumMapTile;
import st.plugin.quantum.provider.QuantumWorld;

public class EntityStore {
	//store
	public QuantumWorld quantumWorld;
	public QuantumMapTile quantumMap;
	public PlaceMap placeMap;
	//ui
	public QuantumNetworkDashUI quantumNetworkDashUI;
	public AtomicSynthesizerUI atomicSynthesizerUI;
	public MessageUI messageUI;
	public ToolTipBar toolTipBar;
	public ItemProvider items;
	public EffectProvider effects;
}
