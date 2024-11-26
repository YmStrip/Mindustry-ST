package wool.module;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import wool.root.AppModule;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class Fs extends AppModule {
    public static boolean isFile(String path) {
        return new File(path).isFile();
    }

    public static boolean isDir(String path) {
        return new File(path).isDirectory();
    }

    public static String[] getDir(String path) {
        return new File(path).list();
    }


    public static byte[] getFile(String path) {
        try {
            return Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            return new byte[0];
        }
    }

    public static void setFile(String path, byte[] content) {
        try (FileOutputStream out = new FileOutputStream(path)) {
            out.write(content);
        } catch (IOException e) {
        }
    }

    public static void setFile(String path, String content) {
        try (FileWriter writer = new FileWriter(path)) {
            writer.write(content);
        } catch (IOException e) {
        }
    }

    public static void setDir(String path) {
        new File(path).mkdirs();
    }

    public static void delFile(String path) {
        new File(path).delete();
    }

    public static void delDir(File dir) {
        if (dir.isDirectory()) {
            for (File file : dir.listFiles()) {
                if (file.isDirectory()) {
                    delDir(file);
                } else {
                    file.delete();
                }
            }
        }
        dir.delete();
    }

    public static void setJson(String fi, HashMap json) {
        setFile(fi, new JSONObject(json).toJSONString());
    }

    public static HashMap getJson(String fi) {
        try {
            var parser = new JSONParser();
            var data = new String(getFile(fi));
            var obj = (JSONObject) parser.parse(data);
            return new HashMap(obj);
        } catch (Exception e) {
            return new HashMap();
        }
    }

    public static String src(String path) {
        return path.replace("\\", "/");
    }

    public static String dirname(String path) {
        path = src(path);
        int lastSlashIndex = path.lastIndexOf("/");
        if (lastSlashIndex == -1) {
            return ".";
        }
        return path.substring(0, lastSlashIndex);
    }

    public static String filename(String path) {
        path = src(path);
        int lastSlashIndex = path.lastIndexOf("/");
        if (lastSlashIndex == -1) {
            return path;
        }
        return path.substring(lastSlashIndex + 1);
    }

    public static void copy(String src, String dest) {
        src = src(src);
        dest = src(dest);
        if (isDir(src)) {
            copyDir(src, dest);
        } else if (isFile(src)) {
            copyFile(src, dest);
        }
    }

    private static void copyDir(String src, String dest) {
        setDir(dest);
        String[] files = getDir(src);
        for (String file : files) {
            String srcFile = src + "/" + file;
            String destFile = dest + "/" + file;
            if (isDir(srcFile)) {
                copyDir(srcFile, destFile);
            } else if (isFile(srcFile)) {
                copyFile(srcFile, destFile);
            }
        }
    }

    public static void move(String src, String dest) {
        src = src(src);
        dest = src(dest);
        copy(src, dest);
        del(src);
    }

    private static void copyFile(String src, String dest) {
        var content = getFile(src);
        setFile(dest, content);
    }

    public static void del(String path) {
        path = src(path);
        if (isDir(path)) {
            delDir(path);
        } else if (isFile(path)) {
            delFile(path);
        }
    }

    private static void delDir(String path) {
        String[] files = getDir(path);
        for (String file : files) {
            String filePath = path + "/" + file;
            if (isDir(filePath)) {
                delDir(filePath);
            } else if (isFile(filePath)) {
                delFile(filePath);
            }
        }
        delFile(path);
    }
}