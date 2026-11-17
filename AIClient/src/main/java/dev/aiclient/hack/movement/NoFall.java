package dev.aiclient.hack.movement;

import dev.aiclient.hack.Hack;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;

public class NoFall extends Hack {
	public NoFall() {
		super("NoFall", Category.MOVEMENT, "Tells the server you're on the ground so you take no fall damage.");
	}

	@Override
	public void onTick() {
		if (mc.getConnection() == null) return;
		if (mc.player.fallDistance > 2.0 && !mc.player.getAbilities().flying) {
			mc.getConnection().send(new ServerboundMovePlayerPacket.StatusOnly(true, mc.player.horizontalCollision));
		}
	}
}
