package dev.aiclient.hack.movement;

import dev.aiclient.hack.Hack;
import net.minecraft.world.phys.Vec3;

public class Jesus extends Hack {
	public Jesus() {
		super("Jesus", Category.MOVEMENT, "Walk on water and lava (hold sneak to sink).");
	}

	@Override
	public void onTick() {
		if (mc.player.isShiftKeyDown()) return;
		if (mc.player.isInWater() || mc.player.isInLava()) {
			Vec3 v = mc.player.getDeltaMovement();
			mc.player.setDeltaMovement(v.x, 0.11, v.z);
		}
	}
}
