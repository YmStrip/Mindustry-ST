package st.addon.unit.provider;

import arc.scene.ui.layout.Table;
import layer.annotations.Import;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.extend.LayerProvider;
import mindustry.type.Category;
import mindustry.ui.Styles;
import mindustry.world.Block;
import mindustry.world.meta.BuildVisibility;
import st.ST;
import st.addon.content.SContent;
import st.addon.content.entity.STooltipBuilder;
import st.addon.content.provider.TooltipProvider;
import st.addon.content.provider.ValueProvider;
import st.addon.unit.entity.SUnitType;

@Provider
public class UnitPreset extends LayerProvider {
	@Import(cls = SContent.class)
	@Require(cls = ValueProvider.class)
	ValueProvider value;
	@Import(cls = SContent.class)
	@Require(cls = TooltipProvider.class)
	TooltipProvider tooltip;
	
	public STooltipBuilder inject(SUnitType unit) {
		//value.armor(unit);
		//value.health(unit);
		var ts = tooltip.tooltip(unit.stats);
		ts.show("life", unit.stages.size() + 1);
		ts.show("stage", tb -> {
			tb.row();
			for (var i : unit.stages) {
				var t = new Table() {{
					background(Styles.grayPanel);
				}};
				t.labelWrap(ST.stage(i.name));
				t.row();
				//生命
				if (i.health > 0) {
					t.labelWrap(ST.stat("health") + ": " + i.health);
					t.row();
				}
				if (i.armor > 0) {
					t.labelWrap(ST.stat("armor") + ": " + i.armor);
					t.row();
				}
				if (i.speed>0) {
					t.labelWrap(ST.stat("speed") + ": " + i.speed);
					t.row();
				}
				//获得效果
				t.labelWrap(ST.stat("effect"));
				t.row();
				for (var j : i.effect) {
					t.image(j.effect.uiIcon).size(16);
					t.labelWrap("[orange]" + j.effect.localizedName + "[white]" + "~" + (j.time / 60) + ST.stat("second"));
					t.row();
				}
				tb.add(t).left().expandX().margin(5);
				tb.row();
			}
		});
		//生命阶段
		return ts;
	}
	
	public STooltipBuilder inject(Block b) {
		value.health(b);
		b.category = Category.units;
		b.buildVisibility = BuildVisibility.shown;
		return tooltip.tooltip(b.stats);
	}
	
	public STooltipBuilder inject(Block b, int level) {
		return inject(b).techLevel(level);
	}
	
	public STooltipBuilder inject(SUnitType unit, int level) {
		return inject(unit).techLevel(level);
	}
}
