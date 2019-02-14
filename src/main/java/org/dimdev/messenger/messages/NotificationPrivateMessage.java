package org.dimdev.messenger.messages;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import org.dimdev.messenger.Utils;

import javax.annotation.Nullable;

public class NotificationPrivateMessage {

    private String title;
    private String message;

    public NotificationPrivateMessage(String titleComponent, @Nullable String subtitleComponent) {
        this.title = titleComponent;
        this.message = subtitleComponent == null ? " " : subtitleComponent;
    }

    public void sendMessage() {
        Minecraft.getMinecraft().player.sendMessage(new TextComponentString("§7§m-----------------------------------------------------"));
        Minecraft.getMinecraft().player.sendMessage(new TextComponentString("§e§lFROM:§r §7§l" + title.toUpperCase()));
        Minecraft.getMinecraft().player.sendMessage(new TextComponentString(Utils.colorize(message)));
        Minecraft.getMinecraft().player.sendMessage(new TextComponentString("§7§m-----------------------------------------------------"));
    }
}
