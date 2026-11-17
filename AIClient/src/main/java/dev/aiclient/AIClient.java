package dev.aiclient;

import com.mojang.blaze3d.platform.InputConstants;
import dev.aiclient.gui.ClickGuiScreen;
import dev.aiclient.gui.Hud;
import dev.aiclient.hack.HackManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AIClient implements ClientModInitializer {
	public static final String MOD_ID = "aiclient";
	public static final String NAME = "AI Client";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	private static KeyMapping guiKey;

	@Override
	public void onInitializeClient() {
		HackManager.init();

		KeyMapping.Category category = KeyMapping.Category.register(Identifier.fromNamespaceAndPath(MOD_ID, "hacks"));

		guiKey = KeyMappingHelper.registerKeyMapping(
				new KeyMapping("key.aiclient.gui", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, category));
		HackManager.registerKeys(category);

		ClientTickEvents.END_CLIENT_TICK.register(AIClient::tick);

		HudElementRegistry.attachElementBefore(
				VanillaHudElements.CHAT,
				Identifier.fromNamespaceAndPath(MOD_ID, "hud"),
				Hud::extract);

		LOGGER.info("{} loaded with {} modules", NAME, HackManager.all().size());
	}

	private static void tick(Minecraft client) {
		while (guiKey.consumeClick()) {
			if (client.screen == null) client.setScreen(new ClickGuiScreen());
		}
		HackManager.tick(client);
	}
}
