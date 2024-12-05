package wool.module.render.entity;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import org.json.simple.JSONObject;
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
	//offset
	public RenderKey offset = new RenderKey(0, 0).label("offset");
	public RenderKey scale = new RenderKey(1).label("scale");
	public RenderKey rotate = new RenderKey(0).label("rotate");
	public RenderKey alpha = new RenderKey(1).label("alpha");
	public RenderKey x = new RenderKey(0).label("x");
	public RenderKey y = new RenderKey(0).label("y");
	//
	public RenderIter iter = new RenderIter(500);
	public void iter(boolean add) {
		iter.iter(add);
	}
	//
	public void setPos(float x, float y) {
		this.x.same(x);
		this.y.same(y);
	}
	public void setPos(float x, float y, float rotate) {
		setPos(x, y);
		setRotate(rotate);
	}
	public void setRotate(float rotate) {
		this.rotate.same(rotate);
	}
	public void setScale(float scale) {
		this.scale.same(scale);
	}
	//
	public void setScaleBlock(int size, int sizeRegion) {
		var a = Math.max(size * 32f / sizeRegion, .1f);
		scale.same(a);
	}
	public void solve(float key) {
		prevent = false;
		if (!enable) return;
		if (region == null) return;
		//solve current
		this.alpha.solve(key);
		this.scale.solve(key);
		this.offset.solve(key);
		this.rotate.solve(key);
		this.x.solve(key);
		this.y.solve(key);
		if (this.modifyEnable) {
			for (int i = -1, l = this.modify.size(); ++i < l; ) {
				var modify = this.modify.get(i);
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
		//transform current
		this.offset.solveRotateOffset(rotate);
		//transform parent
		if (parent != null) {
			this.alpha.solveMul(parent.alpha);
			this.scale.solveMul(parent.scale);
			this.rotate.solveAdd(parent.rotate);
			//rotate current
			this.x.solveRotateX(y, parent.rotate);
			this.y.solveRotateY(x, parent.rotate);
			//add pos
			this.x.solveAdd(parent.x);
			this.y.solveAdd(parent.y);
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
			Draw.rect(region, x.solve[0] + offset.solve[0], y.solve[0] + offset.solve[1], rotate.solve[0]);
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
	public Render copy() {
		var render = new Render();
		copy(render);
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
	protected void copy(Render render) {
		render.enable = enable;
		render.region = region;
		render.offset = offset.copy();
		render.x = x.copy();
		render.y = y.copy();
		render.scale = scale.copy();
		render.alpha = alpha.copy();
		render.iter = iter.copy();
		render.zIndex = zIndex;
		for (var i : this.modify) {
			var copy = i.copy();
			copy.render = render;
			render.modify.add(copy);
		}
		for (var i : child) {
			render.child(i.copy());
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
		this.child.add(child);
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
		data.put("Render(" + label + ")", ch);
		x.json(ch);
		y.json(ch);
		scale.json(ch);
		alpha.json(ch);
		rotate.json(ch);
		offset.json(ch);
		ch.put("region", (this.region != null && this.region.found() ? "[found]" : "[notfound]") + " " + (this.region));

		if (!this.modify.isEmpty()) {
			var modify = new HashMap<>();
			ch.put("modify", modify);
			for (var i : this.modify) {
				i.json(modify);
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
	public void debugElement() {
	}
}
