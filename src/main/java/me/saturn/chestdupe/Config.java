package me.saturn.chestdupe;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {
    public static boolean showButtons = true;
    public static boolean autoClose = true;
    public static int dupeKey = 82; // R
    public static int dupeDisconnectKey = 68; // D

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "chestdupe.json");

    public static void load() {
        if (!FILE.exists()) {
            save();
            return;
        }
        try (FileReader reader = new FileReader(FILE)) {
            ConfigData data = GSON.fromJson(reader, ConfigData.class);
            if (data != null) {
                showButtons = data.showButtons;
                autoClose = data.autoClose;
                dupeKey = data.dupeKey;
                dupeDisconnectKey = data.dupeDisconnectKey;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        try (FileWriter writer = new FileWriter(FILE)) {
            GSON.toJson(new ConfigData(), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ConfigData {
        boolean showButtons = Config.showButtons;
        boolean autoClose = Config.autoClose;
        int dupeKey = Config.dupeKey;
        int dupeDisconnectKey = Config.dupeDisconnectKey;
    }
}
