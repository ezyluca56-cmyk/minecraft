package dev.aiclient.hack.movement;

import dev.aiclient.hack.Hack;
import dev.aiclient.setting.NumSetting;
import org.lwjgl.glfw.GLFW;

public class Flight extends Hack {
	private final NumSetting speed = num("Speed", 2.0, 0.5, 10.0, 0.5);

	public Flight() {
		super("Flight", Category.MOVEMENT, "Creative-style flying in survival.", GLFW.GLFW_KEY_G);
	}

	@Override
	public void onTick() {
		var abilities = mc.player.getAbilities();
		abilities.mayfly = true;
		abilities.flying = true;
		abilities.setFlyingSpeed(0.05f * speed.f());
	}

	@Override
	protected void onDisable() {
		if (mc.player == null) return;
		var abilities = mc.player.getAbilities();
		abilities.setFlyingSpeed(0.05f);
		if (!mc.player.isCreative() && !mc.player.isSpectator()) {
			abilities.flying = false;
			abilities.mayfly = false;
		}
	}
}
