package dev.aiclient.hack.movement;

import dev.aiclient.hack.Hack;

public class AutoWalk extends Hack {
	public AutoWalk() {
		super("AutoWalk", Category.MOVEMENT, "Holds the forward key for you.");
	}

	@Override
	public void onTick() {
		mc.options.keyUp.setDown(true);
	}

	@Override
	protected void onDisable() {
		mc.options.keyUp.setDown(false);
	}
}
