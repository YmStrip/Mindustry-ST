package st.plugin.shop;

import layer.annotations.Module;
import layer.module.LayerModule;
import st.plugin.shop.provider.ShopProvider;

@Module(name = "shop")
public class Shop extends LayerModule {
	public Shop() {
		provider(
			new ShopProvider()
		);
	}
}
