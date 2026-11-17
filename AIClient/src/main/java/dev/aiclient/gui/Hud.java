package dev.aiclient.gui;

import dev.aiclient.AIClient;
import dev.aiclient.hack.Hack;
import dev.aiclient.hack.HackManager;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import java.util.Comparator;
import java.util.List;

public final class Hud {
	private Hud() {}

	public static void extract(GuiGraphicsExtractor g, DeltaTracker tracker) {
		Minecraft mc = Minecraft.getInstance();
		if (mc.options.hideGui || mc.player == null) return;

		int sw = mc.getWindow().getGuiScaledWidth();
		int sh = mc.getWindow().getGuiScaledHeight();

		// Watermark
		g.text(mc.font, AIClient.NAME + " 26.2", 4, 4, 0xFFB39DFF, true);

		// Array list of enabled modules, right aligned, longest first
		List<Hack> enabled = HackManager.all().stream()
				.filter(Hack::isEnabled)
				.sorted(Comparator.comparingInt((Hack h) -> mc.font.width(h.name)).reversed())
				.toList();

		int y = 4;
		for (Hack h : enabled) {
			int w = mc.font.width(h.name);
			int x = sw - w - 4;
			g.fill(x - 3, y - 1, sw, y + 10, 0x90000000);
			g.fill(sw - 1, y - 1, sw, y + 10, 0xFF9B7BFF);
			g.text(mc.font, h.name, x, y + 1, 0xFFFFFFFF, false);
			y += 11;
		}

		// Coordinates, bottom left
		String pos = String.format("XYZ %.1f / %.1f / %.1f", mc.player.getX(), mc.player.getY(), mc.player.getZ());
		g.text(mc.font, pos, 4, sh - 12, 0xFFFFFFFF, true);
	}
}
