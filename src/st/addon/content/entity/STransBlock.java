package st.addon.content.entity;

import arc.Core;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.graphics.Pal;
import mindustry.ui.Bar;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.meta.Attribute;
import mindustry.world.meta.Stat;

//自定义方块-输入输出-原版的生产方块写的惨不忍睹
public class STransBlock extends Block {
	public boolean displayEfficiency = true;
	//速度 speed
	public float time = 60;
	//效率
	public Attribute attr;
	//效率倍增
	public float attrMultiplier = 1;
	public float baseEfficiency = 1;
	
	//环境倍增效率
	//流体输出
	@Override
	public void setBars() {
		super.setBars();
		
		if (!displayEfficiency) return;
		
		addBar("efficiency", (STransBuild entity) ->
			new Bar(
				() -> Core.bundle.format("bar.efficiency", (int) (entity.efficiency() * 100)),
				() -> Pal.lightOrange,
				entity::efficiency));
	}
	
	//环境影响
	@Override
	public boolean canPlaceOn(Tile tile, Team team, int rotation) {
		if (attr == null) return true;
		return baseEfficiency + tile.getLinkedTilesAs(this, tempTiles).sumf(other -> other.floor().attributes.get(attr)) > 0;
	}
	
	@Override
	public void setStats() {
		super.setStats();
		stats.add(baseEfficiency <= 0.0001f ? Stat.tiles : Stat.affinities, attr, floating, size * size, !displayEfficiency);
	}
	
	public STransBlock(String name) {
		super(name);
		hasLiquids = true;
	}
	
	public class STransBuild extends Building {
		//获取当前的效率
		public float efficiency() {
			return 0;
		}
		
		public void trans() {
		
		}
		
		@Override
		public void updateTile() {
			trans();
		}
	}
}
