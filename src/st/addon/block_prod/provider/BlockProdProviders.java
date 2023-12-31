package st.addon.block_prod.provider;

import layer.annotations.Controller;
import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerController;
import layer.extend.LayerProvider;
import st.addon.entity.Entity;
import st.addon.entity.entity.StBlock;
import st.addon.entity.provider.EntityStoreProvider;

@Provider
public class BlockProdProviders extends LayerProvider {
	@Require(cls = EntityStoreProvider.class)
	@Import(cls = Entity.class)
	EntityStoreProvider store;
	public StBlock AtomicSynthesizer;
	
	@Override
	public void run() {
		/*AtomicSynthesizer = new AtomicSynthesizer(store.store, "atomic-synthesizer") {{
			requirements(Category.distribution, ItemStack.with(Items.copper, 100));
		}};*/
	}
}
