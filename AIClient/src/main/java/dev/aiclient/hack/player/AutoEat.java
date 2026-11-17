package dev.aiclient.hack.player;

import dev.aiclient.hack.Hack;
import dev.aiclient.setting.NumSetting;
import net.minecraft.core.component.DataComponents;

/** Eats food from your hotbar when your hunger drops below the threshold. */
public class AutoEat extends Hack {
	private final NumSetting threshold = num("Eat Below", 14.0, 1.0, 19.0, 1.0);

	private boolean eating;
	private int oldSlot;
	private int ticks;

	public AutoEat() {
		super("AutoEat", Category.PLAYER, "Automatically eats when you're hungry.");
	}

	@Override
	protected void onDisable() {
		stopEating();
	}

	@Override
	public void onTick() {
		if (mc.screen != null) return;

		if (eating) {
			ticks++;
			boolean done = !mc.player.getMainHandItem().has(DataComponents.FOOD)
					|| mc.player.getFoodData().getFoodLevel() >= 20
					|| ticks > 80;
			if (done) stopEating();
			else mc.options.keyUse.setDown(true);
			return;
		}

		if (mc.player.getFoodData().getFoodLevel() > threshold.value) return;

		for (int i = 0; i < 9; i++) {
			if (mc.player.getInventory().getItem(i).has(DataComponents.FOOD)) {
				oldSlot = mc.player.getInventory().getSelectedSlot();
				mc.player.getInventory().setSelectedSlot(i);
				mc.options.keyUse.setDown(true);
				eating = true;
				ticks = 0;
				return;
			}
		}
	}

	private void stopEating() {
		if (!eating) return;
		mc.options.keyUse.setDown(false);
		if (mc.player != null) mc.player.getInventory().setSelectedSlot(oldSlot);
		eating = false;
	}
}
