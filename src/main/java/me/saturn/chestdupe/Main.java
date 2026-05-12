package me.saturn.chestdupe;

import net.fabricmc.api.ModInitializer;
import me.saturn.chestdupe.Config;

public class Main implements ModInitializer {
	@Override
	public void onInitialize() {
		Config.load();
		System.out.println("Chest Dupe Mod (1.16.5) initialized");
	}
}
