package dev.aiclient.hack.combat;

import dev.aiclient.hack.Hack;
import dev.aiclient.setting.BoolSetting;
import dev.aiclient.setting.NumSetting;
import dev.aiclient.util.TargetUtil;
import net.minecraft.world.entity.LivingEntity;
import org.lwjgl.glfw.GLFW;

public class KillAura extends Hack {
	private final NumSetting range = num("Range", 4.2, 2.0, 6.0, 0.1);
	private final BoolSetting playersOnly = bool("Players Only", false);
	private final BoolSetting rotate = bool("Rotate", true);
	private final BoolSetting cooldown = bool("Wait Cooldown", true);

	public KillAura() {
		super("KillAura", Category.COMBAT, "Automatically attacks the nearest entity in range.", GLFW.GLFW_KEY_R);
	}

	@Override
	public void onTick() {
		LivingEntity target = TargetUtil.nearest(range.value, playersOnly.value);
		if (target == null) return;

		if (rotate.value) {
			float[] rot = TargetUtil.rotationsTo(target);
			mc.player.setYRot(rot[0]);
			mc.player.setXRot(rot[1]);
		}

		if (!cooldown.value || TargetUtil.cooldownReady()) {
			TargetUtil.attack(target);
		}
	}
}
