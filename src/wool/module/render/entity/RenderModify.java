package wool.module.render.entity;

import wool.entity.JSON;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class RenderModify {
	public RenderModify() {

	}
	public RenderModify(Render render) {
		onInit(render);
	}
	//
	public String label = "Modify";
	public Render render;
	public boolean enable = true;
	public void onSolve(float key) {
	}
	public void onRender(float key) {

	}
	public void onInit(Render render) {
		this.render = render;
		if (render.copying) return;
		if (!render.modify.contains(this)) {
			render.modify.add(this);
		}
	}
	public boolean onRenderPrevent(float key) {
		return false;
	}
	//
	public abstract RenderModify copy();
	public void copyAfter(RenderCopy copy) {

	}

	protected RenderModify copy(RenderModify copy) {
		copy.label = label;
		copy.enable = enable;
		return copy;
	}
	public void tick(Object object) {
	}
	@Override
	public String toString() {
		return JSON.stringify(json(), null, "    ");
	}
	public HashMap<Object, Object> json() {
		return jsonPut(new HashMap<>());
	}
	public HashMap<Object, Object> jsonPut(HashMap<Object, Object> data) {
		var ch = new HashMap<>();
		var key = JSON.nextKey(data, "Modify(" + this.label + ")", iter -> "Modify(" + this.label + "#" + iter + ")");
		data.put(key, ch);
		ch.put("enable", enable);
		json(ch);
		return data;
	}

	protected void json(HashMap<Object, Object> data) {

	}
}
