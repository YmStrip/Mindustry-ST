package st.addon.unit.entity;

import arc.Core;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.entities.units.StatusEntry;
import mindustry.gen.BlockUnitUnit;
import mindustry.type.StatusEffect;
import mindustry.type.Weapon;
import st.ST;

import java.nio.file.Paths;
import java.util.ArrayList;

public class SUnitStage {
	public SUnitStage () {}
	public SUnitStage (String name) {
		this.name = name;
	}
	//生命阶段构造器,每个boss的生命阶段,对应不同的特性
	//修正血条
	public float health = -1;
	public float speed = -1;
	public String name = "stage";
	
	//修正护甲
	public float armor = -1;
	
	//获得效果
	public ArrayList<StatusEntry> effect = new ArrayList<>();
	public SUnitStage effect (StatusEntry statusEntry) {
		effect.add(statusEntry);
		return this;
	}
	
	//获得添加武器
	public ArrayList<Weapon> addWeapons = new ArrayList<>();
	
	public SUnitStage weapon(Weapon weapon) {
		addWeapons.add(weapon);
		return this;
	}
	
	public ArrayList<Weapon> weapons;
	
}
