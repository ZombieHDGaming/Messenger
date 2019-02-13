package org.dimdev.messenger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.dimdev.messenger.network.PacketHandler;

@Mod(modid = Messenger.MODID, name = Messenger.NAME, version = Messenger.VERSION, acceptedMinecraftVersions = "[1.12,)")
public class Messenger
{
    public static final String MODID = "messenger";
    public static final String NAME = "Messenger";
    public static final String VERSION = "1.0";
    
    @EventHandler
    public void init(FMLPreInitializationEvent event) {
        PacketHandler.registerMessages();
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            MinecraftForge.EVENT_BUS.register(TestEvent.class);
        }
    }
}
