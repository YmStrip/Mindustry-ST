package wool.module.render.renders;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import wool.module.render.entity.Render;

import java.util.ArrayList;

public class RenderFrame extends Render {
	public String name(String name, int frane) {
		var rel = offset + frane;
		var f = rel < 10 ? "000" + rel : rel < 100 ? "00" + rel : rel < 1000 ? "0" + rel : rel + "";
		return name + f;
	}
	public int offset = 0;
	public int fps = 24;
	public int frame = 0;
	public float frameCurrent = 0;
	public void frameIter(float df) {
		frameCurrent += df;
		if (frameCurrent > frame) frameCurrent = 0;
	}
	public void frameIter() {
		//1t = 1000/k
		//f(x) = 1000/f*x
		//g(t) = 1000/t*t
		//k=g/f=f/t
		frameIter(fps / 60f);
	}
	public ArrayList<TextureRegion> regions = new ArrayList<>();
	public void regions(String name, int fps, int start, int end) {
		this.frame = end - start;
		this.offset = start;
		this.fps = fps;
		regions.clear();
		for (var i = 0; i < frame; i++) {
			regions.add(Core.atlas.find(name(name, i)));
		}
	}
	public int frameIndex() {
		var index = Math.floor(frameCurrent);
		if (index < 0 || index >= regions.size() || regions.isEmpty()) return -1;
		return (int) index;
	}
	@Override
	public void render(float x, float y) {
		var index = frameIndex();
		if (index < 0) return;
		region = regions.get((int) index);
		super.render(x, y);
	}
}
