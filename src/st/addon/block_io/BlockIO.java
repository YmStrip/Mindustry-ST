package st.addon.block_io;

import layer.annotations.Module;
import layer.module.LayerModule;
import mindustry.content.Blocks;
import st.addon.block_io.controller.BlockIoTechController;
import st.addon.block_io.provider.BlockIOProviders;
import st.addon.block_io.provider.BlockLogicProvider;
import st.addon.entity.Entity;
import st.addon.item.ITEM;

@Module(name = "block-io")
public class BlockIO extends LayerModule {
	public BlockIO() {
		imports(
			Entity.class,
			ITEM.class
		);
		controller(
			new BlockIoTechController()
		);
		provider(
			new BlockIOProviders(),
			new BlockLogicProvider()
		);
	}
}
