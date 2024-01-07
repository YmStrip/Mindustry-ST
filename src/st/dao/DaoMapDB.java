package st.dao;

import layer.annotations.Config;
import layer.annotations.LayerClass;
import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;

import java.util.HashMap;

@LayerClass
public class DaoMapDB<T> extends Dao<T> {
	public static DB defineDB(String name) {
		return DBMaker.fileDB(DaoValue.root + "/" + name).checksumHeaderBypass().make();
	}
	
	@Config
	public String name;
	@Config
	public DB db;
	public HTreeMap HTree;
	
	@Override
	public void setup() {
		HTree = db.hashMap(name + "_json").keySerializer(Serializer.STRING).createOrOpen();
	}
	//implement
	
	@Override
	public T get(String name) {
		return (T) HTree.get(name);
	}
	
	@Override
	public void set(String name, T d) {
		HTree.put(name, d);
	}
	
	@Override
	public void del(String name) {
		HTree.remove(name);
	}
	
	@Override
	public int size() {
		return HTree.size();
	}
	
	@Override
	public void clear() {
		HTree.clear();
	}
	
	@Override
	public void save() {
		db.commit();
	}
	
	@Override
	public void save(String name) {
		set(name, get(name));
	}
}
