package ml.pkom.uncraftingtable;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.collection.DefaultedList;

public class OutSlot extends Slot {
    public InsertSlot insertSlot;

    public OutSlot(Inventory inventory, int index, int x, int y, InsertSlot slot) {
        super(inventory, index, x, y);
        this.insertSlot = slot;
    }

    @Override
    public boolean canInsert(ItemStack stack) {
        return false;
    }

    public void superSetStack(ItemStack stack) {
        super.setStack(stack);
    }

    @Override
    public void setStack(ItemStack stack) {
        super.setStack(stack);
        if (stack.isEmpty()) {
            insertSlot.player.inventory.offerOrDrop(insertSlot.player.world, insertSlot.player.inventory.getCursorStack());
            /*if (canGive(insertSlot.player.inventory.main)) {
                insertSlot.player.giveItemStack(insertSlot.player.inventory.getCursorStack());
            } else {
                insertSlot.player.dropItem(insertSlot.player.inventory.getCursorStack(), false);
            }

             */
            insertSlot.player.inventory.getCursorStack().setCount(0);

            for (int i = 1;i < 10;i++) {
                insertSlot.player.inventory.offerOrDrop(insertSlot.player.world, inventory.getStack(i));
                /*
                if (canGive(insertSlot.player.inventory.main)) {
                    insertSlot.player.giveItemStack(inventory.getStack(i));
                } else {
                    insertSlot.player.dropItem(inventory.getStack(i), false);
                }

                 */
                inventory.setStack(i, ItemStack.EMPTY);
            }
            if (insertSlot.getStack().getCount() - insertSlot.latestOutputCount == 0) {
                insertSlot.setStackSuper(ItemStack.EMPTY);
            } else {
                ItemStack insertStack = insertSlot.getStack().copy();
                insertStack.setCount(insertStack.getCount() - insertSlot.latestOutputCount);
                insertSlot.setStack(insertStack);
            }
        }
    }

    public static boolean canGive(DefaultedList<ItemStack> inv) {
        for ( ItemStack stack : inv ) {
            if (stack.isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
