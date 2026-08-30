package com.bruno.ph_and_co.screen; // Ajuste para o seu pacote

import com.bruno.ph_and_co.menu.MortarMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class MortarScreen extends AbstractContainerScreen<MortarMenu> {

    // O caminho exato de onde a sua imagem PNG vai ficar salva!
    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath("ph_and_co", "textures/gui/mortar_gui.png");

    public MortarScreen(MortarMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        // Tamanho padrão de interfaces do Minecraft (como o Baú/Fornalha)
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void init() {
        super.init();
        // Remove os nomes padroes "Inventário" e "Mortar" se eles ficarem por cima dos slots.
        // Se quiser que apareçam, é só comentar essas duas linhas.
        this.titleLabelX = 10000;
        this.inventoryLabelX = 10000;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // Escurece o fundo do jogo
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        // Desenha a interface
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        // Desenha as caixinhas de texto quando passa o mouse nos itens
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(TEXTURE, x, y, 0, 0, this.imageWidth, this.imageHeight);

        int progress = this.menu.data.get(0);
        int maxProgress = this.menu.data.get(1);

        if (maxProgress > 0 && progress > 0) {

            int arrowWidth = (progress * 24) / maxProgress;

            guiGraphics.blit(TEXTURE, x + 82, y + 50, 176, 0, arrowWidth, 17);
        }
    }
}