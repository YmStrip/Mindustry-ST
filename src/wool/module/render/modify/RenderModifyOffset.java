package wool.module.render.modify;


import wool.module.render.entity.Render;
import wool.module.render.entity.RenderKey;
import wool.module.render.entity.RenderModify;

import java.util.HashMap;

public class RenderModifyOffset extends RenderModify {
	{
		label = "offset";
	}

	public RenderModifyOffset() {

	}
	public RenderModifyOffset(Render render) {
		super(render);
	}
	public float rotate = 0;
	public RenderKey offset = new RenderKey(0, 0).label("offset");
	@Override
	public void onSolve(float key) {
		offset.solve(key);
		var deg = rotate + render.rad.solve[0];
		render.x.solve[0] += (float) (offset.solve[0] * Math.cos(deg) - offset.solve[1] * Math.sin(deg));
		render.y.solve[0] += (float) (offset.solve[1] * Math.cos(deg) + offset.solve[0] * Math.sin(deg));
	}
	@Override
	public RenderModifyOffset copy() {
		var copy = new RenderModifyOffset();
		copy(copy);
		copy.rotate = rotate;
		copy.offset = offset.copy();
		return copy;
	}
	@Override
	protected void json(HashMap<Object, Object> data) {
		this.offset.json(data);
	}
}
