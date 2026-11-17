package dev.aiclient.hack.player;

import dev.aiclient.hack.Hack;
import net.minecraft.client.gui.screens.DeathScreen;

public class AutoRespawn extends Hack {
	public AutoRespawn() {
		super("AutoRespawn", Category.PLAYER, "Respawns instantly when you die.");
	}

	@Override
	public void onTick() {
		if (mc.screen instanceof DeathScreen) {
			mc.player.respawn();
		}
	}
}
