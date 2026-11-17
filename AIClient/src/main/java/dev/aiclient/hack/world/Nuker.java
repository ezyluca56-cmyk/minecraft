package dev.aiclient.hack.world;

import dev.aiclient.hack.Hack;
import dev.aiclient.setting.NumSetting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.state.BlockState;

/** Breaks blocks around you. */
public class Nuker extends Hack {
	private final NumSetting radius = num("Radius", 4.0, 1.0, 6.0, 1.0);
	private final NumSetting perTick = num("Blocks / Tick", 4.0, 1.0, 20.0, 1.0);

	public Nuker() {
		super("Nuker", Category.WORLD, "Breaks every block in a radius around you.");
	}

	@Override
	public void onTick() {
		if (mc.gameMode == null || mc.screen != null) return;

		int r = radius.i();
		BlockPos center = mc.player.blockPosition();
		int broken = 0;

		for (BlockPos pos : BlockPos.betweenClosed(center.offset(-r, -r, -r), center.offset(r, r, r))) {
			if (broken >= perTick.i()) return;
			if (pos.distSqr(center) > (double) r * r) continue;

			BlockState state = mc.level.getBlockState(pos);
			if (state.isAir() || state.getDestroySpeed(mc.level, pos) < 0) continue;

			if (mc.gameMode.startDestroyBlock(pos.immutable(), Direction.UP)) {
				mc.player.swing(InteractionHand.MAIN_HAND);
				broken++;
			}
		}
	}
}
