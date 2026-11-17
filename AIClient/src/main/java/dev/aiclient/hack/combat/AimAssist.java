package dev.aiclient.hack.combat;

import dev.aiclient.hack.Hack;
import dev.aiclient.setting.BoolSetting;
import dev.aiclient.setting.NumSetting;
import dev.aiclient.util.TargetUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public class AimAssist extends Hack {
	private final NumSetting range = num("Range", 5.0, 2.0, 8.0, 0.5);
	private final NumSetting speed = num("Speed", 6.0, 1.0, 30.0, 1.0);
	private final NumSetting fov = num("FOV", 90.0, 10.0, 180.0, 10.0);
	private final BoolSetting playersOnly = bool("Players Only", true);

	public AimAssist() {
		super("AimAssist", Category.COMBAT, "Smoothly pulls your aim toward nearby targets.");
	}

	@Override
	public void onTick() {
		if (mc.screen != null) return;
		LivingEntity target = TargetUtil.nearest(range.value, playersOnly.value);
		if (target == null) return;

		float[] rot = TargetUtil.rotationsTo(target);
		float yawDiff = Mth.wrapDegrees(rot[0] - mc.player.getYRot());
		float pitchDiff = rot[1] - mc.player.getXRot();
		if (Math.abs(yawDiff) > fov.value / 2.0) return;

		float s = speed.f();
		mc.player.setYRot(mc.player.getYRot() + Mth.clamp(yawDiff, -s, s));
		mc.player.setXRot(Mth.clamp(mc.player.getXRot() + Mth.clamp(pitchDiff, -s, s), -90.0f, 90.0f));
	}
}
