package ml.pkom.uncraftingtable;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

public class UncraftingTableClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(UncraftingScreenHandler.SCREEN_HANDLER_TYPE, UncraftingScreen::new);
    }
}
