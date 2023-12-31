package st.addon.block_prod;

import layer.annotations.Module;
import layer.module.LayerModule;
import st.addon.block_prod.provider.BlockProdProviders;
import st.addon.entity.Entity;

@Module(name = "prod")
public class BlockProd extends LayerModule {
	public BlockProd() {
		imports(
			Entity.class
		);
		provider(
			new BlockProdProviders()
		);
	}
}
