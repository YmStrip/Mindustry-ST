package st.addon.ui;

import layer.annotations.Module;
import layer.module.LayerModule;
import st.addon.ui.provider.components.Tab;
import st.addon.ui.provider.components.Title;
import st.addon.ui.provider.views.AtomicSynthesizerUI;
import st.addon.ui.provider.views.MessageUI;
import st.addon.ui.provider.views.QuantumNetworkDashUI;
import st.plugin.quantum.Quantum;
import st.plugin.shop.Shop;

@Module(name = "ui")
public class UI extends LayerModule {
	public UI() {
		imports(
			Quantum.class,
			Shop.class
		);
		provider(
			new Title(),
			new Tab(),
			new MessageUI(),
			new AtomicSynthesizerUI(),
			new QuantumNetworkDashUI()
		);
	}
}
