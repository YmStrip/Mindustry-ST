package st.addon.block_io.provider;

import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.Block;
import mindustry.world.blocks.logic.MessageBlock;
import mindustry.world.meta.BuildVisibility;
import st.addon.block_io.provider.effects.BigMessage;
import st.addon.entity.Entity;
import st.addon.entity.entity.StBlock;
import st.addon.entity.entity.StBuilding;
import st.addon.entity.provider.EntityStoreProvider;

import static mindustry.type.ItemStack.with;

@Provider
public class BlockLogicProvider extends LayerProvider {
	@Require(cls = EntityStoreProvider.class)
	@Import(cls = Entity.class)
	EntityStoreProvider store;
	public Block bigMessage;
	
	@Override
	public void run() {
		bigMessage = new MessageBlock("大号消息板") {{
			health = 9999999;
			requirements(Category.distribution, BuildVisibility.editorOnly, with());
			maxTextLength = 999999;
			maxNewlines = 9999;
		}};
	}
}