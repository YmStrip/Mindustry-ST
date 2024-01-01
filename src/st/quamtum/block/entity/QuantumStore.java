package st.quamtum.block.entity;

import st.addon.content.provider.ItemProvider;
import st.addon.content.provider.TooltipProvider;
import st.provider.place.provider.PlaceProvider;
import st.provider.ui.provider.views.QuantumNetworkDashUI;
import st.quamtum.provider.provider.QuantumMapTile;
import st.quamtum.provider.provider.QuantumWorld;

public class QuantumStore {
	public ItemProvider items;
	public TooltipProvider tooltip;
	public PlaceProvider placeMap;
	public QuantumMapTile quantumMap;
	public QuantumWorld quantumWorld;
	public QuantumNetworkDashUI quantumNetworkDashUI;
}
