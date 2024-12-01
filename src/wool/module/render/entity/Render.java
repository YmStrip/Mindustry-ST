package wool.module.render.entity;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import wool.entity.Transition;


public class Render {
	public TextureRegion region;
	public void region(TextureRegion region) {
		this.region = region;
	}
	public void region(String region) {
		this.region = Core.atlas.find(region);
	}

	public float scale = 1;
	public float rotate = 0;
	//multi call once -> tick
	public float tick = 0;
	public boolean tick() {
		var time = System.currentTimeMillis();
		if (time >= tick + 1000 / 60f) {
			tick = time;
			return true;
		}
		return false;
	}


	public void scaleBlock(int size, int sizeRegion) {
		scale = size * 32f / sizeRegion;
	}
	public boolean cycleEnable = false;
	public float cycle = 800;
	public float cycleCurrent = 0;
	public float cycleClamp = .5f;
	public void cycleIter(float k) {
		//sin(x+w) = sin(x)
		//w = 2pi
		//f(z) = 60*dw = 2pi while z = 1000
		//f(z*s) = s * f(z) = 60 * dw / s = 2pi
		//dw = 2pi * s / 60
		//1000 * s = r
		//s = r / 1000
		cycleCurrent += (float) (Math.PI * 2f * (cycle / 1000) / 60) * k;
	}
	public void cycleIter() {
		cycleIter(1);
	}
	public float cycleCalc() {
		return (float) ((cycleClamp / 2) * Math.sin(cycleCurrent) + (1 - (cycleClamp / 2)));
	}
	public boolean transitionEnable = true;
	public float transition = 800;
	public float transitionCurrent = 0;
	public void transitionIter(boolean add,float k) {
		transitionCurrent = Math.min(transition, Math.max(transitionCurrent + (add ? k * 1000 / 60f : k * -1000 / 60f), 0));
	}
	public void transitionIter(boolean add) {
		transitionIter(add,1);
	}
	public float transitionNorm() {
		return Transition.norm(transitionCurrent, 0, transition);
	}
	public float transitionIterCalc() {
		var norm = transitionNorm();
		return Transition.l2(norm, 0, 1);
	}
	public float alpha() {
		return (transitionEnable ? transitionIterCalc() : 1) * (cycleEnable ? cycleCalc() : 1);
	}
	public void renderStart() {
		renderStart(alpha());
	}
	public void renderStart(float alpha) {
		Draw.alpha(alpha);
		Draw.scl *= scale;
	}
	public void renderEnd() {
		Draw.scl /= scale;
		Draw.alpha(1);
	}
	public void render(float x, float y) {
		if (region==null) return;
		renderStart();
		Draw.rect(region, x, y, rotate);
		renderEnd();
	}
	public void render(float x, float y, float rotate) {
		this.rotate = rotate;
		this.render(x, y);
	}
	public void renderAlpha(float x, float y, float alpha) {
		if (region==null) return;
		renderStart(alpha);
		Draw.rect(region, x, y, rotate);
		renderEnd();
	}
	public void renderAlpha(float x, float y, float alpha, float rotate) {
		this.rotate = rotate;
		renderAlpha(x, y, alpha);
	}
}
