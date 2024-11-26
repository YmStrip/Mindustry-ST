package wool.entity;

import wool.module.Fs;

import java.util.HashMap;


public class Register {
    public static String fi;

    public static void configureData(Object data, HashMap assign) {
        var cls = data.getClass();
        for (Object key : assign.keySet()) {
            if (!(key instanceof String)) continue;
            try {
                var fi = cls.getField((String) key);
                var reg = fi.get(data);
                if (!(reg instanceof Register)) continue;
                ((Register) reg).configure((HashMap) assign.get(key));
            } catch (Exception e) {

            }
        }
    }

    public static HashMap serializeDeep(Object data) {
        var hashMap = new HashMap();
        var fi = data.getClass().getFields();
        for (var i = 0; i < fi.length; i++) {
            var field = fi[i];
            field.setAccessible(true);
            try {
                var o = field.get(data);
                if (o instanceof Register) {
                    hashMap.put(field.getName(), serializeDeep(o));
                } else {
                    hashMap.put(field.getName(), o);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return hashMap;
    }

    public HashMap ignoreKeys = new HashMap();

    public Register setIgnoreKeys(String[] data) {
        for (int i = -1; ++i < data.length; ) {
            this.ignoreKeys.put(data[i], true);
        }
        return this;
    }

    public Register delIgnoreKeys(String[] data) {
        for (int i = -1; ++i < data.length; ) {
            this.ignoreKeys.remove(data[i]);
        }
        return this;
    }

    public Register configure(HashMap data) {
        var cls = this.getClass();
        for (Object key : data.keySet()) {
            try {
                if (ignoreKeys.containsKey(key)) continue;
                var fi = cls.getField((String) key);
                fi.setAccessible(true);
                var reg = fi.get(this);
                if (reg instanceof Register) {
                    ((Register) reg).configure((HashMap) data.get(key));
                } else {
                    //over
                    fi.set(this, data.get(key));
                }
            } catch (Exception e) {

            }
        }
        return this;
    }

    public HashMap serialize() {
        var data = new HashMap();
        var cls = this.getClass();
        var fis = cls.getFields();
        for (var i = 0; i < fis.length; i++) {
            try {
                var field = fis[i];
                field.setAccessible(true);
                var o = field.get(this);
                if (o instanceof Register) {
                    data.put(field.getName(), ((Register) o).serialize());
                } else {
                    data.put(field.getName(), o);
                }
            } catch (Exception e) {
            }
        }
        return data;
    }

    public Register Set(String key, Object value) {
        try {
            var fi = this.getClass().getField(key);
            fi.set(this, value);
        } catch (Exception e) {

        }
        return this;
    }

    public Object Get(String key) {
        try {
            var fi = this.getClass().getField(key);
            return fi.get(this);
        } catch (Exception e) {
            return null;
        }
    }

    public Register write(String fi) {
        Fs.setDir(Fs.dirname(fi));
        Fs.setJson(fi, this.serialize());
        return this;
    }

    public Register read(String fi) {
        var json = Fs.getJson(fi);
        this.configure(json);
        return this;
    }
}
