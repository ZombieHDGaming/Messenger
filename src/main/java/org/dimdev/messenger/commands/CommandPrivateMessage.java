package org.dimdev.messenger.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.client.IClientCommand;
import org.dimdev.messenger.client.gui.DelayedGuiDisplayTicker;
import org.dimdev.messenger.client.gui.GuiPrivateMessage;

import java.util.ArrayList;
import java.util.List;

public class CommandPrivateMessage extends CommandBase implements IClientCommand {

    private List<String> alias;

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return true;
    }

    public CommandPrivateMessage() {
        alias = new ArrayList<>();
        alias.add("pm");
        alias.add("msg");
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 1;
    }

    @Override
    public List<String> getAliases() {
        return this.alias;
    }

    @Override
    public String getName() {
        return "privatemessage";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "privatemessage";
    }

    @Override
    public boolean allowUsageWithoutPrefix(ICommandSender sender, String message) {
        return false;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        new DelayedGuiDisplayTicker(1, new GuiPrivateMessage());
    }
}
