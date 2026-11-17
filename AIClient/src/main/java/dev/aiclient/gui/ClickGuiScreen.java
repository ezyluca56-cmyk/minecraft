package dev.aiclient.gui;

import dev.aiclient.hack.Hack;
import dev.aiclient.hack.HackManager;
import dev.aiclient.setting.BoolSetting;
import dev.aiclient.setting.NumSetting;
import dev.aiclient.setting.Setting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Simple ClickGUI.
 *  - Left click a module: toggle it
 *  - Right click a module: expand / collapse its settings
 *  - Boolean setting: left click toggles
 *  - Number setting: left click = +step, right click = -step
 *  - Right Shift or Esc closes
 */
public class ClickGuiScreen extends Screen {
	private static final int PANEL_W = 118;
	private static final int ROW_H = 15;
	private static final int GAP = 8;
	private static final int TOP = 12;

	private static final int ACCENT = 0xFF9B7BFF;
	private static final Set<Hack> EXPANDED = new HashSet<>();

	private enum Kind { HEADER, HACK, BOOL, NUM }

	private record Row(Kind kind, int x, int y, int w, int h, String label, Hack hack, Setting setting) {
		boolean contains(double mx, double my) {
			return mx >= x && mx < x + w && my >= y && my < y + h;
		}
	}

	public ClickGuiScreen() {
		super(Component.literal("AI Client"));
	}

	private List<Row> rows() {
		List<Row> rows = new ArrayList<>();
		int panel = 0;
		for (Hack.Category cat : Hack.Category.values()) {
			int x = GAP + panel * (PANEL_W + GAP);
			int y = TOP;
			rows.add(new Row(Kind.HEADER, x, y, PANEL_W, ROW_H + 2, cat.label, null, null));
			y += ROW_H + 2;
			for (Hack hack : HackManager.in(cat)) {
				rows.add(new Row(Kind.HACK, x, y, PANEL_W, ROW_H, hack.name, hack, null));
				y += ROW_H;
				if (EXPANDED.contains(hack)) {
					for (Setting s : hack.settings) {
						Kind k = s instanceof BoolSetting ? Kind.BOOL : Kind.NUM;
						rows.add(new Row(k, x, y, PANEL_W, ROW_H, s.name, hack, s));
						y += ROW_H;
					}
				}
			}
			panel++;
		}
		return rows;
	}

	@Override
	public void extractRenderState(GuiGraphicsExtractor g, int mouseX, int mouseY, float delta) {
		super.extractRenderState(g, mouseX, mouseY, delta);

		Hack hovered = null;
		for (Row r : rows()) {
			boolean hover = r.contains(mouseX, mouseY);
			int bg;
			String text = r.label();
			int color = 0xFFFFFFFF;

			switch (r.kind()) {
				case HEADER -> {
					bg = 0xFF15151F;
					g.fill(r.x(), r.y(), r.x() + r.w(), r.y() + r.h(), bg);
					g.fill(r.x(), r.y() + r.h() - 1, r.x() + r.w(), r.y() + r.h(), ACCENT);
					g.text(font, text, r.x() + 5, r.y() + 5, ACCENT, false);
					continue;
				}
				case HACK -> {
					boolean on = r.hack().isEnabled();
					bg = on ? (hover ? 0xFF6A55D6 : 0xFF5A45C6) : (hover ? 0xFF36364A : 0xFF26263A);
					if (!r.hack().settings.isEmpty()) text += EXPANDED.contains(r.hack()) ? "  [-]" : "  [+]";
					if (hover) hovered = r.hack();
				}
				case BOOL -> {
					BoolSetting s = (BoolSetting) r.setting();
					bg = hover ? 0xFF2E2E42 : 0xFF1E1E2E;
					text = "  " + s.name + ": " + (s.value ? "ON" : "OFF");
					color = s.value ? 0xFF8CFF9B : 0xFFFF8C8C;
				}
				default -> {
					NumSetting s = (NumSetting) r.setting();
					bg = hover ? 0xFF2E2E42 : 0xFF1E1E2E;
					text = String.format("  %s: %.2f", s.name, s.value);
					color = 0xFFD0D0FF;
				}
			}

			g.fill(r.x(), r.y(), r.x() + r.w(), r.y() + r.h(), bg);
			g.text(font, text, r.x() + 5, r.y() + 4, color, false);
		}

		if (hovered != null) {
			g.text(font, hovered.name + " - " + hovered.description, 8, height - 14, 0xFFFFFFFF, true);
		}
	}

	@Override
	public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
		double mx = event.x();
		double my = event.y();
		int button = event.button();

		for (Row r : rows()) {
			if (!r.contains(mx, my)) continue;
			switch (r.kind()) {
				case HACK -> {
					if (button == 0) {
						r.hack().toggle();
					} else if (button == 1 && !r.hack().settings.isEmpty()) {
						if (!EXPANDED.remove(r.hack())) EXPANDED.add(r.hack());
					}
				}
				case BOOL -> {
					if (button == 0) ((BoolSetting) r.setting()).toggle();
				}
				case NUM -> ((NumSetting) r.setting()).step(button == 0 ? 1 : -1);
				default -> {}
			}
			return true;
		}
		return super.mouseClicked(event, doubleClick);
	}

	@Override
	public boolean keyPressed(KeyEvent event) {
		if (event.key() == GLFW.GLFW_KEY_RIGHT_SHIFT) {
			onClose();
			return true;
		}
		return super.keyPressed(event);
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}
}
