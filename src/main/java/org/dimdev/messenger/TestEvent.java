package org.dimdev.messenger;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.dimdev.messenger.client.gui.GuiNotifications;

public class TestEvent {

    @SubscribeEvent
    public static void chat(ServerChatEvent event) {
        if (event.getMessage().contains("Chat")) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiNotifications());
        }
    }

    @SubscribeEvent
    public static void serverChat(ClientChatReceivedEvent event) {
        if (event.getMessage().getUnformattedText().contains("Chat")) {
            Minecraft.getMinecraft().displayGuiScreen(new GuiNotifications());
        }
    }

}
