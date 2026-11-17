package dev.aiclient.hack.combat;

import dev.aiclient.hack.Hack;
import dev.aiclient.setting.NumSetting;
import dev.aiclient.util.Attr;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

/** Adds to your interaction range attributes on the client. */
public class Reach extends Hack {
	private static final Identifier ENTITY_ID = Attr.id("reach_entity");
	private static final Identifier BLOCK_ID = Attr.id("reach_block");

	private final NumSetting entityReach = num("Entity Reach", 3.0, 0.0, 6.0, 0.25);
	private final NumSetting blockReach = num("Block Reach", 2.0, 0.0, 6.0, 0.25);

	public Reach() {
		super("Reach", Category.COMBAT, "Extends how far you can hit entities and break/place blocks.");
	}

	@Override
	public void onTick() {
		Attr.apply(mc.player, Attributes.ENTITY_INTERACTION_RANGE, ENTITY_ID, entityReach.value);
		Attr.apply(mc.player, Attributes.BLOCK_INTERACTION_RANGE, BLOCK_ID, blockReach.value);
	}

	@Override
	protected void onDisable() {
		Attr.remove(mc.player, Attributes.ENTITY_INTERACTION_RANGE, ENTITY_ID);
		Attr.remove(mc.player, Attributes.BLOCK_INTERACTION_RANGE, BLOCK_ID);
	}
}
