package wool.module.render.modify;

import arc.Core;
import arc.graphics.g2d.TextureRegion;
import wool.module.render.entity.Render;
import wool.module.render.entity.RenderModify;

import java.util.ArrayList;
import java.util.HashMap;

public class RenderModifyFrame extends RenderModify {
	public RenderModifyFrame(Render key) {
		super(key);
	}
	public RenderModifyFrame() {
		super();
	}
	public boolean iterFrame = true;
	public boolean iterFrameRepeat = true;
	public String label = "frame";
	public String name(String name, int frane) {
		var rel = offset + frane;
		var f = rel < 10 ? "000" + rel : rel < 100 ? "00" + rel : rel < 1000 ? "0" + rel : rel + "";
		return name + f;
	}
	public ArrayList<TextureRegion> regions = new ArrayList<>();
	public RenderModifyFrame regions(String name, int fps, int start, int end) {
		this.frame = end - start;
		this.offset = start;
		this.fps = fps;
		regions.clear();
		for (var i = 0; i < frame; i++) {
			regions.add(Core.atlas.find(name(name, i)));
		}
		return this;
	}
	public int offset = 0;
	public int fps = 24;
	public int frameTotal = 0;
	public float frame = 0;
	public float frameClamp(float v) {
		if (v > this.frameTotal) v = this.frameTotal;
		if (v < 0) v = 0;
		return v;
	}
	public int frameIndex() {
		var index = Math.floor(frame);
		if (index < 0 || index >= regions.size() || regions.isEmpty()) return -1;
		return (int) index;
	}
	public void tickAdd() {
		frame = frameClamp(frame + fps / 60f);
	}
	public void tickSub() {
		frame = frameClamp(frame - fps / 60f);
	}
	public void tickIter() {
		tickAdd();
		if (frame > frameTotal) {
			frame = 0;
		}
	}
	public void tickNorm(float norm) {
		this.frame = frameClamp(this.frameTotal * norm);
	}
	public void link(RenderModifyFrame modify) {
		this.regions = modify.regions;
	}
	@Override
	public void tick(Object object) {
		tickIter();
	}
	@Override
	public void onSolve(float key) {
		var index = frameIndex();
		if (index < 0) return;
		this.render.region = regions.get((int) index);
	}
	@Override
	public RenderModifyFrame copy() {
		var copy = new RenderModifyFrame();
		copy(copy);
		copy.iterFrame = this.iterFrame;
		copy.iterFrameRepeat = this.iterFrameRepeat;
		copy.label = this.label;
		copy.regions = this.regions;
		copy.offset = this.offset;
		copy.fps = this.fps;
		copy.frame = this.frame;
		copy.frameTotal = this.frameTotal;
		return copy;
	}
	@Override
	protected void json(HashMap<Object, Object> r) {
		r.put("fps", fps);
		r.put("frameTotal", frameTotal);
		r.put("offset", offset);
		r.put("solve", frame);
		r.put("region", (regions.isEmpty() ? "[notfound]" : regions.get(0).found()) + " " + (regions.isEmpty() ? "" : regions.get(0)));
	}
}
