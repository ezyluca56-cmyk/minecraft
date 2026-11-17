package dev.aiclient.hack.combat;

import dev.aiclient.hack.Hack;
import dev.aiclient.setting.BoolSetting;
import dev.aiclient.setting.NumSetting;
import dev.aiclient.util.TargetUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;

/** Hold left click while looking at an entity and it attacks at a fixed CPS. */
public class AutoClicker extends Hack {
	private final NumSetting cps = num("CPS", 12.0, 1.0, 20.0, 1.0);
	private final BoolSetting respectCooldown = bool("Wait Cooldown", false);

	private double charge;

	public AutoClicker() {
		super("AutoClicker", Category.COMBAT, "Clicks for you while you hold attack on an entity.");
	}

	@Override
	protected void onEnable() {
		charge = 0;
	}

	@Override
	public void onTick() {
		if (mc.screen != null || !mc.options.keyAttack.isDown()) {
			charge = 0;
			return;
		}
		if (!(mc.hitResult instanceof EntityHitResult hit)) return;
		Entity e = hit.getEntity();
		if (!(e instanceof LivingEntity)) return;

		charge += cps.value / 20.0;
		if (charge >= 1.0) {
			charge -= 1.0;
			if (!respectCooldown.value || TargetUtil.cooldownReady()) TargetUtil.attack(e);
		}
	}
}
