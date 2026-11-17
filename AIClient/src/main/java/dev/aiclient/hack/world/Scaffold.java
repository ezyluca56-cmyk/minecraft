package dev.aiclient.hack.world;

import dev.aiclient.hack.Hack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

/** Places a block from your hotbar under your feet while you walk. */
public class Scaffold extends Hack {
	public Scaffold() {
		super("Scaffold", Category.WORLD, "Automatically places blocks under you.");
	}

	@Override
	public void onTick() {
		if (mc.gameMode == null || mc.screen != null) return;

		BlockPos below = mc.player.blockPosition().below();
		if (!mc.level.getBlockState(below).canBeReplaced()) return;

		int slot = -1;
		for (int i = 0; i < 9; i++) {
			if (mc.player.getInventory().getItem(i).getItem() instanceof BlockItem) {
				slot = i;
				break;
			}
		}
		if (slot == -1) return;

		for (Direction dir : Direction.values()) {
			BlockPos neighbor = below.relative(dir);
			if (mc.level.getBlockState(neighbor).canBeReplaced()) continue;

			Direction face = dir.getOpposite();
			Vec3 hit = Vec3.atCenterOf(neighbor).add(face.getStepX() * 0.5, face.getStepY() * 0.5, face.getStepZ() * 0.5);

			int old = mc.player.getInventory().getSelectedSlot();
			mc.player.getInventory().setSelectedSlot(slot);
			mc.gameMode.useItemOn(mc.player, InteractionHand.MAIN_HAND, new BlockHitResult(hit, face, neighbor, false));
			mc.player.swing(InteractionHand.MAIN_HAND);
			mc.player.getInventory().setSelectedSlot(old);
			return;
		}
	}
}
