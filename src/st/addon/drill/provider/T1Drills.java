package st.addon.drill.provider;

import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.type.ItemStack;
import mindustry.world.blocks.production.Drill;
import mindustry.world.blocks.production.Pump;
import mindustry.world.blocks.production.SolidPump;
import st.addon.content.SContent;
import st.addon.content.provider.AttrProvider;
import st.addon.content.provider.ItemProvider;
import st.addon.drill.entity.SExcavator;

@Provider
public class T1Drills extends LayerProvider {
	@Require(cls = DrillPreset.class)
	DrillPreset preset;
	@Import(cls = SContent.class)
	@Require(cls = ItemProvider.class)
	ItemProvider items;
	@Import(cls = SContent.class)
	@Require(cls = AttrProvider.class)
	AttrProvider attrs;
	//浅层挖矿机
	public SExcavator 浅层挖矿机 = new SExcavator("浅层挖矿机") {{
		consumeLiquid(Liquids.water, 0.15f);
		consumePower(6f);
		size = 2;
		craftTime = 8;
		liquidCapacity = 250;
		itemCapacity = 250;
		results = ItemStack.with(Items.copper, 1, Items.lead, 1);
	}};
	//深层挖矿机
	public SExcavator 深层挖矿机 = new SExcavator("深层挖矿机") {{
		consumeLiquid(Liquids.water, 0.15f);
		consumePower(9f);
		size = 2;
		craftTime = 16;
		liquidCapacity = 250;
		itemCapacity = 250;
		results = ItemStack.with(Items.titanium, 1, Items.thorium, 1);
	}};
	//特种挖矿机
	public SExcavator 特种挖矿机 = new SExcavator("特种挖矿机") {{
		consumeLiquid(Liquids.water, 0.15f);
		consumePower(6f);
		size = 2;
		craftTime = 8;
		liquidCapacity = 250;
		itemCapacity = 250;
		results = ItemStack.with(Items.sand, 1, Items.coal, 1);
	}};
	//富集挖矿机
	public SExcavator 富集机 = new SExcavator("富集机") {{
		consumeLiquid(Liquids.water, 0.5f);
		consumePower(24f);
		size = 2;
		craftTime = 16;
		liquidCapacity = 250;
		itemCapacity = 250;
		results = ItemStack.with(Items.metaglass, 3, Items.graphite, 3, Items.silicon, 4, Items.plastanium, 1, Items.phaseFabric, 1, Items.surgeAlloy, 1);
	}};
	//time = 240 / (size * speed)
	//超导钻头
	public Drill 钻头 = new Drill("t1钻头") {{
		consumePower(3f);
		size = 2;
		tier = 4;
		drillTime = 240 / (4 * 0.25f);
	}};
	//泵
	public Pump 泵 = new Pump("t1泵") {{
		consumePower(3.5f);
		pumpAmount = 0.25f;
		liquidCapacity = 200f;
		hasPower = true;
		size = 2;
	}};
	public SolidPump 抽水机 = new SolidPump("t1抽水机") {{
		size = 2;
		consumePower(8f);
		pumpAmount = 1.25f;
		liquidCapacity = 150f;
		rotateSpeed = 1.4f;
		result = Liquids.water;
	}};
	public SolidPump 抽矿机 = new SolidPump("t1抽矿机") {{
		size = 2;
		consumePower(12f);
		pumpAmount = 1f;
		liquidCapacity = 150f;
		rotateSpeed = 1.4f;
		result = Liquids.slag;
	}};
	public SolidPump 抽冷冻液机 = new SolidPump("t1抽冷冻液机") {{
		size = 2;
		consumePower(12f);
		pumpAmount = 1f;
		liquidCapacity = 150f;
		rotateSpeed = 1.4f;
		result = Liquids.cryofluid;
	}};
	
	@Override
	public void run() {
		//Blocks.impulsePump
		//Blocks.mechanicalDrill
		//Blocks.separator
		//Blocks.cultivator
		//浅层
		{
			浅层挖矿机.requirements = ItemStack.with(items.纳米碳管, 100, items.超导体, 50, Items.silicon, 150, Items.surgeAlloy, 100);
			浅层挖矿机.attribute = attrs.浅层矿物;
			preset.inject(浅层挖矿机, 1);
		}
		//深层
		{
			深层挖矿机.requirements = ItemStack.with(items.纳米碳管, 250, items.超导体, 100, Items.silicon, 250, Items.surgeAlloy, 150);
			深层挖矿机.attribute = attrs.深层矿物;
			preset.inject(深层挖矿机, 1);
		}
		//特种
		{
			特种挖矿机.requirements = ItemStack.with(items.纳米碳管, 100, items.超导体, 50, Items.silicon, 150, Items.surgeAlloy, 100);
			特种挖矿机.attribute = attrs.浅层矿物;
			preset.inject(特种挖矿机, 1);
		}
		//富集
		{
			富集机.requirements = ItemStack.with(items.纳米碳管, 500, items.超导体, 250, Items.silicon, 350, Items.surgeAlloy, 250);
			富集机.attribute = attrs.富集地;
			preset.inject(富集机, 1);
		}
		//钻头
		{
			钻头.requirements = ItemStack.with(items.纳米碳管, 100, items.超导体, 25, Items.silicon, 50, Items.titanium, 50);
			preset.inject(钻头, 1);
		}
		//泵
		{
			泵.requirements = ItemStack.with(items.纳米碳管, 100, items.超导体, 25, Items.metaglass, 150, Items.titanium, 50);
			preset.inject(泵, 1);
		}
		//抽水机
		{
			抽水机.requirements = ItemStack.with(items.纳米碳管, 150, items.超导体, 50, Items.silicon, 100, Items.metaglass, 100, Items.titanium, 100, Items.plastanium, 50);
			preset.inject(抽水机, 1);
		}
		//抽矿机
		{
			抽矿机.requirements = ItemStack.with(items.纳米碳管, 175, Items.metaglass, 150, items.超导体, 25, Items.silicon, 200, Items.titanium, 100, Items.plastanium, 50);
			preset.inject(抽矿机, 1);
		}
		//抽冷冻液机
		{
			抽冷冻液机.requirements = ItemStack.with(items.纳米碳管, 150, Items.metaglass, 150, items.超导体, 50, Items.silicon, 175, Items.titanium, 150, Items.plastanium, 100);
			preset.inject(抽冷冻液机, 1);
		}
	}
}
