package dev.aiclient.hack.movement;

import dev.aiclient.hack.Hack;
import dev.aiclient.setting.NumSetting;
import dev.aiclient.util.Attr;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class Step extends Hack {
	private static final Identifier ID = Attr.id("step");

	private final NumSetting height = num("Height", 2.0, 1.0, 10.0, 0.5);

	public Step() {
		super("Step", Category.MOVEMENT, "Walk up blocks without jumping.");
	}

	@Override
	public void onTick() {
		// Vanilla step height is 0.6, so add the difference.
		Attr.apply(mc.player, Attributes.STEP_HEIGHT, ID, height.value - 0.6);
	}

	@Override
	protected void onDisable() {
		Attr.remove(mc.player, Attributes.STEP_HEIGHT, ID);
	}
}
