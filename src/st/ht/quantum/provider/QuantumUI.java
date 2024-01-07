package st.ht.quantum.provider;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import arc.scene.ui.Label;
import arc.scene.ui.layout.Table;
import arc.util.Strings;
import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.Vars;
import mindustry.core.UI;
import mindustry.gen.Icon;
import mindustry.gen.Tex;
import mindustry.ui.Styles;
import st.addon.content.SContent;
import st.addon.content.provider.UIProvider;
import st.ht.quantum.block.QuantumBlock;
import st.ht.quantum.block.QuantumInterface;
import st.ht.quantum.entity.QuantumNetwork;

import java.util.ArrayList;
import java.util.HashMap;

import static mindustry.Vars.mobile;
import static mindustry.Vars.ui;

@Provider
public class QuantumUI extends LayerProvider {
	@Import(cls = SContent.class)
	@Require(cls = UIProvider.class)
	UIProvider uis;
	
	@Require(cls = QuantumMap.class)
	QuantumMap quantumMap;
	
	public class component {
		public QuantumBlock.QuantumBuilding building;
		public QuantumBlock block;
		
		public component(QuantumBlock.QuantumBuilding b) {
			var view = uis.new tab("quantum-network-dash", Styles.fullDialog) {
				//检查进度条
				@Override
				public void hide() {
					super.hide();
					if (building instanceof QuantumInterface.QuantumInterfaceBuild ib) {
						ib.checkProcess();
					}
				}
			};
			view.setWidth(Core.scene.getWidth());
			building = b;
			block = b.block;
			view.index = "@st.quantum-network-tab-info";
			var isInterface = block instanceof QuantumInterface;
			view.tab(new Object[]{"@st.quantum-network-tab-info", Icon.info});
			view.tab(new Object[]{"@st.quantum-network-tab-network", Icon.menu});
			//本地量子储存
			view.slot("@st.quantum-network-tab-info", t -> {
				var ts = quantumMap.team(building.team.id);
				t.labelWrap("@st.quantum-network-info-description").left().expandX();
				t.row();
				renderCapacity(t, ts);
				renderItemList(t, ts);
				renderLiquidList(t, ts);
			});
			//全局网络
			view.slot("@st.quantum-network-tab-network", t -> {
				t.labelWrap("@st.quantum-network-control-description").left();
				t.row();
				if (!quantumMap.isSector) {
					t.add(uis.new title("@st.quantum-network-control-not-sector")).expandX().left();
					t.row();
					return;
				}
				var ts = quantumMap.globalTeam(building.team.id);
				renderCapacity(t, ts);
				if (!isInterface) {
					renderItemList(t, ts);
					renderLiquidList(t, ts);
				}
				//
				else {
					//System.out.println(ts);
					var IntBuild = (QuantumInterface.QuantumInterfaceBuild) building;
					var mapItem = IntBuild.selectConfig.items.data;
					var mapLiquid = IntBuild.selectConfig.liquids.data;
					renderItemList(t, ts, mapItem);
					renderLiquidList(t, ts, mapLiquid);
				}
				//添加导出物品
			});
			view.show();
		}
		
		public class renderListConfig {
			Table t;
			public String title;
			public ArrayList<valueInfo> list;
			//map
			public HashMap<String, Float> exportMap;
		}
		
		public class valueInfo {
			public String name;
			public String lozName;
			public TextureRegion icon;
			public long count;
			public long max = 25000000L;
		}
		
		public float bsize = 40f;
		
		//v0 name v1 lozName v2 icon v3 count
		public void renderList(renderListConfig config) {
			config.t.add(uis.new title(config.title)).expandX().left();
			config.t.row();
			/*if (config.exportMap != null) {
				var f = new TextField();
				config.t.add(f).left().expandX().fillX();
				config.t.row();
			}*/
			var t1 = new Table().left();
			var split = 0;
			if (config.exportMap == null) {
				for (var v : config.list) {
					++split;
					if (split > 4) {
						split = 1;
						t1.row();
					}
					var lab = new Table();
					lab.image((TextureRegion) v.icon).size(32);
					lab.labelWrap(UI.formatAmount(v.count)).padLeft(5f);
					t1.add(lab).minWidth(80f).pad(5f);
				}
			} else {
				for (var v : config.list) {
					t1.table(Tex.pane, ct -> {
						ct.margin(4).marginRight(8).left();
						ct.button("-", Styles.flatt, () -> {
							config.exportMap.put(
								v.name,
								Math.max(config.exportMap.getOrDefault(v.name, 0f) - 1000, 0));
							//updater.run();
						}).size(bsize);
						ct.button("+", Styles.flatt, () -> {
							config.exportMap.put(
								(String) v.name,
								(float) Math.min(config.exportMap.getOrDefault(v.name, 0f) + 1000, v.count));
						}).size(bsize);
						ct.button(Icon.pencil, Styles.flati, () -> ui.showTextInput("@configure", v.lozName, 10, config.exportMap.getOrDefault(v.name, 0f) + "", true, str -> {
							if (Strings.canParsePositiveInt(str)) {
								int amount = Strings.parseInt(str);
								if (amount >= 0 && amount <= v.count) {
									config.exportMap.put(v.name, (float) amount);
									return;
								}
							}
							ui.showInfo(Core.bundle.format("configure.invalid", v.count));
						})).size(bsize);
						ct.image(v.icon).size(8 * 3).padRight(4).padLeft(4);
						ct.label(() -> UI.formatAmount(Math.round(config.exportMap.getOrDefault(v.name, 0f))) + " " + "/" + " " + UI.formatAmount(v.count)).left().width(140f);
					}).pad(2).left().fillX();
					if (++split % 2 == 0 || (mobile && Core.graphics.isPortrait())) {
						t1.row();
					}
				}
			}
			config.t.add(t1).expandX().left();
			config.t.add(new Table()).width(25f);
			config.t.row();
		}
		
