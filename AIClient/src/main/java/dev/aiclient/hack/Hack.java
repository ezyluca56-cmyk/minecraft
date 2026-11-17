package dev.aiclient.hack;

import dev.aiclient.AIClient;
import dev.aiclient.setting.BoolSetting;
import dev.aiclient.setting.NumSetting;
import dev.aiclient.setting.Setting;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/** Base class for every module ("hack") in the client. */
public abstract class Hack {
	public enum Category {
		COMBAT("Combat"),
		MOVEMENT("Movement"),
		PLAYER("Player"),
		RENDER("Render"),
		WORLD("World");

		public final String label;

		Category(String label) {
			this.label = label;
		}
	}

	protected static final Minecraft mc = Minecraft.getInstance();

	public final String name;
	public final String description;
	public final Category category;
	public final int defaultKey;
	public final List<Setting> settings = new ArrayList<>();

	private boolean enabled;

	protected Hack(String name, Category category, String description) {
		this(name, category, description, GLFW.GLFW_KEY_UNKNOWN);
	}

	protected Hack(String name, Category category, String description, int defaultKey) {
		this.name = name;
		this.category = category;
		this.description = description;
		this.defaultKey = defaultKey;
	}

	public final boolean isEnabled() {
		return enabled;
	}

	public final void toggle() {
		setEnabled(!enabled);
	}

	public final void setEnabled(boolean value) {
		if (value == enabled) return;
		enabled = value;
		try {
			if (value) onEnable();
			else onDisable();
		} catch (Throwable t) {
			AIClient.LOGGER.error("Error toggling {}", name, t);
		}
	}

	protected void onEnable() {}

	protected void onDisable() {}

	/** Called every client tick while enabled, and only when a world + player exist. */
	public void onTick() {}

	protected BoolSetting bool(String name, boolean def) {
		BoolSetting s = new BoolSetting(name, def);
		settings.add(s);
		return s;
	}

	protected NumSetting num(String name, double def, double min, double max, double step) {
		NumSetting s = new NumSetting(name, def, min, max, step);
		settings.add(s);
		return s;
	}

	public String translationKey() {
		return "key.aiclient." + name.toLowerCase(Locale.ROOT);
	}
}
