package wool.module.unit.render;


import wool.module.render.entity.Render;
import wool.module.render.entity.RenderEntity;
import wool.module.unit.entity.Unit;

public class RenderUnit extends RenderEntity {
	public Render renderFire = new Render("unit.fire");
	@Override
	public void load(Object object) {
		if (object instanceof Unit unit) {
			//
			renderLight.alpha.lock(1);
			renderLight.region(unit.name + "-light");
			renderLight.setScaleBlock(unit.size, sizeBase);
			renderLight.alpha.lock(1);
			//
			renderAnimate.alpha.lock(1);
			renderAnimate.setScaleBlock(unit.size, sizeBase);
			//
			renderFire.alpha.lock(1);
			renderFire.setScaleBlock(unit.size, sizeBase);
			//
			setScale(unit.baseRegion, unit.size);
			setScale(unit.legRegion, unit.size);
			setScale(unit.region, unit.size);
			setScale(unit.previewRegion, unit.size);
			setScale(unit.shadowRegion, unit.size);
			setScale(unit.cellRegion, unit.size);
			setScale(unit.itemCircleRegion, unit.size);
			setScale(unit.softShadowRegion, unit.size);
			setScale(unit.jointRegion, unit.size);
			setScale(unit.footRegion, unit.size);
			setScale(unit.legRegion, unit.size);
			setScale(unit.baseJointRegion, unit.size);
			setScale(unit.outlineRegion, unit.size);
			setScale(unit.treadRegion, unit.size);
			for (var i : unit.wreckRegions) setScale(i, unit.size);
			for (var i : unit.segmentRegions) setScale(i, unit.size);
			for (var i : unit.segmentOutlineRegions) setScale(i, unit.size);
		}
	}
	public void tick(Unit.UnitEntity unit) {
		super.tick(unit);
		renderFire.tick(unit);
	}
	@Override
	public void render(float x, float y, float rotate) {
		super.render(x, y, rotate);
		renderFire.setPos(x, y, rotate);
		renderFire.render();
	}
	@Override
	public RenderUnit copy() {
		var copy = new RenderUnit();
		copy.renderFire = renderFire.copy();
		copy(copy);
		return copy;
	}
}