		public ArrayList<valueInfo> getItemsList(QuantumNetwork ts, boolean isCore) {
			var core = building.core();
			var data = new ArrayList<valueInfo>();
			for (var i : Vars.content.items()) {
				var hasItem = ts.items.get(i.name);
				if (isCore && core != null && core.items != null) hasItem += core.items.get(i);
				if (hasItem <= 0) continue;
				Float finalHasItem = hasItem;
				data.add(new valueInfo() {{
					lozName = i.localizedName;
					name = i.name;
					max = quantumMap.team(building.team.id).itemCapability;
					icon = i.uiIcon;
					count = Math.round(finalHasItem);
				}});
			}
			return data;
		}
		
		public void renderItemList(Table t, QuantumNetwork ts) {
			var data = getItemsList(ts, true);
			var config = new renderListConfig();
			config.t = t;
			config.list = data;
			config.title = "@item";
			renderList(config);
			t.row();
		}
		
		public void renderItemList(Table t, QuantumNetwork ts, HashMap<String, Float> map) {
			var data = getItemsList(ts, false);
			var config = new renderListConfig();
			config.t = t;
			config.list = data;
			config.title = "@item";
			config.exportMap = map;
			renderList(config);
			t.row();
		}
		
		public ArrayList<valueInfo> getLiquidsList(QuantumNetwork ts, boolean isCore) {
			var core = building.core();
			var data = new ArrayList<valueInfo>();
			for (var i : Vars.content.liquids()) {
				var hasLiquid = ts.liquids.get(i.name);
				if (isCore && core != null && core.liquids != null) hasLiquid += core.liquids.get(i);
				if (hasLiquid <= 0) continue;
				Float finalHasLiquid = hasLiquid;
				data.add(new valueInfo() {{
					lozName = i.localizedName;
					name = i.name;
					max = quantumMap.team(building.team.id).liquidCapability;
					icon = i.uiIcon;
					count = Math.round(finalHasLiquid);
				}});
			}
			return data;
		}
		
		public void renderLiquidList(Table t, QuantumNetwork ts) {
			var data = getLiquidsList(ts, true);
			var config = new renderListConfig();
			config.t = t;
			config.list = data;
			config.title = "@liquid";
			renderList(config);
		}
		
		public void renderLiquidList(Table t, QuantumNetwork ts, HashMap<String, Float> map) {
			var data = getLiquidsList(ts, false);
			var config = new renderListConfig();
			config.t = t;
			config.list = data;
			config.title = "@liquid";
			config.exportMap = map;
			renderList(config);
		}
		
		public void renderCapacity(Table t, QuantumNetwork ts) {
			var core = building.team.core();
			var coreItem = core == null ? 0 : core.storageCapacity;
			t.add(uis.new title("@capacity")).expandX().left();
			t.row();
			t.add(new Label(Core.bundle.get("item-capacity") + ": " + UI.formatAmount(ts.itemCapability + coreItem))).expandX().left().padLeft(5f);
			t.row();
			if (block instanceof QuantumInterface) {
				t.add(new Label(Core.bundle.get("import-item-speed") + ": " + UI.formatAmount(block.importItemSpeed * 60L) + "/min")).expandX().left().padLeft(5f);
				t.row();
			}
			t.add(new Label(Core.bundle.get("liquid-capacity") + ": " + UI.formatAmount(ts.liquidCapability))).expandX().left().padLeft(5f);
			t.row();
			if (block instanceof QuantumInterface) {
				t.add(new Label(Core.bundle.get("import-liquid-speed") + ": " + UI.formatAmount(block.importLiquidSpeed * 60L) + "/min")).expandX().left().padLeft(5f);
				t.row();
			}
		}
	}
}
