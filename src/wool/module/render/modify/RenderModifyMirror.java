package wool.module.render.modify;

import arc.graphics.g2d.Draw;
import wool.module.render.entity.Render;
import wool.module.render.entity.RenderCopy;
import wool.module.render.entity.RenderKey;
import wool.module.render.entity.RenderModify;

import java.util.ArrayList;
import java.util.HashMap;

public class RenderModifyMirror extends RenderModify {
	{
		label = "mirror";
	}

	public RenderModifyMirror(Render key) {
		super(key);
		align(key);
	}
	public RenderModifyMirror() {
		super();

	}
	public boolean mirrorX = true;
	public boolean flipX = true;
	public boolean mirrorY = false;
	public boolean flipY = false;
	//public boolean mirrorY = false;
	public Render align;
	public void align(Render align) {
		this.align = align;
	}
	@Override
	public void onRender(float key) {
		super.onRender(key);
		if (align == null || render.region == null) return;
		if (mirrorX) {
			render.region.flip(flipX, flipY);
			var dx = align.x0 - render.x.solve[0];
			var dy = align.y0 - render.y.solve[0];

			//Draw.rect(render.region, x, y, (float) Math.toDegrees(render.rad.solve[0]));
			render.region.flip(flipX, flipY);
		}
	}
	@Override
	public RenderModifyMirror copy() {
		var copy = new RenderModifyMirror();
		copy(copy);
		copy.mirrorX = mirrorX;
		copy.mirrorY = mirrorY;
		copy.align = align;
		return copy;
	}
	@Override
	public void copyAfter(RenderCopy copy) {
		var index = copy.renderFrom.indexOf(align);
		if (index != -1) align = copy.renderTo.get(index);
	}
	@Override
	protected void json(HashMap<Object, Object> data) {
		data.put("align", "Render(" + align.label + ")");
		data.put("mirrorX", mirrorX);
		data.put("mirrorY", mirrorY);
	}
}
