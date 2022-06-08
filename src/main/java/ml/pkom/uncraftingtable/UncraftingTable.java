package ml.pkom.uncraftingtable;

import ml.pkom.mcpitanlib.api.register.v2.Registries;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UncraftingTable implements ModInitializer {

    public static final String MOD_ID = "uncraftingtable76";
    public static final String MOD_NAME = "UncraftingTable";

    public static Logger LOGGER = LogManager.getLogger();
    public static void log(Level level, String message){
        LOGGER.log(level, "[" + MOD_NAME + "] " + message);
    }

    @Override
    public void onInitialize() {
        Registries.registerBlock(id("uncraftingtable"), UncraftingTableBlock.UNCRAFTING_TABLE, new FabricItemSettings().group(ItemGroup.DECORATIONS));
        //Registry.register(Registry.BLOCK, id("uncraftingtable"), UncraftingTableBlock.UNCRAFTING_TABLE);
        //Registry.register(Registry.ITEM, id("uncraftingtable"), new BlockItem(UncraftingTableBlock.UNCRAFTING_TABLE, new FabricItemSettings().group(ItemGroup.DECORATIONS)));
        UncraftingScreenHandler.init();

        ServerPlayNetworking.registerGlobalReceiver(id("network"), ((server, player, handler, buf, responseSender) -> {
            NbtCompound nbt = buf.readNbt();
            if (nbt.contains("control")) {
                int ctrl = nbt.getInt("control");
                if (ctrl == 0) {
                    if (!(player.currentScreenHandler instanceof UncraftingScreenHandler)) return;
                    UncraftingScreenHandler screenHandler = (UncraftingScreenHandler) player.currentScreenHandler;
                    if (screenHandler.getSlot(0) instanceof InsertSlot) {
                        InsertSlot slot = (InsertSlot) screenHandler.getSlot(0);
                        if (slot.getStack().isEmpty()) return;
                        slot.removeRecipeIndex();
                    }
                }
                if (ctrl == 1) {
                    if (!(player.currentScreenHandler instanceof UncraftingScreenHandler)) return;
                    UncraftingScreenHandler screenHandler = (UncraftingScreenHandler) player.currentScreenHandler;
                    if (screenHandler.getSlot(0) instanceof InsertSlot) {
                        InsertSlot slot = (InsertSlot) screenHandler.getSlot(0);
                        if (slot.getStack().isEmpty()) return;
                        slot.addRecipeIndex();
                    }
                }
            }
        }));
    }

    public static Identifier id(String id) {
        return new Identifier(MOD_ID, id);
    }
}
