package org.dimdev.messenger.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dimdev.messenger.Utils;
import org.dimdev.messenger.messages.NotificationPrivateMessage;

import javax.annotation.Nullable;

public class PacketPrivateMessage implements IMessage {

    private String player;
    private String message;

    public PacketPrivateMessage() {
        this.player = "";
        this.message = "";
    }

    public PacketPrivateMessage(String player, @Nullable String message) {
        this.player = player;
        this.message = message;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.player = ByteBufUtils.readUTF8String(buf);
        this.message = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, player);
        ByteBufUtils.writeUTF8String(buf, message);
    }

    public static class Handler implements IMessageHandler<PacketPrivateMessage, IMessage> {
        @Override
        public IMessage onMessage(PacketPrivateMessage message, MessageContext ctx) {
            if (ctx.side != Side.CLIENT) {
                Utils.sendFromServer(message);
                return null;
            }

            process(message);
            return null;
        }

        @SideOnly(Side.CLIENT)
        public void process(PacketPrivateMessage message) {
            if (Minecraft.getMinecraft().player.getName().equals(message.player)) {
                Minecraft.getMinecraft().addScheduledTask(() ->
                    new NotificationPrivateMessage(
                            message.player,
                            message.message).sendMessage()
                );
            }
        }
    }
}
