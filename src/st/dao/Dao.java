package st.dao;

import layer.annotations.LayerClass;
import layer.extend.Layer;
import mindustry.Vars;
import mindustry.type.Sector;

@LayerClass
public class Dao<T extends Object> extends Layer {
	
	public void set(String name, T d) {
	}
	
	public void set(Sector sector, T d) {
	}
	
	public T get(String name) {
		return null;
	}
	
	public T get(Sector sector) {
		return null;
	}
	
	public void del(String name) {
	}
	public void del(Sector sector) {
	
	}
	
	public int size() {
		return 0;
	}
	
	public void clear() {
	}
	
	public void save() {
	}
	
	public void save(String name) {
	}
	public void save(Sector sector) {
	
	}
}
