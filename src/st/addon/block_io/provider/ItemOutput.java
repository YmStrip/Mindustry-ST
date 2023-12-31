package st.addon.block_io.provider;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.layout.Table;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.type.Item;
import mindustry.world.blocks.ItemSelection;
import mindustry.world.meta.BlockGroup;
import st.addon.entity.entity.EntityStore;
import st.addon.entity.entity.StBlock;
import st.addon.entity.entity.StBuilding;
import st.dao.LibgdxDao;

public class ItemOutput extends StBlock {
	public ItemOutput(EntityStore store, String name) {
		super(store, name);
		this.group = BlockGroup.liquids;
		update = true;
		solid = true;
		health = 500;
		hasItems = true;
		configurable = true;
		saveConfig = false;
		itemCapacity = 150;
		noUpdateDisabled = true;
		unloadable = false;
		this.config(Item.class, (build, item) -> {
			((ItemOutputBuild) build).selectItem = item;
		});
	}
	
	StBlock b = this;
	TextureRegion mainRegion;
	TextureRegion topRegion;
	
	@Override
	public void load() {
		super.load();
		mainRegion = Core.atlas.find("st-物品输出接口");
		topRegion = Core.atlas.find("st-物品输出接口-top");
	}
	
	@Override
	protected TextureRegion[] icons() {
		return new TextureRegion[]{
			mainRegion,
			topRegion
		};
	}
	
	public class ItemOutputBuild extends StBuilding {
		public ItemOutputBuild() {
			super(b);
		}
		
		public Item selectItem = null;
		
		
		public void updateTile() {
			//var ts = GameStore.team(this.team.id);
			if (!isWaitItem()) return;
			Building core = team.core();
			if (selectItem == null || core == null)
				return;
			if (core.items.get(selectItem) > 0 && put(selectItem)) {
				//从核心取出
				core.itemTaken(selectItem);
				core.items.remove(selectItem, 1);
				waitItem();
			} else {
				//从网络取出
				var ts = store.quantumMap.team(team.id);
				if (ts.items.get(selectItem.name) <= 0) return;
				if (put(selectItem)) {
					ts.items.add(selectItem.name, -1f);
					ts.items.change(selectItem.name, -1f);
				}
			}
		}
		
		public void draw() {
			Draw.rect(mainRegion, this.x, this.y);
			Draw.color(this.selectItem == null ? Color.clear : this.selectItem.color);
			Draw.rect(topRegion, this.x, this.y);
		}
		
		public void buildConfiguration(Table table) {
			ItemSelection.buildTable(table, Vars.content.items(), () -> {
				return this.selectItem;
			}, this::configure);
		}
		
		public boolean onConfigureBuildTapped(Building other) {
			if (this == other) {
				this.deselect();
				this.configure((Object) null);
				return false;
			} else {
				return true;
			}
		}
		
		public boolean put(Item item) {
			int dump = this.cdump;
			for (int i = 0; i < this.proximity.size; ++i) {
				this.incrementDump(this.proximity.size);
				Building other = (Building) this.proximity.get((i + dump) % this.proximity.size);
				if (other instanceof QuantumDrive.QuantumDriveBuild) return false;
				if (other.team == this.team && !(other instanceof ItemInput.ItemInputBuild) && other.acceptItem(this, item) && this.canDump(other, item)) {
					other.handleItem(this, item);
					return true;
				}
			}
			
			return false;
		}
		
		public Item config() {
			return this.selectItem;
		}
		
		@Override
		public void write(Writes write) {
			super.write(write);
			LibgdxDao.writeStr(write, this.selectItem == null ? "" : this.selectItem.name);
		}
		
		@Override
		public void read(Reads read, byte revision) {
			super.read(read);
			String selectName = LibgdxDao.readStr(read);
			try {
				selectItem = Vars.content.item(selectName);
			} catch (Exception ignored) {
			
			}
		}
	}
}
