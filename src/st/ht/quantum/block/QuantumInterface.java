package st.ht.quantum.block;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.gen.Building;
import mindustry.type.Category;
import mindustry.type.Item;
import mindustry.type.Liquid;
import mindustry.ui.Bar;
import st.dao.LibgdxDao;
import st.ht.quantum.entity.QuantumRequest;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class QuantumInterface extends QuantumBlock {
	
	public TextureRegion topRegion;
	public TextureRegion mainRegion;
	
	public QuantumInterface(String name) {
		super(name);
		hasPower = true;
		size = 8;
		update = true;
		canOverdrive = true;
		hasItems = true;
		hasLiquids = true;
		liquidCapacity = 50;
		itemCapacity = 50;
		configurable = true;
		saveConfig = true;
		saveData = false;
		noUpdateDisabled = true;
		maxPlace = 1;
		placeClass = "io-quantum-interface";
		quantum = true;
		category = Category.effect;
	}
	
	@Override
	public void load() {
		super.load();
		mainRegion = Core.atlas.find(this.name);
		topRegion = Core.atlas.find(this.name + "-top");
	}
	
	@Override
	public void setBars() {
		super.setBars();
		addBar("progress", (QuantumInterfaceBuild d) -> new Bar(
			() -> {
				return Core.bundle.format("bar.progress");
			},
			() -> {
				return Color.rgb(0, 255, 255);
			},
			() -> {
				return d.progress;
			}
		));
	}
	
	QuantumBlock b = this;
	
	public class QuantumInterfaceBuild extends QuantumBuilding {
		public QuantumRequest selectConfig = new QuantumRequest();
		
		public QuantumInterfaceBuild() {
			super();
			checkProcess();
		}
		
		@Override
		public void read(Reads read, byte res) {
			selectConfig = LibgdxDao.readJson(read, selectConfig);
			checkProcess();
		}
		
		@Override
		public void write(Writes write) {
			LibgdxDao.writeJson(write, selectConfig);
		}
		
		//2.txt~60一次,可加速
		public float progress = 0;
		public boolean isProduct = false;
		public int iter = 0;
		
		@Override
		public void updateTile() {
			super.updateTile();
			//加法减少开销
			++iter;
			if (iter > 40) {
				iter = 0;
				if (!isProduct) checkProcess();
			}
			if (!isProduct) return;
			//没有电
			if (power.status <= 0) {
				return;
			}
			progress += 0.05f * power.status / 30;
			consume();
			if (progress >= 1) {
				progress = 0;
				use();
				endProduct();
				checkProcess();
			}
		}
		
		//30秒一次
		public void use() {
			var ts = store.quantumMap.team(team.id);
			var time = 30;
			var core = team.core();
			if (core == null) return;
			var require = new QuantumRequest();
			//prefix
			selectConfig.items.data.forEach((name, data) -> {
				var i = Vars.content.item(name);
				if (i == null) return;
				require.items.set(name, (float) Math.min(core.storageCapacity - core.items.get(i), Math.min(b.importItemSpeed * time, data)));
			});
			selectConfig.liquids.data.forEach((name, data) -> {
				require.liquids.set(name, Math.min(ts.liquidCapability - ts.liquids.get(name), Math.min(b.importLiquidSpeed * time, data)));
			});
			var taken = store.quantumMap.global_take(this.team.id + "", require, store.quantumMap.sector);
			//v0(false);
			//add-data
			AtomicBoolean has = new AtomicBoolean(false);
			taken.items.data.forEach((name, data) -> {
				var item = Vars.content.item(name);
				if (item == null) return;
				core.items.add(item, Math.round(data));
				selectConfig.items.add(name, -data);
				if (data > 0) has.set(true);
			});
			taken.liquids.data.forEach((name, data) -> {
				ts.liquids.add(name, data);
				ts.liquids.change(name, data);
				selectConfig.liquids.add(name, -data);
				if (data > 0) has.set(true);
			});
			if (!has.get()) {
				endProduct();
				selectConfig = new QuantumRequest();
			}
		}
		
		public void endProduct() {
			isProduct = false;
			consumesPower = false;
		}
		
		public void startProduct() {
			isProduct = true;
			consumesPower = true;
		}
		
		//发射进度条
		public boolean isClose() {
			if (!store.quantumMap.isSector) return false;
			var core = team.core();
			var ts = store.quantumMap.team(team.id);
			if (core == null) return false;
			var isItem = false;
			var isLiquid = false;
			for (Map.Entry<String, Float> data : selectConfig.items.data.entrySet()) {
				var v = data.getValue();
				var n = data.getKey();
				var it = Vars.content.item(n);
				if (it == null) continue;
				if (v > 0) isItem = true;
				if (core.items.get(it) + v > core.storageCapacity) {
					data.setValue((float) (core.storageCapacity - core.items.get(it)));
				}
			}
			for (Map.Entry<String, Float> data : selectConfig.liquids.data.entrySet()) {
				var v = data.getValue();
				var n = data.getKey();
				if (v > 0) isLiquid = true;
				if (ts.liquids.get(n) + v > ts.liquidCapability) {
					data.setValue(ts.liquidCapability - ts.liquids.get(n));
				}
			}
			return isItem || isLiquid;
		}
		
		public void checkProcess() {
			if (!store.quantumMap.isSector) {
				endProduct();
				return;
			}
			;
			var is = isClose();
			if (!is) {
				isProduct = false;
				progress = 0;
			} else if (!isProduct) {
				startProduct();
			}
		}
		
		@Override
		public void draw() {
			float rotation = 0.0F;
			Draw.rect(mainRegion, this.x, this.y, rotation);
			if (isProduct) {
				Draw.rect(topRegion, this.x, this.y, rotation);
			}
		}
		
		@Override
		public boolean acceptLiquid(Building source, Liquid liquid) {
			return store.quantumMap.globalTeam(team.id).liquids.acceptCapacity(liquid.name) > 0;
		}
		
		@Override
		public void handleItem(Building source, Item item) {
			store.quantumMap.globalTeam(team.id).items.add(item.name, 1f);
		}
		
		@Override
		public void handleLiquid(Building source, Liquid liquid, float amount) {
			store.quantumMap.globalTeam(team.id).liquids.add(liquid.name, amount);
		}
		
		@Override
		public boolean acceptItem(Building source, Item item) {
			return store.quantumMap.globalTeam(team.id).items.acceptCapacity(item.name) > 0;
		}
	}
}
