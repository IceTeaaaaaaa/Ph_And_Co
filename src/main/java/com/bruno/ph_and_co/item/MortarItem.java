package com.bruno.ph_and_co.item;

import com.bruno.ph_and_co.menu.MortarMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class MortarItem extends Item {

    public MortarItem(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, @NotNull Player player, @NotNull InteractionHand hand) {

        if (!level.isClientSide()) {

            player.openMenu(new SimpleMenuProvider(
                    (id, playerInv, p) -> new MortarMenu(id, playerInv),
                    Component.translatable("menu.ph_and_co.mortar")
            ));
        }


        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), level.isClientSide());
    }
}