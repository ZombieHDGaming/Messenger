package org.dimdev.messenger.network.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import org.dimdev.messenger.client.gui.GuiPrivateMessage;
import org.dimdev.messenger.network.PacketHandler;

public class PacketShowGui implements IMessage {

    private String name;
    private EntityPlayerMP playerMP;

    public PacketShowGui() {
    }

    public PacketShowGui(String name, EntityPlayerMP playerMP) {
        this.name = name;
        this.playerMP = playerMP;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        ByteBufUtils.writeUTF8String(buf, name);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        name = ByteBufUtils.readUTF8String(buf);
    }

    public static class Handler implements IMessageHandler<PacketShowGui, IMessage> {
        @Override
        public IMessage onMessage(PacketShowGui message, MessageContext ctx) {
            if (ctx.side != Side.CLIENT) {
                PacketHandler.INSTANCE.sendTo(message, message.playerMP);
                return null;
            }

            switch(message.name) {
                case "private_message":
                    Minecraft.getMinecraft().displayGuiScreen(new GuiPrivateMessage());
                    break;
                default:
                    break;
            }
            return null;
        }
    }
}
