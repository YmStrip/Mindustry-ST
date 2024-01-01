package st.provider.ui;

import layer.annotations.Module;
import layer.module.LayerModule;
import st.provider.ui.provider.components.Tab;
import st.provider.ui.provider.components.Title;
import st.provider.ui.provider.views.AtomicSynthesizerUI;
import st.provider.ui.provider.views.MessageUI;
import st.provider.ui.provider.views.QuantumNetworkDashUI;
import st.quamtum.provider.Quantum;
import st.provider.shop.Shop;

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
