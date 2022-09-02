package de.venarge.magicnature.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.venarge.magicnature.MagicNature;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class AltarBlockScreen extends AbstractContainerScreen<AltarBlockMenu> {

   private static final ResourceLocation TEXTURE =
           new ResourceLocation (MagicNature.MOD_ID, "textures/gui/altar_gui.png");

    public AltarBlockScreen(AltarBlockMenu p_97741_, Inventory p_97742_, Component p_97743_) {
        super (p_97741_, p_97742_, p_97743_);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int mouseX, int mouseY) {
        RenderSystem.setShader (GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor (1.0F,1.0F,1.0F,1.0F);
        RenderSystem.setShaderTexture (0,TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        this.blit(pPoseStack,x,y,0,0,imageHeight,imageHeight);
    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render (pPoseStack, mouseX, mouseY, delta);
        renderTooltip (pPoseStack,mouseX,mouseY);
    }
}
