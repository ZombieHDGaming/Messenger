package org.dimdev.messenger.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.config.GuiSlider;
import org.dimdev.messenger.network.PacketHandler;
import org.dimdev.messenger.network.packets.PacketChatNotification;
import org.dimdev.messenger.network.packets.PacketToastNotification;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiNotifications extends GuiScreen {

    private GuiTextField titleField;
    private GuiTextField subtitleField;
    private GuiSlider slider;
    private GuiButton btnDone;
    private GuiButton btnCancel;
    private boolean sliderHandled;

    @Override
    public void initGui() {
        super.initGui();
        btnDone = new GuiButton(0, width / 2 - 120, height / 2 + 50, 100, 20, /*I18n.format("gui.done")*/"§lDONE");
        buttonList.add(btnDone);

        btnCancel = new GuiButton(1, width / 2 + 20, height / 2 + 50, 100, 20, /*I18n.format("gui.done")*/"§lCANCEL");
        buttonList.add(btnCancel);

        titleField = new GuiTextField(2, fontRenderer, width / 2 - 100, height / 2 - 40, 200, 20);
        titleField.setMaxStringLength(128);
        titleField.setText("");
        titleField.setFocused(true);

        subtitleField = new GuiTextField(3, fontRenderer,width / 2 - 100, height / 2, 200, 20);
        subtitleField.setMaxStringLength(360);
        subtitleField.setText("");

        slider = new GuiSlider(4, width / 2 - 75, height / 2 + 25, 150, 20, "", "", 1, 10, 1, false, true);
        buttonList.add(slider);
        Keyboard.enableRepeatEvents(true);
    }

    @Override
    public void updateScreen() {
        titleField.updateCursorCounter();
        subtitleField.updateCursorCounter();
        if (sliderHandled) {
            slider.updateSlider();
            sliderHandled = false;
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        fontRenderer.drawString("§lNotification GUI", width / 2 - (fontRenderer.getStringWidth("§lNotification GUI") / 2), height / 2 - 80, 0xFFFFFF, true);

        fontRenderer.drawString("§lTitle", width / 2 - 100, height / 2 - 50, 0xFFFFFF, true);
        fontRenderer.drawString("§lSubtitle", width / 2 - 100, height / 2 - 10, 0xFFFFFF, true);

        titleField.drawTextBox();
        subtitleField.drawTextBox();
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button == btnDone) {
            if (titleField.getText().isEmpty()) {
                titleField.setFocused(true);
                return;
            }

            switch (slider.getValueInt()) {
                case 1:
                    PacketHandler.INSTANCE.sendToServer(new PacketToastNotification(titleField.getText(), subtitleField.getText()));
                    break;
                case 2:
                    PacketHandler.INSTANCE.sendToServer(new PacketChatNotification(titleField.getText(), subtitleField.getText()));
                    break;
                default:
                    Minecraft.getMinecraft().player.sendStatusMessage(new TextComponentString("§e§lNOTIFICATION TYPE NOT FOUND"), true);
                    break;
            }

            FMLClientHandler.instance().getClientPlayerEntity().closeScreen();
        } else if (button == btnCancel) {
            FMLClientHandler.instance().getClientPlayerEntity().closeScreen();
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (titleField.mouseClicked(mouseX, mouseY, mouseButton)
                || subtitleField.mouseClicked(mouseX, mouseY, mouseButton)
                || slider.mousePressed(mc, mouseX, mouseY)) {
            mouseHandled = true;
            sliderHandled = true;
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);

        slider.mouseReleased(mouseX, mouseY);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == Keyboard.KEY_RETURN) {
            actionPerformed(btnDone);
            return;
        }

        if (titleField.textboxKeyTyped(typedChar, keyCode) || subtitleField.textboxKeyTyped(typedChar, keyCode)) {
            keyHandled = true;
            return;
        }

        super.keyTyped(typedChar, keyCode);
    }
}
