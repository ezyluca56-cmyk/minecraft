package dev.aiclient.hack.movement;

import dev.aiclient.hack.Hack;
import dev.aiclient.setting.NumSetting;
import net.minecraft.world.phys.Vec3;

public class Glide extends Hack {
	private final NumSetting fallSpeed = num("Fall Speed", 0.05, 0.01, 0.3, 0.01);

	public Glide() {
		super("Glide", Category.MOVEMENT, "Slows your fall to a gentle glide.");
	}

	@Override
	public void onTick() {
		if (mc.player.onGround() || mc.player.getAbilities().flying) return;
		Vec3 v = mc.player.getDeltaMovement();
		if (v.y < -fallSpeed.value) {
			mc.player.setDeltaMovement(v.x, -fallSpeed.value, v.z);
		}
	}
}
