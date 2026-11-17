package dev.aiclient.hack.movement;

import dev.aiclient.hack.Hack;
import net.minecraft.world.phys.Vec3;

public class Spider extends Hack {
	public Spider() {
		super("Spider", Category.MOVEMENT, "Climb walls by walking into them.");
	}

	@Override
	public void onTick() {
		if (mc.player.horizontalCollision) {
			Vec3 v = mc.player.getDeltaMovement();
			mc.player.setDeltaMovement(v.x, 0.2, v.z);
		}
	}
}
