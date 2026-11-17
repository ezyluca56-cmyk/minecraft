package dev.aiclient.hack.movement;

import dev.aiclient.hack.Hack;

public class AirJump extends Hack {
	private int delay;

	public AirJump() {
		super("AirJump", Category.MOVEMENT, "Jump again while in mid-air.");
	}

	@Override
	public void onTick() {
		if (delay > 0) delay--;
		if (mc.screen != null) return;
		if (mc.options.keyJump.isDown() && !mc.player.onGround() && delay == 0) {
			mc.player.jumpFromGround();
			delay = 6;
		}
	}
}
