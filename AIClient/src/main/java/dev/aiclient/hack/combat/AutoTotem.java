package dev.aiclient.hack.combat;

import dev.aiclient.hack.Hack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.world.item.Items;

/** Keeps a Totem of Undying in your offhand (totem must be in the hotbar). */
public class AutoTotem extends Hack {
	private int stage;
	private int oldSlot;
	private int totemSlot;

	public AutoTotem() {
		super("AutoTotem", Category.COMBAT, "Moves a hotbar totem into your offhand automatically.");
	}

	@Override
	protected void onEnable() {
		stage = 0;
	}

	@Override
	public void onTick() {
		if (mc.getConnection() == null) return;

		switch (stage) {
			case 0 -> {
				if (mc.player.getOffhandItem().is(Items.TOTEM_OF_UNDYING)) return;
				for (int i = 0; i < 9; i++) {
					if (mc.player.getInventory().getItem(i).is(Items.TOTEM_OF_UNDYING)) {
						oldSlot = mc.player.getInventory().getSelectedSlot();
						totemSlot = i;
						mc.player.getInventory().setSelectedSlot(i);
						stage = 1; // wait a tick so the server learns the new selected slot
						return;
					}
				}
			}
			case 1 -> {
				mc.getConnection().send(new ServerboundPlayerActionPacket(
						ServerboundPlayerActionPacket.Action.SWAP_ITEMS_WITH_OFFHAND, BlockPos.ZERO, Direction.DOWN));
				stage = 2;
			}
			default -> {
				mc.player.getInventory().setSelectedSlot(oldSlot);
				stage = 0;
			}
		}
	}
}
