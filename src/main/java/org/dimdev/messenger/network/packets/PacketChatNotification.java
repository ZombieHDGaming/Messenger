package org.dimdev.messenger.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import org.dimdev.messenger.network.PacketHandler;
import org.dimdev.messenger.messages.NotificationChat;

import javax.annotation.Nullable;

public class PacketChatNotification implements IMessage {

    private String title;
    private String subtitle;

    public PacketChatNotification() {
        this.title = " ";
        this.subtitle = null;
    }

    public PacketChatNotification(String title, @Nullable String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.title = ByteBufUtils.readUTF8String(buf);
        this.subtitle = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, title);
        ByteBufUtils.writeUTF8String(buf, subtitle);
    }

    public static class Handler implements IMessageHandler<PacketChatNotification, IMessage> {
        @Override
        public IMessage onMessage(PacketChatNotification message, MessageContext ctx) {
            PacketHandler.getThreadListener(ctx).addScheduledTask(() ->
                    new NotificationChat(
                            new TextComponentString(message.title),
                            new TextComponentString(message.subtitle)
                    ).sendMessage());
            return null;
        }
    }

}
