package wool.module.render.entity;

import wool.entity.JSON;

import java.util.HashMap;

public class RenderModify {
	public String label = "Modify";
	public Render render;
	public boolean enable = true;
	public void onSolve(float key) {
	}
	public void onRender(float key) {

	}
	public boolean onRenderPrevent(float key) {
		return false;
	}
	public RenderModify copy() {
		var copy = new RenderModify();
		return copy(copy);
	}
	protected RenderModify copy(RenderModify copy) {
		copy.label = label;
		copy.enable = enable;
		return copy;
	}
	public RenderModify() {

	}
	public RenderModify(Render render) {
		this.render = render;
		if (render != null && !render.modify.contains(this)) {
			render.modify.add(this);
		}
	}
	public void tick(Object object) {
	}
	@Override
	public String toString() {
		return JSON.stringify(json(), null, "    ");
	}
	public HashMap<Object, Object> json() {
		return json_(new HashMap<>());
	}
	private HashMap<Object, Object> json_(HashMap<Object, Object> data) {
		var ch = new HashMap<>();
		data.put("Modify(" + label + ")", ch);
		ch.put("enable", enable);
		json(ch);
		return data;
	}
	protected void json(HashMap<Object, Object> data) {

	}
}
