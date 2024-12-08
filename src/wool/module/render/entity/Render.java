package wool.module.render.entity;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import wool.entity.JSON;
import wool.module.render.modify.RenderModifyFlash;
import wool.module.render.modify.RenderModifyFrame;

import java.util.ArrayList;
import java.util.HashMap;


public class Render {
	public Render() {
	}
	public Render(String label) {
		this();
		label(label);
	}
	public String label = "";
	public void label(String label) {
		this.label = label;
	}
	public boolean prevent = false;
	public boolean enable = true;
	public TextureRegion region;
	public void region(TextureRegion region) {
		this.region = region;
	}
	public void region(String region) {
		this.region = Core.atlas.find(region);
	}
	public float zIndex = 55;
	//modify
	public boolean modifyEnable = true;
	public ArrayList<RenderModify> modify = new ArrayList<>();
	public RenderKey scale = new RenderKey(1).label("scale");
	// == rad
	public RenderKey rad = new RenderKey(0).label("rad");
	public RenderKey alpha = new RenderKey(1).label("alpha");
	public RenderKey x = new RenderKey(0).label("x");
	public RenderKey y = new RenderKey(0).label("y");
	public float x0 = 0;
	public float y0 = 0;
	public float scale0 = 1;
	public float alpha0 = 1;
	public float rad0 = 0;
	//
	public RenderIter iter = new RenderIter(500);
	public void iter(boolean add) {
		iter.iter(add);
	}
	//
	public void setPos(float x, float y) {
		this.x.lock(x);
		this.y.lock(y);
	}
	public void setPos(float x, float y, float rotate) {
		setPos(x, y);
		setRad((float) (Math.toRadians(rotate)));
	}
	public void setRad(float rad) {
		this.rad.lock(rad);
	}
	public void setScale(float scale) {
		this.scale.lock(scale);
	}
	//
	public void setScaleBlock(int size, int sizeRegion) {
		var a = Math.max(size * 32f / sizeRegion, .1f);
		scale.lock(a);
	}
	public void solve(float key) {
		prevent = false;
		if (!enable) return;
		if (region == null) return;
		//solve current
		this.alpha.solve(key);
		this.scale.solve(key);
		this.rad.solve(key);
		this.x.solve(key);
		this.y.solve(key);
		//transform parent first
		if (parent != null) {
			this.alpha.solveMul(parent.alpha);
			this.scale.solveMul(parent.scale);
			this.rad.solveAdd(parent.rad);
			//position
			this.x.solveAdd(parent.x);
			this.y.solveAdd(parent.y);
		}
		this.x0 = x.solve[0];
		this.y0 = y.solve[0];
		this.alpha0 = alpha.solve[0];
		this.scale0 = scale.solve[0];
		this.rad0 = rad.solve[0];

		if (this.modifyEnable) {
			for (int i = -1, l = this.modify.size(); ++i < l; ) {
				var modify = this.modify.get(i);
				if (modify.render == null) modify.onInit(this);
				if (modify.enable) {
					modify.onSolve(key);
				}
			}
		}
		if (this.modifyEnable) {
			for (int i = -1, l = this.modify.size(); ++i < l; ) {
				var modify = this.modify.get(i);
				if (modify.enable) {
					if (modify.onRenderPrevent(key)) prevent = true;
				}
			}
		}

		//solve child
		for (var i : child) {
			i.solve(key);
		}
		//
	}
	public void solveRender(float key) {
		if (scale.solve[0] <= 0) scale.solve[0] = 1f;
		Draw.alpha(alpha.solve[0]);
		var scl = Draw.scl;
		Draw.scl *= scale.solve[0];
		Draw.z(this.zIndex);
		//the javaFucker
		//System.out.println("x.keyframe=" + x.keyframe.size());
		//System.out.println("has-parent=" + (parent != null) + "prevent = " + prevent + ",x=" + x.solve[0] + ",y=" + y.solve[0] + ",rotate=" + rotate.solve[0] + ",scale=" + scale.solve[0] + ",offset=" + offset.solve[0] + "," + offset.solve[1] + ",iter=" + iter.key + ",region=" + (region != null) + ",region-found=" + (region != null && region.found()));
		//
		if (!prevent && region != null) {
			/*System.out.println(region + " ,x=" + x.solve[0] + ",alpha=" + alpha.solve[0]+",iter="+iter.key+",alphaKey"+alpha.keyframe.size()+",key0="+(
				alpha.keyframe.size()>=1?alpha.keyframe.get(0):null
				)+",key1="+(
				alpha.keyframe.size()>=2?alpha.keyframe.get(1):null)
			);*/
			/*for (var i : alpha.keyframe) {
				System.out.println("k="+i.key);
				System.out.println("v="+i.value);
			}*/
			Draw.rect(region, x.solve[0], y.solve[0], (float) Math.toDegrees(rad.solve[0]));
		}
		if (this.modifyEnable) {
			for (int i = -1, l = this.modify.size(); ++i < l; ) {
				var modify = this.modify.get(i);
				if (modify.enable) {
					modify.onRender(key);
				}
			}
		}
		//
		Draw.scl = scl;
		if (Draw.scl <= 0) Draw.scl = 1;
		Draw.alpha(1);
		//
		for (var i : child) {
			i.solveRender(key);
		}
	}
	public void render(float key) {
		solve(key);
		solveRender(key);
	}
	public void render() {
		render(iter.key);
	}
	public boolean copying = false;
	public Render copy() {
		var copy = new RenderCopy();
		var render = copy(copy);
		for (var i = copy.modifyTo.size(); --i >= 0; ) {
			var modify = copy.modifyTo.get(i);
			modify.copyAfter(copy);
		}
		return render;
	}
	public Render copy(RenderCopy copy) {
		copying = true;
		copy.renderFrom.add(this);
		var render = new Render();
		copy.renderTo.add(render);
		copy(render, copy);
		copying = false;
		return render;
	}
	public void tick(Object object) {
		if (this.modifyEnable) for (var i : this.modify) {
			if (i.enable) i.tick(object);
		}
		for (var i : child) {
			if (i.enable) i.tick(object);
		}
	}
	protected void copy(Render render, RenderCopy copy) {
		render.enable = enable;
		render.region = region;
		render.x = x.copy();
		render.y = y.copy();
		render.scale = scale.copy();
		render.alpha = alpha.copy();
		render.iter = iter.copy();
		render.zIndex = zIndex;
		for (var i : this.modify) {
			var modify = i.copy();
			copy.modifyFrom.add(i);
			modify.onInit(render);
			copy.modifyTo.add(modify);
		}
		for (var i : child) {
			var ch = i.copy(copy);
			render.child(ch);
		}
	}
	//
	public RenderModifyFrame modifyFrame() {
		return new RenderModifyFrame(this);
	}
	public RenderModifyFlash modifyFlash() {
		return new RenderModifyFlash(this);
	}
	//
	public Render parent;
	public ArrayList<Render> child = new ArrayList<>();
	public void child(Render child) {
		if (!this.child.contains(child)) this.child.add(child);
		child.zIndex = zIndex + .1f;
		child.parent = this;
	}
	public void childClear() {
		for (var i : this.child) {
			i.parent = null;
		}
		this.child.clear();
	}
	@Override
	public String toString() {
		return JSON.stringify(json(), null, "    ");
	}
	public HashMap json() {
		return json(new HashMap<>());
	}
	public HashMap<Object, Object> json(HashMap<Object, Object> data) {
		var ch = new HashMap<>();
		var key = JSON.nextKey(data, "Render(" + label + ")", iter -> "Render(" + label + "#" + iter + ")");
		data.put(key, ch);
		x.json(ch);
		y.json(ch);
		scale.json(ch);
		alpha.json(ch);
		rad.json(ch);
		ch.put("region", (this.region != null && this.region.found() ? "[found]" : "[notfound]") + " " + (this.region));

		if (!this.modify.isEmpty()) {
			var modify = new HashMap<>();
			ch.put("modify", modify);
			for (var i : this.modify) {
				i.jsonPut(modify);
			}
		}
		if (!this.child.isEmpty()) {
			var child = new HashMap<>();
			ch.put("child", child);
			for (var i : this.child) {
				i.json(child);
			}
		}
		return data;
	}
}
