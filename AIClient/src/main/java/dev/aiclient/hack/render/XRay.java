package dev.aiclient.hack.render;

import dev.aiclient.hack.Hack;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.state.BlockState;
import org.lwjgl.glfw.GLFW;

/** Shows only ores and valuables. The actual filtering happens in BlockMixin. */
public class XRay extends Hack {
	public static volatile boolean ACTIVE = false;

	public XRay() {
		super("XRay", Category.RENDER, "Hides everything except ores and valuable blocks.", GLFW.GLFW_KEY_X);
	}

	public static boolean isVisible(BlockState state) {
		String path = BuiltInRegistries.BLOCK.getKey(state.getBlock()).getPath();
		return path.endsWith("_ore")
				|| path.equals("ancient_debris")
				|| path.equals("chest")
				|| path.equals("trapped_chest")
				|| path.equals("ender_chest")
				|| path.equals("spawner")
				|| path.equals("lava")
				|| path.equals("water")
				|| path.endsWith("_portal")
				|| path.equals("obsidian");
	}

	@Override
	protected void onEnable() {
		ACTIVE = true;
		reload();
	}

	@Override
	protected void onDisable() {
		ACTIVE = false;
		reload();
	}

	private void reload() {
		if (mc.levelRenderer != null) mc.levelRenderer.allChanged();
	}
}
