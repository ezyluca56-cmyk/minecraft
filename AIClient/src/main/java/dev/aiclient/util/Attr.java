package dev.aiclient.util;

import dev.aiclient.AIClient;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

/** Client-side attribute modifier helpers (used by Reach, Step, HighJump). */
public final class Attr {
	private Attr() {}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(AIClient.MOD_ID, path);
	}

	public static void apply(Player player, Holder<Attribute> attribute, Identifier id, double amount) {
		AttributeInstance inst = player.getAttribute(attribute);
		if (inst == null) return;
		AttributeModifier current = inst.getModifier(id);
		if (current == null || current.amount() != amount) {
			inst.addOrUpdateTransientModifier(new AttributeModifier(id, amount, AttributeModifier.Operation.ADD_VALUE));
		}
	}

	public static void remove(Player player, Holder<Attribute> attribute, Identifier id) {
		if (player == null) return;
		AttributeInstance inst = player.getAttribute(attribute);
		if (inst != null) inst.removeModifier(id);
	}
}
