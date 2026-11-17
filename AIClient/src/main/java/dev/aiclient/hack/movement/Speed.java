package dev.aiclient.hack.movement;

import dev.aiclient.hack.Hack;
import dev.aiclient.setting.BoolSetting;
import dev.aiclient.setting.NumSetting;
import net.minecraft.world.phys.Vec3;

public class Speed extends Hack {
	private final NumSetting speed = num("Speed", 0.45, 0.1, 2.0, 0.05);
	private final BoolSetting groundOnly = bool("Ground Only", false);

	public Speed() {
		super("Speed", Category.MOVEMENT, "Moves you faster horizontally.");
	}

	@Override
	public void onTick() {
		if (mc.screen != null) return;
		if (groundOnly.value && !mc.player.onGround()) return;

		float forward = (mc.options.keyUp.isDown() ? 1 : 0) - (mc.options.keyDown.isDown() ? 1 : 0);
		float strafe = (mc.options.keyLeft.isDown() ? 1 : 0) - (mc.options.keyRight.isDown() ? 1 : 0);
		if (forward == 0 && strafe == 0) return;

		double yaw = Math.toRadians(mc.player.getYRot());
		double sin = Math.sin(yaw);
		double cos = Math.cos(yaw);
		double x = -sin * forward + cos * strafe;
		double z = cos * forward + sin * strafe;
		double len = Math.sqrt(x * x + z * z);
		if (len == 0) return;

		Vec3 v = mc.player.getDeltaMovement();
		mc.player.setDeltaMovement(x / len * speed.value, v.y, z / len * speed.value);
	}
}
