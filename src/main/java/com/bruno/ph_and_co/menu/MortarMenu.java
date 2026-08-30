package com.bruno.ph_and_co.menu;

import com.bruno.ph_and_co.ModItems; // Mude para o seu pacote!
import com.bruno.ph_and_co.recipe.ChanceResult;
import com.bruno.ph_and_co.recipe.ModRecipes; // Mude para o seu pacote!
import com.bruno.ph_and_co.recipe.MortarRecipe;
import com.bruno.ph_and_co.recipe.MortarRecipeInput;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.crafting.SizedIngredient;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MortarMenu extends AbstractContainerMenu {
    private final Container mortarInventory;
    public final ContainerData data;
    private final Player player;

    public int progress = 0;
    public int maxProgress = 20;
    private long lastTickTime = 0;

    public MortarMenu(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, new SimpleContainer(8), new SimpleContainerData(2));
    }

    public MortarMenu(int containerId, Inventory playerInventory, Container mortarInventory, ContainerData data) {
        super(ModMenus.MORTAR_MENU.get(), containerId);
        this.mortarInventory = mortarInventory;
        this.data = data;
        this.player = playerInventory.player;

        checkContainerSize(mortarInventory, 8);
        mortarInventory.startOpen(this.player);

        // ALINHAMENTO DA TIGELA: Grid 3x2 de entrada
        for (int row = 0; row < 2; row++) {
            for (int col = 0; col < 3; col++) {
                this.addSlot(new Slot(mortarInventory, col + (row * 3), 20 + (col * 18), 17 + (row * 18)));
            }
        }

        // ALINHAMENTO DO PANO: Coluna 1x2 de saída
        for (int row = 0; row < 2; row++) {
            this.addSlot(new Slot(mortarInventory, 6 + row, 120, 29 + (row * 18)));
        }

        // Adiciona o inventário do jogador embaixo
        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
        this.addDataSlots(data);
    }

    // --- O MOTOR DA MÁQUINA BLINDADO ---
    public void tick() {
        if (this.player.level().isClientSide()) return;

        long currentTime = this.player.level().getGameTime();
        if (currentTime == this.lastTickTime) {
            return;
        }
        this.lastTickTime = currentTime;

        List<ItemStack> inputStacks = new ArrayList<>();
        for (int i = 0; i < 6; i++) inputStacks.add(this.slots.get(i).getItem());
        MortarRecipeInput recipeInput = new MortarRecipeInput(inputStacks);

        Optional<RecipeHolder<MortarRecipe>> recipeOpt = this.player.level()
                .getRecipeManager()
                .getRecipeFor(ModRecipes.MORTAR_TYPE.get(), recipeInput, this.player.level());

        if (recipeOpt.isPresent()) {
            MortarRecipe recipe = recipeOpt.get().value();
            this.maxProgress = recipe.processTime();

            if (!canFitResult(recipe)) {
                return;
            }

            this.progress++;

            this.data.set(0, this.progress);
            this.data.set(1, this.maxProgress);

            if (this.progress >= this.maxProgress) {
                craftItem(recipe);
                this.progress = 0;
                this.data.set(0, 0);
            }
        } else {
            this.progress = 0;
            this.data.set(0, 0);
            this.data.set(1, 20);
        }
    }


    private boolean canFitResult(MortarRecipe recipe) {
        for (ChanceResult chanceResult : recipe.results()) {
            ItemStack outputCopy = chanceResult.stack().copy();
            boolean fits = false;
            for (int i = 6; i <= 7; i++) {
                ItemStack outSlot = this.slots.get(i).getItem();
                if (outSlot.isEmpty() || (ItemStack.isSameItemSameComponents(outSlot, outputCopy) && outSlot.getCount() + outputCopy.getCount() <= outSlot.getMaxStackSize())) {
                    fits = true;
                    break;
                }
            }
            if (!fits) return false;
        }
        return true;
    }

    private void craftItem(MortarRecipe recipe) {

        for (SizedIngredient sizedIng : recipe.ingredients()) {
            int remainingToConsume = sizedIng.count();
            for (int i = 0; i < 6; i++) {
                ItemStack slotStack = this.slots.get(i).getItem();
                if (sizedIng.ingredient().test(slotStack)) {
                    int shrinkAmount = Math.min(remainingToConsume, slotStack.getCount());
                    slotStack.shrink(shrinkAmount);
                    remainingToConsume -= shrinkAmount;
                    if (remainingToConsume <= 0) break;
                }
            }
        }

        for (ChanceResult chanceResult : recipe.results()) {

            if (chanceResult.chance() < 1.0f && this.player.getRandom().nextFloat() >= chanceResult.chance()) {
                continue;
            }

            ItemStack outputCopy = chanceResult.stack().copy();
            for (int i = 7; i >= 6; i--) {
                ItemStack outSlot = this.slots.get(i).getItem();
                if (outSlot.isEmpty()) {
                    this.slots.get(i).set(outputCopy);
                    break;
                } else if (ItemStack.isSameItemSameComponents(outSlot, outputCopy) && outSlot.getCount() + outputCopy.getCount() <= outSlot.getMaxStackSize()) {
                    outSlot.grow(outputCopy.getCount());
                    break;
                }
            }
        }

        this.player.level().playSound(null, this.player.blockPosition(), SoundEvents.GRINDSTONE_USE, SoundSource.BLOCKS, 1.0F, 1.0F);

    }

    public static void onPlayerTick(PlayerTickEvent.Post event) {
        if (event.getEntity().containerMenu instanceof MortarMenu mortarMenu) {
            mortarMenu.tick();
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return player.getMainHandItem().is(ModItems.MORTAR.get()) || player.getOffhandItem().is(ModItems.MORTAR.get());
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.clearContainer(player, this.mortarInventory);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (index < 8) {
                if (!this.moveItemStackTo(itemstack1, 8, this.slots.size(), true)) return ItemStack.EMPTY;
            } else if (!this.moveItemStackTo(itemstack1, 0, 6, false)) return ItemStack.EMPTY;
            if (itemstack1.isEmpty()) slot.setByPlayer(ItemStack.EMPTY);
            else slot.setChanged();
        }
        return itemstack;
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 84 + i * 18));
            }
        }
    }

    // DE VOLTA AO NORMAL: 1 linha de 9
    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }
}