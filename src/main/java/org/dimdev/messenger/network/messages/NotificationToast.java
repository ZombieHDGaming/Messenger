package org.dimdev.messenger.network.messages;

import net.minecraft.client.gui.toasts.GuiToast;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.dimdev.messenger.Utils;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class NotificationToast implements IToast {

    private String title;
    private String subtitle;

    public NotificationToast(ITextComponent titleComponent, @Nullable ITextComponent subtitleComponent) {
        this.title = titleComponent.getUnformattedText();
        this.subtitle = subtitleComponent == null ? null : subtitleComponent.getUnformattedText();
    }

    @Override
    public Visibility draw(GuiToast toastGui, long delta) {
        toastGui.getMinecraft().getTextureManager().bindTexture(TEXTURE_TOASTS);
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        toastGui.drawTexturedModalRect(0, 0, 0, 0, 160, 32);

        if (this.subtitle == null)
        {
            toastGui.getMinecraft().fontRenderer.drawString(Utils.colorize(this.title), 8, 12, -1);
        }
        else
        {
            toastGui.getMinecraft().fontRenderer.drawString(Utils.colorize(this.title), 8, 7, -1, true);
            toastGui.getMinecraft().fontRenderer.drawString(Utils.colorize(this.subtitle), 8, 18, -1);
        }

        return delta <= 7500L ? IToast.Visibility.SHOW : IToast.Visibility.HIDE;
    }
}
