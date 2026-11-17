package dev.aiclient.hack.player;

import dev.aiclient.hack.Hack;
import net.minecraft.world.InteractionHand;

public class AntiAFK extends Hack {
	private int timer;

	public AntiAFK() {
		super("AntiAFK", Category.PLAYER, "Jumps, swings and turns now and then so you don't get kicked for idling.");
	}

	@Override
	public void onTick() {
		if (++timer < 200) return;
		timer = 0;
		mc.player.swing(InteractionHand.MAIN_HAND);
		mc.player.setYRot(mc.player.getYRot() + (mc.player.getRandom().nextFloat() * 30f - 15f));
		if (mc.player.onGround()) mc.player.jumpFromGround();
	}
}
