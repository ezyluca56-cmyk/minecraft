package dev.aiclient.hack.render;

import dev.aiclient.hack.Hack;
import dev.aiclient.setting.BoolSetting;
import dev.aiclient.setting.NumSetting;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

/** Outlines entities through walls using the vanilla glowing effect (client-side only). */
public class ESP extends Hack {
	private final NumSetting range = num("Range", 64.0, 8.0, 128.0, 8.0);
	private final BoolSetting playersOnly = bool("Players Only", false);

	public ESP() {
		super("ESP", Category.RENDER, "See entities through walls.");
	}

	@Override
	public void onTick() {
		for (Entity e : mc.level.entitiesForRendering()) {
			if (e == mc.player || !(e instanceof LivingEntity)) continue;
			boolean show = mc.player.distanceTo(e) <= range.value && (!playersOnly.value || e instanceof Player);
			e.setGlowingTag(show);
		}
	}

	@Override
	protected void onDisable() {
		if (mc.level == null) return;
		for (Entity e : mc.level.entitiesForRendering()) {
			e.setGlowingTag(false);
		}
	}
}
