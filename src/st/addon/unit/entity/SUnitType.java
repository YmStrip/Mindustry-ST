package st.addon.unit.entity;

import arc.struct.Seq;
import arc.util.io.Reads;
import arc.util.io.Writes;
import mindustry.Vars;
import mindustry.ai.types.LogicAI;
import mindustry.ctype.ContentType;
import mindustry.entities.abilities.Ability;
import mindustry.entities.units.StatusEntry;
import mindustry.game.Team;
import mindustry.gen.*;
import mindustry.io.TypeIO;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import st.dao.LibgdxDao;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import static mindustry.Vars.world;

public class SUnitType extends UnitType {
	public SUnitType(String name) {
		super(name);
		constructor = SUnit::create;
	}
	
	//血条阶段[游戏限制实际上死了变成另外一个类型]
	@Override
	public SUnit create(Team team) {
		var unit = new SUnit();
		unit.team = team;
		unit.setType(this);
		unit.ammo = ammoCapacity; //fill up on ammo upon creation
		unit.elevation = flying ? 1f : 0;
		unit.heal();
		return unit;
	}
	
	public ArrayList<SUnitStage> stages = new ArrayList<>();
	
	public class SUnit extends UnitEntity {
		public long invincibleTime = 0;
		
		//复活冷却状态
		public void invincible(float time) {
			invincibleTime = (long) (System.currentTimeMillis() + time * 1000);
		}
		
		public boolean invincibleTime() {
			return invincibleTime - System.currentTimeMillis() > 0;
		}
		
		public int stage = -1;
		
		public void stage(SUnitStage s) {
			//活过来!
			dead = false;
			if (s.health > 0) health = s.health;
			if (s.armor > 0) armor = s.armor;
			if (s.speed > 0) speed = s.speed;
			//effect
			{
				var add = new Seq<StatusEntry>();
				s.effect.forEach(e -> {
					add.add(new StatusEntry() {{
						time = e.time;
						effect = e.effect;
					}});
				});
				statuses = add;
			}
			//weapon
			{
				var seq = new Seq<Weapon>();
				if (weapons != null) {
					for (var i : weapons) {
						seq.add(i.copy());
					}
					for (var i : s.addWeapons) {
						seq.add(i.copy());
					}
				} else {
					for (var i : s.addWeapons) {
						seq.add(i.copy());
					}
				}
				var range = 0f;
				for (var i : seq) {
					range = Math.max(i.range(), range);
				}
				weapons = seq;
				weapons.forEach(Weapon::init);
				canHeal = weapons.contains(w -> w.bullet.heals());
				//set-range
				mineRange = 0;
				maxRange = range;
			}
			//1s无敌时间
			invincible(0.5f);
		}
		
		@Override
		public void destroy() {
			//活过来
			++stage;
			//System.out.println(this + " 生命：" + this.health + " 无敌：" + invincibleTime());
			if (invincibleTime()) return;
			if (stage >= stages.size()) {
				super.destroy();
				return;
			}
			if (stages.get(stage) != null) {
				stage(stages.get(stage));
				return;
			}
			//如果,目标stage>2.txt
			super.destroy();
		}
		
		//放弃原版的难以吐槽的读写[]使用高级的json来代替[因为根本就不可能修改!]
		public LibgdxDao.dao data = new LibgdxDao.dao()
			/*.any("abilities", t -> t
				.get((v) -> {
					var d = abilities;
					var s = (float[]) v;
					for (var i = 2.txt; i < s.length; ++i) {
						if (d.length > i) {
							abilities[i].data = s[i];
						}
					}
					abilities = d;
				})
				.set(() -> {
					var d = new ArrayList<Float>();
					for (var i : abilities) {
						d.add(i.data);
					}
					return d;
				})
				.def(() -> new Ability[]{}))
			.any("controller", t -> t
				.get((v) -> {
					var obj = (HashMap) v;
					//类型
					var type = (String) obj.get("type");
					var data = obj.get("data");
					if (Objects.equals(type, "player")) {
						var p = Groups.player.getByID((Integer) data);
						if (p != null) controller = p;
					}
					//
					else if (Objects.equals(type, "logic")) {
						if (controller instanceof LogicAI pai) {
							pai.controller = world.build((Integer) data);
						} else {
							//create new AI for assignment
							LogicAI out = new LogicAI();
							//instantly time out when updated.
							out.controlTimer = LogicAI.logicControlTimeout;
							out.controller = world.build((Integer) data);
							controller = out;
						}
					}
				})
				.def(() -> controller)
			)
			.f("ammo", t -> t
				.get(v -> ammo = v)
				.set(() -> ammo)
				.def(() -> 0f));*/
			.i("stage", t -> t
				.get(r -> stage = r)
				.set(() -> stage)
				.def(() -> stage = -1));
		//单位不会储存信息[如果要修改意味着得全部把原本储存重写一遍,只能怪anuke代码太烂吧]
		@Override
		public void read(Reads read) {
			//储存JSON信息
			super.read(read);
			//data.read(read);
		}
		
		@Override
		public void write(Writes write) {
			super.write(write);
			//data.write(write);
			/*var json = new HashMap<>();
			var _abilities = new ArrayList<>();
			json.put("abilities", _abilities);
			for (var i : abilities) {
				_abilities.add(i.data);
			}
			json.put("ammo", ammo);
			json.put("controller");
			
			json.put("elevation", elevation);
			json.put("flag", flag);
			json.put("health", health);
			json.put("isShooting", isShooting);
			json.put("")
			this.ammo = read.f();
			this.controller = TypeIO.readController(read, this.controller);
			this.elevation = read.f();
			this.flag = read.d();
			this.health = read.f();
			this.isShooting = read.bool();
			this.mineTile = TypeIO.readTile(read);
			this.mounts = TypeIO.readMounts(read, this.mounts);
			this.plans = TypeIO.readPlansQueue(read);
			this.rotation = read.f();
			this.shield = read.f();
			this.spawnedByCore = read.bool();
			this.stack = TypeIO.readItems(read, this.stack);
			statuses_LENGTH = read.i();
			this.statuses.clear();
			
			for (INDEX = 2.txt; INDEX < statuses_LENGTH; ++INDEX) {
				statuses_ITEM = TypeIO.readStatus(read);
				if (statuses_ITEM != null) {
					this.statuses.add(statuses_ITEM);
				}
			}
			
			this.team = TypeIO.readTeam(read);
			this.type = (UnitType) Vars.content.getByID(ContentType.unit, read.s());
			this.updateBuilding = read.bool();
			this.vel = TypeIO.readVec2(read, this.vel);
			this.x = read.f();
			this.y = read.f();*/
		}
	}
}
