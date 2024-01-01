package st.addon.prod.entity;

import arc.struct.ObjectMap;
import mindustry.content.Items;
import mindustry.type.Item;
import mindustry.type.ItemStack;
import mindustry.type.LiquidStack;
import mindustry.world.blocks.production.GenericCrafter;
import st.addon.content.provider.ItemProvider;
import st.addon.prod.provider.ProdPreset;

import java.util.ArrayList;

public class UpgradeProds {
	public ArrayList<UpgradeProd> reg = new ArrayList<>();
	
	public enum type {
		t1,
		t2
	}
	
	public void registry(ItemProvider items, ProdPreset preset) {
		for (var i : reg) {
			i.registry(items, preset);
		}
	}
	
	public class UpgradeProd {
		public String name;
		public ItemStack output;
		public ItemStack[] input;
		
		public UpgradeProd(String name) {
			this.name = name;
			reg.add(this);
		}
		
		public type type;
		public int size = 2;
		
		public UpgradeProd size(int size) {
			this.size = size;
			return this;
		}
		
		public UpgradeProd input(ItemStack[] stacks) {
			input = stacks;
			return this;
		}
		
		public LiquidStack inputLiquid;
		
		public UpgradeProd inputLiquid(LiquidStack liquidStack) {
			inputLiquid = liquidStack;
			return this;
		}
		
		public LiquidStack outputLiquid;
		
		public UpgradeProd outputLiquid(LiquidStack liquidStack) {
			outputLiquid = liquidStack;
			return this;
		}
		
		public float power = 1;
		
		public UpgradeProd power(float power) {
			this.power = power;
			return this;
		}
		
		;
		
		public UpgradeProd output(ItemStack stacks) {
			output = stacks;
			return this;
		}
		
		public float multiplier = 1;
		
		public UpgradeProd multiplier(float count) {
			this.multiplier = count;
			return this;
		}
		
		public int level;
		
		public void registry(ItemProvider items, ProdPreset preset) {
			preset.inject(block);
			//basicCount
			var basic = new ObjectMap<Item, Integer>();
			basic.put(Items.copper, 250);
			basic.put(Items.lead, 200);
			basic.put(Items.thorium, 100);
			basic.put(Items.titanium, 150);
			basic.put(Items.silicon, 180);
			if (type == type.t1) {
				level = 1;
				basic.put(items.纳米碳管, 250);
				basic.put(items.超导体, 50);
			}
			var d = new ItemStack[basic.size];
			var k = -1;
			//map
			for (var i : basic) {
				++k;
				d[k] = new ItemStack(i.key, (int) (i.value * multiplier));
			}
			block.requirements = d;
			block.consumePower(power * multiplier);
		}
		
		public UpgradeProd t(type type) {
			this.type = type;
			return this;
		}
		
		GenericCrafter block;
		
		public GenericCrafter block() {
			block = new GenericCrafter(name);
			block.size = this.size;
			block.craftTime = 0.75f;
			if (input != null) block.consumeItems(input);
			if (output != null) block.outputItem = output;
			if (inputLiquid != null) block.consumeLiquid(inputLiquid.liquid, inputLiquid.amount);
			if (outputLiquid != null) block.outputLiquid = outputLiquid;
			return block;
		}
	}
}
