package st.plugin.story.provider;

import arc.graphics.Color;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import layer.layer.Logger;
import mindustry.game.Team;
import mindustry.graphics.Pal;
import mindustry.graphics.g3d.HexMesh;
import mindustry.graphics.g3d.HexSkyMesh;
import mindustry.graphics.g3d.MultiMesh;
import mindustry.graphics.g3d.SunMesh;
import mindustry.maps.planet.SerpuloPlanetGenerator;
import mindustry.type.Planet;

@Provider
public class PlanetProvider extends LayerProvider {
	@Require(cls = PlanetG.class)
	PlanetG planetG;
	public Planet 五行星系 = new Planet("五行星系", null, 5f) {{
		bloom = true;
		accessible = false;
		alwaysUnlocked = true;
		meshLoader = () -> new SunMesh(
			this, 5,
			5, 0.3, 1.7, 1.2, 1,
			1.1f,
			Color.valueOf("25c948"),
			Color.valueOf("28da4e"),
			Color.valueOf("2be853"),
			Color.valueOf("2cef55"),
			Color.valueOf("50f8a7"),
			Color.valueOf("6df8a5")
		);
	}};
	public Planet 五行星 = new Planet("五行星", 五行星系, 1f, 3) {{
		generator = new SerpuloPlanetGenerator();
		meshLoader = () -> new HexMesh(this, 5);
		cloudMeshLoader = () -> new MultiMesh(
			new HexSkyMesh(this, 11, 0.15f, 0.13f, 6, new Color().set(Pal.spore).mul(0.9f).a(0.75f), 2, 0.45f, 0.9f, 0.38f),
			new HexSkyMesh(this, 1, 0.6f, 0.16f, 5, Color.white.cpy().lerp(Pal.spore, 0.55f).a(0.75f), 2, 0.45f, 1f, 0.41f)
		);
		//启动时核心物品容量的乘数
		launchCapacityMultiplier = 0.8f;
		//噪音种子
		sectorSeed = 32768;
		//如果属实，那么行业损失就会引发波浪。 to do 删除
		allowWaves = true;
		//是否允许扇区在后台模拟波浪。
		allowWaveSimulation = true;
		//是否模拟来自敌方基地的扇区入侵。
		allowSectorInvasion = true;
		//是否允许用户为此地图指定自定义启动原理图。
		allowLaunchSchematics = true;
		//是否允许用户指定他们使用该地图的资源。
		allowLaunchLoadout = true;
		//如果为真，则核心半径内的方块将被移除并在着陆时以冲击波“堆积”。
		prebuildBase = true;
		//为这个星球上的任何部门设置游戏负载规则。
		ruleSetter = r -> {
			//敌人的队伍在波浪/区域中
			r.waveTeam = Team.crux;
			//如果为 true，则无法将物块放置在靠近敌方队伍的物块附近。
			r.placeRangeCheck = true;
			//如果为 true，则会显示单位生成点。
			r.showSpawns = true;
		};
		//行星列表中出现的图标。
		iconColor = Color.valueOf("0af804");
		//可登陆行星的大气色调。
		atmosphereColor = Color.valueOf("55e283");
		//大气半径高度区间
		atmosphereRadIn = 0.02f;
		atmosphereRadOut = 0.3f;
		//显示在地图对话框中的默认起始扇区
		startSector = 1;
		//该内容是否始终在科技树中解锁。
		alwaysUnlocked = true;
		//着陆时显示云彩。
		landCloudColor = Pal.spore.cpy().a(0.5f);
		//这个星球上没有的物品。为了向后兼容而保留。
		//hiddenItems.addAll(Items.erekirItems).removeAll(Items.serpuloItems);
	}};
	public Planet 五行星_主卫星;
	public Planet 暗黑星;
	@Require
	Logger logger;
	
	@Override
	public void run() {
		logger.suc("星球注册完成!");
	}
}
