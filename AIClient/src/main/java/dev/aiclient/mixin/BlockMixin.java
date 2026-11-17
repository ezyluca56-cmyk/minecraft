package dev.aiclient.mixin;

import dev.aiclient.hack.render.XRay;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Powers XRay: while XRay is on, only "interesting" blocks get their faces rendered.
 * require = 0 means the game still starts if Mojang changes this method; XRay just won't do anything then.
 */
@Mixin(Block.class)
public class BlockMixin {
	@Inject(method = "shouldRenderFace", at = @At("HEAD"), cancellable = true, require = 0)
	private static void aiclient$xray(BlockState state, BlockState neighbor, Direction direction,
									  CallbackInfoReturnable<Boolean> cir) {
		if (XRay.ACTIVE) {
			cir.setReturnValue(XRay.isVisible(state));
		}
	}
}
