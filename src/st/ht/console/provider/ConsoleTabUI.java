package st.ht.console.provider;

import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import st.addon.content.SContent;
import st.addon.content.provider.UIProvider;

import java.util.ArrayList;

@Provider
public class ConsoleTabUI extends LayerProvider {
	@Import(cls = SContent.class)
	@Require(cls = UIProvider.class)
	UIProvider uis;
	public ArrayList<UIProvider.tab.tabInfo> tabs = new ArrayList<>();
	public String last = "";
	
}
