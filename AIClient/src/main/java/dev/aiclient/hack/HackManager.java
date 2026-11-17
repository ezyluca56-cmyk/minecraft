package dev.aiclient.hack;

import com.mojang.blaze3d.platform.InputConstants;
import dev.aiclient.AIClient;
import dev.aiclient.hack.combat.*;
import dev.aiclient.hack.movement.*;
import dev.aiclient.hack.player.*;
import dev.aiclient.hack.render.*;
import dev.aiclient.hack.world.*;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class HackManager {
	private static final List<Hack> HACKS = new ArrayList<>();
	private static final Map<Hack, KeyMapping> KEYS = new LinkedHashMap<>();

	private HackManager() {}

	public static void init() {
		// Combat
		add(new KillAura());
		add(new Triggerbot());
		add(new AimAssist());
		add(new AutoClicker());
		add(new Reach());
		add(new Velocity());
		add(new AutoTotem());
		// Movement
		add(new Flight());
		add(new Speed());
		add(new Sprint());
		add(new NoFall());
		add(new Step());
		add(new HighJump());
		add(new Jesus());
		add(new Spider());
		add(new Glide());
		add(new AirJump());
		add(new AutoWalk());
		// Player
		add(new AutoRespawn());
		add(new AntiAFK());
		add(new AutoEat());
		// Render
		add(new Fullbright());
		add(new ESP());
		add(new XRay());
		add(new Zoom());
		// World
		add(new Scaffold());
		add(new Nuker());
	}

	private static void add(Hack hack) {
		HACKS.add(hack);
	}

	public static List<Hack> all() {
		return HACKS;
	}

	public static List<Hack> in(Hack.Category category) {
		return HACKS.stream().filter(h -> h.category == category).toList();
	}

	@SuppressWarnings("unchecked")
	public static <T extends Hack> T get(Class<T> type) {
		for (Hack h : HACKS) {
			if (type.isInstance(h)) return (T) h;
		}
		return null;
	}

	public static void registerKeys(KeyMapping.Category category) {
		for (Hack hack : HACKS) {
			KeyMapping mapping = KeyMappingHelper.registerKeyMapping(
					new KeyMapping(hack.translationKey(), InputConstants.Type.KEYSYM, hack.defaultKey, category));
			KEYS.put(hack, mapping);
		}
	}

	public static void tick(Minecraft client) {
		for (Map.Entry<Hack, KeyMapping> e : KEYS.entrySet()) {
			while (e.getValue().consumeClick()) {
				if (client.screen == null) e.getKey().toggle();
			}
		}

		if (client.player == null || client.level == null) return;

		for (Hack hack : HACKS) {
			if (!hack.isEnabled()) continue;
			try {
				hack.onTick();
			} catch (Throwable t) {
				AIClient.LOGGER.error("{} crashed and was disabled", hack.name, t);
				hack.setEnabled(false);
			}
		}
	}
}
