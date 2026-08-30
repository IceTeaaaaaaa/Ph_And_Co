package com.bruno.ph_and_co.item; // Ajuste para a sua pasta de itens

import com.bruno.ph_and_co.menu.MortarMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class MortarItem extends Item {

    public MortarItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        // Se estivermos no servidor (que é quem manda na lógica)
        if (!level.isClientSide()) {
            // Abre o menu na tela do jogador
            player.openMenu(new SimpleMenuProvider(
                    (id, playerInv, p) -> new MortarMenu(id, playerInv),
                    Component.translatable("menu.ph_and_co.mortar") // O nome que vai aparecer no topo da tela
            ));
        }

        // Retorna sucesso balançando a mão do personagem
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
    }
}