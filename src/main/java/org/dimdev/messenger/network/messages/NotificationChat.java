package org.dimdev.messenger.network.messages;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import org.dimdev.messenger.Utils;

import javax.annotation.Nullable;

public class NotificationChat {

    private String title;
    private String message;

    public NotificationChat(ITextComponent titleComponent, @Nullable ITextComponent subtitleComponent) {
        this.title = titleComponent.getUnformattedText();
        this.message = subtitleComponent == null ? " " : subtitleComponent.getUnformattedText();
    }

    public void sendMessage() {
        Minecraft.getMinecraft().player.sendMessage(new TextComponentString("§a§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
        Minecraft.getMinecraft().player.sendMessage(new TextComponentString(" "));
        Minecraft.getMinecraft().player.sendMessage(new TextComponentString(Utils.colorize(title.toUpperCase())));
        Minecraft.getMinecraft().player.sendMessage(new TextComponentString(" "));
        Minecraft.getMinecraft().player.sendMessage(new TextComponentString(Utils.colorize(message)));
        Minecraft.getMinecraft().player.sendMessage(new TextComponentString(" "));
        Minecraft.getMinecraft().player.sendMessage(new TextComponentString("§a§l▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬▬"));
    }

}
