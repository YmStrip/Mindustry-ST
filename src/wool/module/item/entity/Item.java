package wool.module.item.entity;


import arc.Core;
import arc.Events;
import arc.graphics.g2d.TextureRegion;
import mindustry.game.EventType;
import wool.entity.Color;
import wool.entity.Tooltip;
import wool.module.render.renders.RenderFrame;


public class Item extends mindustry.type.Item {
	public int size = 1;
	public int sizeBase = 512;
	public RenderFrame renderFrame = new RenderFrame();
	public void diff() {
		var tooltip = new Tooltip(stats, Tooltip.cat());
		tooltip.setLevel(level);
		tooltip.set("damage", damage);
	}

	public int level = 0;
	public float damage = 5;

	public Item Color(String color) {
		this.color = new Color(color).to();
		return this;
	}

	public Item Color(Color color) {
		this.color = new Color(color).to();
		return this;
	}

	public Item(String name) {
		super(name);
	}
	public void loadIcon() {
		this.fullIcon = Core.atlas.find(this.getContentType().name() + "-" + this.name + "-full", Core.atlas.find(this.name + "-full", Core.atlas.find(this.name, Core.atlas.find(this.getContentType().name() + "-" + this.name, Core.atlas.find(this.name + "1")))));
		this.uiIcon = Core.atlas.find(this.getContentType().name() + "-" + this.name + "-ui", this.fullIcon);
		uiIcon.scale = (float) (size * 32) / sizeBase;
		fullIcon.scale = (float) (size * 32) / sizeBase;
		if (this.renderFrame.regions.isEmpty()) return;
		this.fullIcon = new TextureRegion(this.fullIcon);
		this.uiIcon = new TextureRegion(this.uiIcon);
		Events.run(EventType.Trigger.update, () -> {
			renderFrame.frameIter();
			var index = renderFrame.frameIndex();
			var region = renderFrame.regions.get(index);
			if (region == null) return;
			uiIcon.set(region);
			fullIcon.set(region);
			uiIcon.scale = (float) (size * 32) / sizeBase;
			fullIcon.scale = (float) (size * 32) / sizeBase;
		});
	}
	{
		alwaysUnlocked = false;
	}
}