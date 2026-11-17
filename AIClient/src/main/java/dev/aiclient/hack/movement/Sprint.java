package dev.aiclient.hack.movement;

import dev.aiclient.hack.Hack;

public class Sprint extends Hack {
	public Sprint() {
		super("Sprint", Category.MOVEMENT, "Always sprints while moving forward.");
	}

	@Override
	public void onTick() {
		if (mc.screen != null) return;
		boolean forward = mc.options.keyUp.isDown();
		if (forward && !mc.player.isShiftKeyDown() && !mc.player.horizontalCollision
				&& mc.player.getFoodData().getFoodLevel() > 6) {
			mc.player.setSprinting(true);
		}
	}
}
