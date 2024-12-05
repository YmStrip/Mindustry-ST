package wool.module.render.entity;


import arc.graphics.g2d.TextureRegion;
import wool.entity.JSON;

import java.util.HashMap;

public class RenderEntity {
	public int sizeRegion = 512;
	public int sizeBase = 512;
	//
	public Render renderLight = new Render("light");
	public Render renderAnimate = new Render("animate");
	//
	public void render(float x, float y, float rotate) {
		renderLight.setPos(x, y, rotate);
		renderAnimate.setPos(x, y, rotate);
		renderLight.render();
		renderAnimate.render();
	}
	public void load(Object object) {

	}
	public void setScale(TextureRegion region, int sizeBlock) {
		if (region == null) return;
		region.scale = sizeBlock * 32f / sizeRegion;
	}
	public void tick(Object o) {
		renderLight.tick(o);
		renderAnimate.tick(o);
	}
	public RenderEntity copy() {
		return copy(new RenderEntity());
	}
	protected RenderEntity copy(RenderEntity copy) {
		copy.renderLight = renderLight.copy();
		copy.renderAnimate = renderAnimate.copy();
		copy.sizeRegion = sizeRegion;
		copy.sizeBase = sizeBase;
		return copy;
	}
	public RenderEntity() {
		renderLight.alpha.clear();
		renderLight.alpha.set(0, 0);
		renderLight.alpha.set(1, 1);
	}
	public String toString() {
		return JSON.stringify(json(), null, "    ");
	}
	public HashMap<Object, Object> json() {
		return json(new HashMap<>());
	}
	public HashMap<Object, Object> json(HashMap<Object, Object> data) {
		var cls = getClass().getFields();
		for (var field : cls) {
			try {
				field.setAccessible(true);
				var o = field.get(this);
				if (o instanceof Render r) {
					r.json(data);
				}
			} catch (Exception e) {

			}
		}
		return data;
	}
}
