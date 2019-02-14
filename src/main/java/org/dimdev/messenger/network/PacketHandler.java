package org.dimdev.messenger.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import org.dimdev.messenger.Messenger;
import org.dimdev.messenger.network.packets.PacketChatNotification;
import org.dimdev.messenger.network.packets.PacketPrivateMessage;
import org.dimdev.messenger.network.packets.PacketToastNotification;

public class PacketHandler {

    public static SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Messenger.MODID);

    private static int ID = 0;

    private static int nextID() { return ID++; }

    public static void registerMessages() {
        INSTANCE.registerMessage(PacketToastNotification.Handler.class, PacketToastNotification.class, nextID(), Side.CLIENT);
        INSTANCE.registerMessage(PacketChatNotification.Handler.class, PacketChatNotification.class, nextID(), Side.CLIENT);
        INSTANCE.registerMessage(PacketPrivateMessage.Handler.class, PacketPrivateMessage.class, nextID(), Side.CLIENT);

        INSTANCE.registerMessage(PacketToastNotification.Handler.class, PacketToastNotification.class, nextID(), Side.SERVER);
        INSTANCE.registerMessage(PacketChatNotification.Handler.class, PacketChatNotification.class, nextID(), Side.SERVER);
        INSTANCE.registerMessage(PacketPrivateMessage.Handler.class, PacketPrivateMessage.class, nextID(), Side.SERVER);
    }
}
