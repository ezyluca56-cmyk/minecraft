package dev.aiclient.util;

import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public final class TargetUtil {
	private TargetUtil() {}

	/** Nearest living entity (not yourself) within range, or null. */
	public static LivingEntity nearest(double range, boolean playersOnly) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.player == null || mc.level == null) return null;

		LivingEntity best = null;
		double bestDist = range;
		for (Entity e : mc.level.entitiesForRendering()) {
			if (!(e instanceof LivingEntity living)) continue;
			if (e == mc.player || e instanceof ArmorStand || !living.isAlive()) continue;
			if (playersOnly && !(e instanceof Player)) continue;
			double d = mc.player.distanceTo(e);
			if (d <= bestDist) {
				bestDist = d;
				best = living;
			}
		}
		return best;
	}

	/** Returns {yaw, pitch} looking from the player's eyes at the center of the entity. */
	public static float[] rotationsTo(Entity target) {
		Minecraft mc = Minecraft.getInstance();
		Vec3 eye = mc.player.getEyePosition();
		Vec3 to = target.getBoundingBox().getCenter();
		double dx = to.x - eye.x;
		double dy = to.y - eye.y;
		double dz = to.z - eye.z;
		double horizontal = Math.sqrt(dx * dx + dz * dz);
		float yaw = (float) (Math.toDegrees(Math.atan2(dz, dx)) - 90.0);
		float pitch = (float) -Math.toDegrees(Math.atan2(dy, horizontal));
		return new float[] { yaw, pitch };
	}

	public static void attack(Entity target) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.gameMode == null || mc.player == null) return;
		mc.gameMode.attack(mc.player, target);
		mc.player.swing(InteractionHand.MAIN_HAND);
	}

	public static boolean cooldownReady() {
		Minecraft mc = Minecraft.getInstance();
		return mc.player != null && mc.player.getAttackStrengthScale(0.5f) >= 1.0f;
	}
}
