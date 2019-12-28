package com.zp4rker.bukkitutils;

import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class JsonFile extends JSONObject {

    private File file;

    private JsonFile(File file, String content) {
        super(content);
        this.file = file;
    }

    public void save() throws IOException {
        Files.write(toString(2).getBytes(), file);
    }

    public static JsonFile loadOrCreate(String fileName) throws IOException {
        return load(new File(fileName));
    }

    public static JsonFile loadWithDefaults(String fileName, String defaultsFile) throws IOException {
        File file = new File(fileName);

        if (file.exists()) return load(file);

        file.getParentFile().mkdirs();
        file.createNewFile();
        Files.write(readDefaults(defaultsFile).getBytes(), file);
        return load(file);
    }

    private static JsonFile load(File file) throws IOException {
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        if (!file.exists()) file.createNewFile();

        String content = Files.toString(file, Charset.defaultCharset());

        return content.isEmpty() ? new JsonFile(file, "{}") : new JsonFile(file, content);
    }

    private static String readDefaults(String defaultsFile) throws IOException {
        ByteSource bs = new ByteSource() {
            @Override
            public InputStream openStream() throws IOException {
                return JsonFile.class.getResourceAsStream("/" + defaultsFile);
            }
        };

        return bs.asCharSource(Charset.defaultCharset()).read();
    }

}
