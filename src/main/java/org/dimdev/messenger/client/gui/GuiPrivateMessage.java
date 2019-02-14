package org.dimdev.messenger.client.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import org.dimdev.messenger.network.PacketHandler;
import org.dimdev.messenger.network.packets.PacketPrivateMessage;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiPrivateMessage extends GuiScreen {

    private GuiTextField playerName;
    private GuiTextField message;
    private GuiButtonExt done;

    @Override
    public void initGui() {
        super.initGui();

        done = new GuiButtonExt(0, width / 2 - 100, height / 2 + 50, 200, 20, /*I18n.format("gui.done")*/"§lSEND MESSAGE");
        buttonList.add(done);

        playerName = new GuiTextField(2, fontRenderer, width / 2 - 100, height / 2 - 40, 200, 20);
        playerName.setMaxStringLength(50);
        playerName.setText("");
        playerName.setFocused(true);

        message = new GuiTextField(3, fontRenderer,width / 2 - 100, height / 2, 200, 20);
        message.setMaxStringLength(256);
        message.setText("");
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        fontRenderer.drawString("§e§lPrivate Message", width / 2 - (fontRenderer.getStringWidth("§lPrivate Message") / 2), height / 2 - 80, 0xFFFFFF, true);

        fontRenderer.drawString("§e§lPlayer", width / 2 - 100, height / 2 - 50, 0xFFFFFF, true);
        fontRenderer.drawString("§e§lMessage", width / 2 - 100, height / 2 - 10, 0xFFFFFF, true);

        playerName.drawTextBox();
        message.drawTextBox();
    }

    @Override
    public void updateScreen() {
        super.updateScreen();

        playerName.updateCursorCounter();
        message.updateCursorCounter();
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (playerName.mouseClicked(mouseX, mouseY, mouseButton) || message.mouseClicked(mouseX, mouseY, mouseButton))
            mouseHandled = true;
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);

        if (button == done) {
            if (playerName.getText().isEmpty()) {
                playerName.setFocused(true);
                return;
            }

            PacketHandler.INSTANCE.sendToServer(new PacketPrivateMessage(playerName.getText(), message.getText()));

            FMLClientHandler.instance().getClientPlayerEntity().closeScreen();
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_RETURN) {
            actionPerformed(done);
            return;
        }

        if (playerName.textboxKeyTyped(typedChar, keyCode) || message.textboxKeyTyped(typedChar, keyCode)) {
            keyHandled = true;
            return;
        }

        super.keyTyped(typedChar, keyCode);
    }
}
