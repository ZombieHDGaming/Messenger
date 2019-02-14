package org.dimdev.messenger;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import org.dimdev.messenger.network.PacketHandler;

public class Utils {

    public static String colorize(String s) {
        return s.replaceAll("&", "§");
    }

    public static void sendFromServer(IMessage message) {
        PacketHandler.INSTANCE.sendToAll(message);
    }

}
