package dev.aiclient.hack.render;

import dev.aiclient.hack.Hack;
import dev.aiclient.setting.NumSetting;
import org.lwjgl.glfw.GLFW;

public class Zoom extends Hack {
	private final NumSetting fov = num("FOV", 30.0, 10.0, 70.0, 5.0);

	private int oldFov = 70;

	public Zoom() {
		super("Zoom", Category.RENDER, "Zooms in by lowering your field of view.", GLFW.GLFW_KEY_C);
	}

	@Override
	protected void onEnable() {
		oldFov = mc.options.fov().get();
	}

	@Override
	public void onTick() {
		int target = Math.max(30, fov.i()); // the vanilla FOV option can't go below 30
		if (mc.options.fov().get() != target) mc.options.fov().set(target);
	}

	@Override
	protected void onDisable() {
		mc.options.fov().set(oldFov);
	}
}
