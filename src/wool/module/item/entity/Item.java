package wool.module.item.entity;


import arc.Core;
import arc.Events;
import arc.graphics.g2d.TextureRegion;
import mindustry.game.EventType;
import wool.entity.Color;
import wool.entity.Tooltip;
import wool.module.render.entity.Render;
import wool.module.render.modify.RenderModifyFrame;


public class Item extends mindustry.type.Item {
	public int size = 1;
	public int sizeBase = 512;
	public Render render = new Render();
	public RenderModifyFrame renderModifyFrame = new RenderModifyFrame(render);
	public Tooltip tooltip;
	public void diff() {
		if (tooltip != null) tooltip.clear();
		tooltip = new Tooltip(stats, Tooltip.cat());
		tooltip.setLevel(level);
		tooltip.set("damage", damage);
		tooltip.set("debug", render.toString());
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
	@FunctionalInterface
	public interface onLoad {
		void call();
	}

	onLoad onLoad;
	public void load(onLoad onLoad) {
		this.onLoad = onLoad;
	}
	@Override
	public void load() {
		super.load();
		if (onLoad != null) onLoad.call();
		diff();
	}
	public Item(String name) {
		super(name);
	}
	public void loadIcon() {
		this.fullIcon = Core.atlas.find(this.getContentType().name() + "-" + this.name + "-full", Core.atlas.find(this.name + "-full", Core.atlas.find(this.name, Core.atlas.find(this.getContentType().name() + "-" + this.name, Core.atlas.find(this.name + "1")))));
		this.uiIcon = Core.atlas.find(this.getContentType().name() + "-" + this.name + "-ui", this.fullIcon);
		uiIcon.scale = (float) (size * 32) / sizeBase;
		fullIcon.scale = (float) (size * 32) / sizeBase;
		if (this.renderModifyFrame.regions.isEmpty()) return;
		this.fullIcon = new TextureRegion(this.fullIcon);
		this.uiIcon = new TextureRegion(this.uiIcon);
		Events.run(EventType.Trigger.update, () -> {
			renderModifyFrame.tickIter();
			var index = renderModifyFrame.frameIndex();
			var region = renderModifyFrame.regions.get(index);
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