package st;

import arc.util.*;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.Items;
import mindustry.content.Liquids;
import mindustry.content.UnitTypes;
import mindustry.entities.bullet.BombBulletType;
import mindustry.entities.effect.MultiEffect;
import mindustry.entities.effect.WaveEffect;
import mindustry.entities.effect.WrapEffect;
import mindustry.mod.*;
import mindustry.type.Planet;
import mindustry.type.Sector;
import mindustry.world.blocks.Attributes;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.logic.MessageBlock;
import mindustry.world.blocks.production.Drill;
import mindustry.world.meta.Attribute;


//640 * 360
//960 * 540
//1280 * 720
public class index extends Mod {
	@Override
	public void loadContent() {
		Log.info("wait to load ST MODULE-GROUP ...");
		ST.init();
		//new WaveE
		//new mindustry.entities.effect.ExplosionEffect()
		//new BombBulletType()
	}
	@Override
	public void init() {
	
	}
}
