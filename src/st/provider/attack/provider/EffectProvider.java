package st.provider.attack.provider;

import arc.graphics.Color;
import arc.graphics.g2d.Fill;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import layer.annotations.Provider;
import layer.extend.LayerProvider;
import mindustry.content.Fx;
import mindustry.entities.Effect;
import mindustry.entities.UnitSorts;
import mindustry.entities.effect.WaveEffect;
import mindustry.gen.Sounds;
import mindustry.graphics.Drawf;
import mindustry.world.blocks.defense.turrets.ItemTurret;
import mindustry.world.blocks.defense.turrets.PowerTurret;
import mindustry.world.blocks.defense.turrets.Turret;

import static arc.graphics.g2d.Draw.alpha;
import static arc.graphics.g2d.Draw.color;
import static arc.graphics.g2d.Lines.stroke;
import static arc.math.Angles.randLenVectors;

@Provider
public class EffectProvider extends LayerProvider {
	
	public Color 白色 = Color.rgb(200, 210, 219);
	public Color 黄色 = Color.rgb(255, 255, 0);
	public Color 深黄色 = Color.rgb(209, 192, 0);
	
	public Color 青色 = Color.rgb(0, 255, 255);
	public Color 深青色 = Color.rgb(0, 219, 219);
	public Color 亮青色 = Color.rgb(161, 219, 219);
	public Color 绿色 = Color.rgb(39, 255, 136);
	public Color 深绿色 = Color.rgb(31, 201, 107);
	public Color 蓝色 = Color.rgb(10, 229, 255);
	public Color 深蓝色 = Color.rgb(8, 173, 193);
	public Color 红色 = Color.rgb(255, 8, 8);
	public Color 深红色 = Color.rgb(209, 4, 7);
	public Color 紫色 = Color.rgb(253, 7, 250);
	public Color 深紫色 = Color.rgb(184, 5, 182);
	
	public void inject_轨道炮(Turret t) {
		t.shootSound = Sounds.railgun;
		t.unitSort = UnitSorts.strongest;
		t.recoil = 5f;
		t.shake = 4f;
		t.ammoUseEffect = Fx.casing3Double;
	}
	
	public Effect 细轨道(Color 亮色) {
		return new Effect(16f, e -> {
			color(Color.white);
			stroke(4);
			for (int i : Mathf.signs) {
				Drawf.tri(e.x, e.y, 4f * e.fout(), 24f, e.rotation + 90 + 90f * i);
			}
			Drawf.light(e.x, e.y, 60f * e.fout(), 亮色, 0.5f);
		});
	}
	
	public Effect 轨道(Color 亮色, Color 暗色) {
		return 轨道(亮色, 暗色, 15);
	}
	
	public Effect 轨道(Color 亮色, Color 暗色, float width) {
		return new Effect(30, e -> {
			for (int i = 0; i < 2; i++) {
				color(i == 0 ? 白色 : 亮色);
				float m = i == 0 ? 1f : 0.5f;
				
				float rot = e.rotation + 180f;
				float w = width * e.fout() * m;
				Drawf.tri(e.x, e.y, w, (30f + Mathf.randomSeedRange(e.id, width)) * m, rot);
				Drawf.tri(e.x, e.y, w, width * 0.666f * m, rot + 180f);
			}
			
			Drawf.light(e.x, e.y, 60f, 暗色, 0.6f * e.fout());
		});
	}

	public Effect 发射(Color 亮色, Color 暗色,float width) {
		return new Effect(24f, e -> {
			e.scaled(10f, b -> {
				color(亮色, 暗色, b.fin());
				stroke(b.fout() * 3f + 0.2f);
				Lines.circle(b.x, b.y, b.fin() * width*2);
			});
			
			color(亮色);
			
			for (int i : Mathf.signs) {
				Drawf.tri(e.x, e.y, 13f * e.fout(), width*3, e.rotation + 90f * i);
				Drawf.tri(e.x, e.y, width/2 * e.fout(), width*2, e.rotation + 20f * i);
			}
			
			Drawf.light(e.x, e.y, 180f, 暗色, 0.9f * e.fout());
		});
	}
	public Effect 发射(Color 亮色, Color 暗色) {
		return 发射(亮色,暗色,23);
	}
	public Effect 打击烟雾(Color 亮色, Color 暗色) {
		return new Effect(70, e -> {
			randLenVectors(e.id, e.fin(), 30, 30f, (x, y, fin, fout) -> {
				color(亮色);
				alpha((0.5f - Math.abs(fin - 0.5f)) * 2f);
				Fill.circle(e.x + x, e.y + y, 0.5f + fout * 4f);
			});
		});
	}
	public Effect 打击(Color 亮色, Color 暗色){
		return 打击(亮色,暗色,23);
	}
	public Effect 打击(Color 亮色, Color 暗色,float width) {
		return new Effect(20f, 200f, e -> {
			color(暗色);
			for (int i = 0; i < 2; i++) {
				color(i == 0 ? 亮色 : 白色);
				
				float m = i == 0 ? 1f : 0.5f;
				
				for (int j = 0; j < 5; j++) {
					float rot = e.rotation + Mathf.randomSeedRange(e.id + j, width*2);
					float w = width * e.fout() * m;
					Drawf.tri(e.x, e.y, w, (width*3 + Mathf.randomSeedRange(e.id + j, width*2)) * m, rot);
					Drawf.tri(e.x, e.y, w, width*2 * m, rot + 180f);
				}
			}
			
			e.scaled(10f, c -> {
				color(亮色);
				stroke(c.fout() * 2f + 0.2f);
				Lines.circle(e.x, e.y, c.fin() * 30f);
			});
			
			e.scaled(12f, c -> {
				color(暗色);
				randLenVectors(e.id, 25, 5f + e.fin() * 80f, e.rotation, 60f, (x, y) -> {
					Fill.square(e.x + x, e.y + y, c.fout() * 3f, 45f);
				});
			});
		});
	}
	
	public Effect 消失(Color 亮色, Color 暗色) {
		return new Effect(45f, 100f, e -> {
			color(暗色);
			stroke(e.fout() * 4f);
			Lines.circle(e.x, e.y, 4f + e.finpow() * 20f);
			
			for (int i = 0; i < 4; i++) {
				Drawf.tri(e.x, e.y, 6f, 80f * e.fout(), i * 90 + 45);
			}
			
			color();
			for (int i = 0; i < 4; i++) {
				Drawf.tri(e.x, e.y, 3f, 30f * e.fout(), i * 90 + 45);
			}
			
			Drawf.light(e.x, e.y, 150f, 暗色, 0.9f * e.fout());
		});
	}
	
	public Effect 爆炸(Color 颜色, float 范围) {
		return new WaveEffect() {{
			lightOpacity = 0.5f;
			lifetime = 30;
			sizeFrom = 2f;
			sizeTo = 范围;
			strokeFrom = 2f;
			strokeTo = 范围 / 10;
			colorFrom = Color.white;
			colorTo = 颜色;
		}};
	}
	
	public Effect 轨道_蓝色 = 轨道(青色, 深青色);
	public Effect 发射_蓝色 = 发射(青色, 深青色);
	public Effect 打击_蓝色 = 打击(青色, 深青色);
	public Effect 消失_蓝色 = 消失(青色, 深青色);
	
	public interface callItem {
		void call(ItemTurret itemTurret);
	}
	
	public interface callPower {
		void call(PowerTurret powerTurret);
	}
	
	
}
