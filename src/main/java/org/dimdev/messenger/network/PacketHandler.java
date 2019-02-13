package org.dimdev.messenger.network;

import net.minecraft.client.Minecraft;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dimdev.messenger.Messenger;
import org.dimdev.messenger.network.packets.PacketChatNotification;
import org.dimdev.messenger.network.packets.PacketToastNotification;

public class PacketHandler {

    public static SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Messenger.MODID);

    private static int ID = 0;

    private static int nextID() { return ID++; }

    public static void registerMessages() {
        INSTANCE.registerMessage(PacketToastNotification.Handler.class, PacketToastNotification.class, nextID(), Side.CLIENT);
        INSTANCE.registerMessage(PacketChatNotification.Handler.class, PacketChatNotification.class, nextID(), Side.CLIENT);

        //INSTANCE.registerMessage(PacketToastNotification.Handler.class, PacketToastNotification.class, nextID(), Side.SERVER);
        //INSTANCE.registerMessage(PacketChatNotification.Handler.class, PacketChatNotification.class, nextID(), Side.SERVER);
    }

    public static IThreadListener getThreadListener(MessageContext ctx) {
        return ctx.side == Side.SERVER ? (WorldServer) ctx.getServerHandler().player.world : getClientThreadListener();
    }

    @SideOnly(Side.CLIENT)
    public static IThreadListener getClientThreadListener() {
        return Minecraft.getMinecraft();
    }

}
