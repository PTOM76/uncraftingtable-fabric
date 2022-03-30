package ml.pkom.uncraftingtable;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class UncraftingTableClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(UncraftingScreenHandler.SCREEN_HANDLER_TYPE, UncraftingScreen::new);
    }
}
