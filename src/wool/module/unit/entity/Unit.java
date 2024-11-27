package wool.module.unit.entity;

import arc.graphics.g2d.TextureRegion;
import mindustry.type.UnitType;
import wool.module.render.renders.RenderFrame;

public class Unit extends UnitType {
	public Unit(String name) {
		super(name);
		constructor = UnitEntity::create;
	}
	public int size = 2;
	public int sizeBase = 512;
	public RenderFrame renderBottom = new RenderFrame() {{
		cycle = 800;
		cycleEnable = false;
		transitionEnable = false;
		transition = 0;
	}};
	public RenderFrame renderTop = new RenderFrame() {{
		cycle = 800;
		cycleEnable = false;
		transitionEnable = false;
		transition = 0;
	}};
	public void setScale(TextureRegion region, int size) {
		if (region==null) return;
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
	}
	@Override
	public void drawBody(mindustry.gen.Unit unit) {
		renderBottom.render(unit.x, unit.y, unit.rotation - 90f);
		super.drawBody(unit);
		renderTop.render(unit.x, unit.y, unit.rotation - 90f);
	}
	@Override
	public void update(mindustry.gen.Unit unit) {
		super.update(unit);
		if (renderBottom.tick()) {
			onTick();

		}
	}
	public void onTick() {
		renderBottom.frameIter();
		renderBottom.cycleIter();
		renderTop.frameIter();
		renderTop.cycleIter();
	}
	{
		drawBody = true;
	}
	public class UnitEntity extends mindustry.gen.UnitEntity {

	}
}