package wool.module.render.modify;

import arc.graphics.g2d.Draw;
import wool.module.render.entity.Render;
import wool.module.render.entity.RenderModify;

public class RenderModifyMirror extends RenderModify {
	public RenderModifyMirror(Render key) {
		super(key);
	}
	public RenderModifyMirror() {
		super();
	}
	public String label = "mirror";
	public boolean mirrorX = true;
	//public boolean mirrorY = false;

	public enum aligns {
		center
	}

	public aligns align = aligns.center;


	@Override
	public void onRender(float key) {
		super.onRender(key);
		if (mirrorX) {
			if (align == aligns.center) {
				var x = render.x.solve[0] - render.offset.solve[0];
				var y = render.y.solve[1] - render.offset.solve[1];
				Draw.rect(render.region, x, y, render.rotate.solve[0]);
			}
		}
	}
	@Override
	public RenderModifyMirror copy() {
		var copy = new RenderModifyMirror();
		copy(copy);
		copy.mirrorX = this.mirrorX;
		copy.align = this.align;
		return copy;
	}
}
