package st.addon.stransport.provider;

import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.content.Items;
import mindustry.type.Category;
import mindustry.type.ItemStack;
import mindustry.world.blocks.distribution.Conveyor;
import mindustry.world.blocks.liquid.Conduit;
import mindustry.world.blocks.storage.Unloader;
import mindustry.world.meta.BlockGroup;
import st.addon.content.SContent;
import st.addon.content.provider.ItemProvider;

@Provider
public class T1Transports extends LayerProvider {
	@Import(cls = SContent.class)
	@Require(cls = ItemProvider.class)
	ItemProvider items;
	@Require(cls = TransportPreset.class)
	TransportPreset preset;
	//卸载器
	public Unloader 超导卸载器 = new Unloader("t1卸载器") {{
		speed = 60f / 24f;
		group = BlockGroup.transportation;
		category = Category.effect;
	}};
	//传送带
/*	public Conveyor 超导管道 = new Conveyor("t1传送带") {{
		size = 1;
		speed = 15 / 140f;
		displayedSpeed = 15;
		category = Category.distribution;
	}};
	//管道
	public Conduit 超导流体管道 = new Conduit("t1流体管道") {{
		size = 1;
		liquidCapacity = 24f;
		liquidPressure = 1.025f;
		category = Category.liquid;
	}};*/
	
	public void run() {
		//卸载器
		{
			超导卸载器.requirements = ItemStack.with(items.超导体, 5, Items.silicon, 25, Items.titanium, 30);
			preset.inject(超导卸载器, 1);
		}
		//管道
		/*{
			超导管道.requirements = ItemStack.with(items.超导体, 1, Items.titanium, 1);
			preset.inject(超导管道, 1);
		}
		//流体管道
		{
			超导流体管道.requirements = ItemStack.with(items.超导体, 1, Items.titanium, 1, Items.metaglass, 1);
			preset.inject(超导流体管道, 1);
		}*/
	}
}
