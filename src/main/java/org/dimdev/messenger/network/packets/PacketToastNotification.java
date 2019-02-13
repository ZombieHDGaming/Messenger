package org.dimdev.messenger.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dimdev.messenger.network.PacketHandler;
import org.dimdev.messenger.messages.NotificationToast;

import javax.annotation.Nullable;

public class PacketToastNotification implements IMessage {

    private String title;
    private String subtitle;

    public PacketToastNotification() {
        this.title = " ";
        this.subtitle = null;
    }

    public PacketToastNotification(String title, @Nullable String subtitle) {
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

    public static class Handler implements IMessageHandler<PacketToastNotification, IMessage> {
        @Override
        public IMessage onMessage(PacketToastNotification message, MessageContext ctx) {
            if (ctx.side != Side.CLIENT) {
                return null;
            }

            process(message, ctx);
            return null;
        }

        @SideOnly(Side.CLIENT)
        public void process(PacketToastNotification message, MessageContext ctx) {
            PacketHandler.getThreadListener(ctx).addScheduledTask(() ->
                    Minecraft.getMinecraft().getToastGui().add(new NotificationToast(
                                    new TextComponentString(message.title),
                                    new TextComponentString(message.subtitle)
                            )
                    )
            );
        }
    }
}
