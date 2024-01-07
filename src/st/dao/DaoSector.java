package st.dao;

import arc.Core;
import arc.Events;
import arc.func.Prov;
import layer.annotations.Config;
import layer.annotations.Provider;
import layer.annotations.Require;
import layer.layer.Logger;
import mindustry.Vars;
import mindustry.game.EventType;
import mindustry.type.Sector;
import st.ST;

import java.util.ArrayList;
import java.util.HashMap;

@Provider
public class DaoSector extends Dao {
	@Config
	public String name = "sector";
	@Require
	public Logger logger;
	
	public boolean isSector(String name) {
		var data = (name == null ? "" : name).split("\\.");
		try {
			var planet = Vars.content.planet(data[0]);
			var sector = planet.sectors.get(Integer.valueOf(data[1]));
			return sector != null;
		} catch (Exception e) {
			return false;
		}
	}
	
	public String name(Sector sector) {
		return sector.planet.name + "." + sector.id;
	}
	
	public String name(String n) {
		return ST.name + "-" + name + "-" + n;
	}
	
	public String nameJson(String n) {
		return ST.name + "-" + name + "-json-" + n;
	}
	
	@Override
	public <T> void set(String name, T d) {
		//是否是扇区
		if (!isSector(name)) {
			logger.err("储存失败:%s 不是有效扇区名称 %s", name, d);
			return;
		}
		Core.settings.put(name(name), d);
	}
	
	@Override
	public <T> T get(String name, Prov<T> def) {
		return (T) Core.settings.getString(name(name), (String) def.get());
	}
	
	@Override
	public <T> void del(String name) {
		Core.settings.remove(name(name));
		Core.settings.remove(nameJson(name));
	}
	
	@Override
	public <T> String[] keys() {
		//sector list
		var data = new ArrayList<String>();
		for (var i : Vars.content.planets()) {
			i.sectors.forEach(d -> {
				if (!(d.isAttacked() || d.isCaptured() || d.isBeingPlayed())) return;
				data.add(name(d));
			});
		}
		return data.toArray(new String[0]);
	}
	@Override
	public void run() {
		//GLOBAL-EVENT.INIT
		Events.on(EventType.SectorLoseEvent.class, e -> {
			del(name(e.sector));
		});
		Events.on(EventType.ContentInitEvent.class, e -> {
			//clear unlocked
			for (var i : Vars.content.planets()) {
				i.sectors.forEach(d -> {
					if ((d.isAttacked() || d.isCaptured() || d.isBeingPlayed())) return;
					del(name(d));
				});
			}
		});
	}
}
