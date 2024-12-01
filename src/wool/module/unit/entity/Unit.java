package wool.module.unit.entity;

import arc.graphics.g2d.TextureRegion;
import arc.math.geom.Vec2;
import mindustry.type.UnitType;
import wool.module.render.entity.Render;
import wool.module.render.renders.RenderFrame;

public class Unit extends UnitType {
	public Unit(String name) {
		super(name);
		constructor = UnitEntity::create;
	}
	public int size = 2;
	public int sizeBase = 512;
	public RenderFrame renderFire = new RenderFrame() {{
		cycleEnable = false;
		transitionEnable = false;
	}};
	public Render renderTop = new Render() {{
		cycleEnable = false;
		transitionEnable = false;
	}};
	public RenderFrame renderHud = new RenderFrame() {{
		cycleEnable = false;
		transitionEnable = false;
	}};
	public RenderFrame renderSpeed = new RenderFrame() {{
		cycleEnable = false;
		transitionEnable = false;
	}};
	public void setScale(TextureRegion region, int size) {
		if (region == null) return;
		region.scale = this.size * 32f / size;
	}
	public void setScale(TextureRegion region) {
		this.setScale(region, sizeBase);
	}
	@Override
	public void load() {
		super.load();
		setScale(baseRegion);
		setScale(legRegion);
		setScale(region);
		setScale(previewRegion);
		setScale(shadowRegion);
		setScale(cellRegion);
		setScale(itemCircleRegion);
		setScale(softShadowRegion);
		setScale(jointRegion);
		setScale(footRegion);
		setScale(legRegion);
		setScale(baseJointRegion);
		setScale(outlineRegion);
		setScale(treadRegion);
		for (var i : wreckRegions) setScale(i);
		for (var i : segmentRegions) setScale(i);
		for (var i : segmentOutlineRegions) setScale(i);
		renderTop.scaleBlock(size, sizeBase);
		renderFire.scaleBlock(size, sizeBase);
		renderSpeed.scaleBlock(size * 4, sizeBase);
		renderHud.scaleBlock(size * 4, sizeBase);
		renderTop.region(name + "-top");
		renderHud.regions("wool-UnitHud", 24, 48, 96);
		renderSpeed.regions("wool-UnitSpeedline", 24, 48, 96);
	}
	@Override
	public void drawBody(mindustry.gen.Unit unit) {
		renderFire.render(unit.x, unit.y, unit.rotation - 90f);
		super.drawBody(unit);
		renderTop.render(unit.x, unit.y, unit.rotation - 90f);
	}
	@Override
	public void update(mindustry.gen.Unit unit) {
		super.update(unit);
		if (renderFire.tick()) {
			onTick();
		}
	}
	public void onTick() {
		renderFire.frameIter();
		renderSpeed.frameIter();
		renderHud.frameIter();
	}
	{
		drawBody = true;
	}

	public class UnitEntity extends mindustry.gen.UnitEntity {
		public RenderFrame renderSpeed = new RenderFrame() {{
			cycleEnable = false;
			transitionEnable = false;
		}};
		public RenderFrame renderHud = new RenderFrame() {{
			cycleEnable = false;
			transitionEnable = false;
		}};
		@Override
		public void update() {
			super.update();
			renderHud.frameIter();
			//renderHud.transitionIter(isPlayer());
			renderSpeed.transitionIter(isPlayer());
			renderSpeed.transitionIter(true);
		}
		@Override
		public void move(Vec2 v) {
			super.move(v);
			renderSpeed.frameIter();
			renderSpeed.transitionIter(true, 10);
			System.out.println("move");
		}
	}
}