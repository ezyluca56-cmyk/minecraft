package dev.aiclient.hack.combat;

import dev.aiclient.hack.Hack;
import dev.aiclient.setting.NumSetting;
import net.minecraft.world.phys.Vec3;

/** Anti-knockback. Approximate: reduces the velocity change on the tick you get hurt. */
public class Velocity extends Hack {
	private final NumSetting horizontal = num("Horizontal %", 0.0, 0.0, 100.0, 10.0);
	private final NumSetting vertical = num("Vertical %", 0.0, 0.0, 100.0, 10.0);

	private Vec3 previous;

	public Velocity() {
		super("Velocity", Category.COMBAT, "Reduces or cancels knockback.");
	}

	@Override
	protected void onEnable() {
		previous = null;
	}

	@Override
	public void onTick() {
		Vec3 now = mc.player.getDeltaMovement();
		if (previous != null && mc.player.hurtTime > 0 && mc.player.hurtTime == mc.player.hurtDuration) {
			double h = horizontal.value / 100.0;
			double v = vertical.value / 100.0;
			Vec3 adjusted = new Vec3(
					previous.x + (now.x - previous.x) * h,
					previous.y + (now.y - previous.y) * v,
					previous.z + (now.z - previous.z) * h);
			mc.player.setDeltaMovement(adjusted);
			now = adjusted;
		}
		previous = now;
	}
}
