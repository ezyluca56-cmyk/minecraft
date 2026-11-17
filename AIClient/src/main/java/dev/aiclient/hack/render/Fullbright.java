package dev.aiclient.hack.render;

import dev.aiclient.hack.Hack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import org.lwjgl.glfw.GLFW;

/** Applies a hidden client-side Night Vision effect. */
public class Fullbright extends Hack {
	public Fullbright() {
		super("Fullbright", Category.RENDER, "See everything as if it were daytime.", GLFW.GLFW_KEY_B);
	}

	@Override
	public void onTick() {
		MobEffectInstance current = mc.player.getEffect(MobEffects.NIGHT_VISION);
		if (current == null || current.getDuration() < 400) {
			mc.player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 1200, 0, false, false));
		}
	}

	@Override
	protected void onDisable() {
		if (mc.player != null) mc.player.removeEffect(MobEffects.NIGHT_VISION);
	}
}
