package dev.aiclient.hack.combat;

import dev.aiclient.hack.Hack;
import dev.aiclient.setting.BoolSetting;
import dev.aiclient.util.TargetUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.EntityHitResult;

public class Triggerbot extends Hack {
	private final BoolSetting playersOnly = bool("Players Only", false);

	public Triggerbot() {
		super("Triggerbot", Category.COMBAT, "Attacks whatever your crosshair is on once your weapon is charged.");
	}

	@Override
	public void onTick() {
		if (mc.screen != null) return;
		if (mc.hitResult instanceof EntityHitResult hit) {
			Entity e = hit.getEntity();
			if (!(e instanceof LivingEntity)) return;
			if (playersOnly.value && !(e instanceof Player)) return;
			if (TargetUtil.cooldownReady()) TargetUtil.attack(e);
		}
	}
}
