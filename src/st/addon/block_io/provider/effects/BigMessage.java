package st.addon.block_io.provider.effects;

import arc.scene.ui.layout.Table;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.content.Items;
import mindustry.gen.Building;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.blocks.logic.MessageBlock;
import st.addon.entity.entity.EntityStore;
import st.addon.entity.entity.StBlock;
import st.addon.entity.entity.StBuilding;
import st.dao.LibgdxDao;

public class BigMessage extends StBlock {
	
	public BigMessage(EntityStore store, String name) {
		super(store, name);
		update = true;
		configurable = true;
		health = 9999999;
		requirements(
			Category.logic,
			ItemStack.with(
				Items.silicon, 999999
			)
		);
		config(String.class, (build, data) -> {
			if (!(build instanceof BigMessageBuild big)) return;
			big.message = data;
		});
	}
	
	public StBlock b = this;
	
	public class BigMessageBuild extends StBuilding {
		public String message;
		
		public BigMessageBuild() {
			super(b);
		}
		
		@Override
		public void configure(Object value) {
			message = "" + value;
		}
		
		@Override
		public void buildConfiguration(Table table) {
			//打开UI
			store.messageUI.new component(this, this.message);
		}
		
		@Override
		public void read(Reads read, byte revision) {
			message = LibgdxDao.readStr(read);
		}
		
		@Override
		public void write(Writes write) {
			LibgdxDao.writeStr(write, message);
		}
	}
}
