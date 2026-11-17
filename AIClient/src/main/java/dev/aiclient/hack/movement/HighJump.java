package dev.aiclient.hack.movement;

import dev.aiclient.hack.Hack;
import dev.aiclient.setting.NumSetting;
import dev.aiclient.util.Attr;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class HighJump extends Hack {
	private static final Identifier ID = Attr.id("highjump");

	private final NumSetting boost = num("Boost", 0.4, 0.1, 2.0, 0.1);

	public HighJump() {
		super("HighJump", Category.MOVEMENT, "Increases your jump strength.");
	}

	@Override
	public void onTick() {
		Attr.apply(mc.player, Attributes.JUMP_STRENGTH, ID, boost.value);
	}

	@Override
	protected void onDisable() {
		Attr.remove(mc.player, Attributes.JUMP_STRENGTH, ID);
	}
}
