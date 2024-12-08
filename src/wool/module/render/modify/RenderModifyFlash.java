package wool.module.render.modify;

import wool.module.render.entity.Render;
import wool.module.render.entity.RenderModify;

import java.util.HashMap;

public class RenderModifyFlash extends RenderModify {
	{
		label = "flash";
	}
	public RenderModifyFlash(Render key) {
		super(key);
	}
	public RenderModifyFlash() {
	}
	public float flashTotal = 800;
	public float flash = 0;
	//min cycle
	public float flashMin = .5f;
	public void tickIter() {
		tickIter(1);
	}
	public void tickIter(float k) {
		//sin(x+w) = sin(x)
		//w = 2pi
		//f(z) = 60*dw = 2pi while z = 1000
		//f(z*s) = s * f(z) = 60 * dw / s = 2pi
		//dw = 2pi * s / 60
		//1000 * s = r
		//s = r / 1000
		flash += (float) (Math.PI * 2f * (flashTotal / 1000) / 60) * k;
	}
	public float calc() {
		return (float) ((flashMin / 2) * Math.sin(flash) + (1 - (flashMin / 2)));
	}
	//

	@Override
	public void onSolve(float key) {
		super.onSolve(key);
		var k = calc();
		render.alpha.forEach(((index, value, solve) -> {
			solve[index] *= k;
		}));
	}
	@Override
	public RenderModifyFlash copy() {
		var copy = new RenderModifyFlash();
		copy(copy);
		copy.flashTotal = this.flashTotal;
		copy.flash = this.flash;
		copy.flashMin = this.flashMin;
		return copy;
	}
	@Override
	public void tick(Object object) {
		tickIter();
	}
	@Override
	protected void json(HashMap<Object, Object> data) {
		data.put("min", flashMin);
		data.put("solve", calc());
	}
}
