package ml.pkom.uncraftingtable;

import com.mojang.blaze3d.platform.GlStateManager;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class UncraftingScreen extends HandledScreen<ScreenHandler> {

    public static Identifier GUI = UncraftingTable.id("textures/uncrafting_table.png");

    public UncraftingScreen(ScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        this.backgroundWidth = 176;
        this.backgroundHeight = 166;
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        this.addButton(new TexturedButtonWidget(x + 31,  y +58, 12, 12, 0, 168, 16, GUI, (buttonWidget) -> {
            // クライアントの反映
            if (handler.getSlot(0) instanceof InsertSlot) {
                InsertSlot slot = (InsertSlot) handler.getSlot(0);
                if (slot.getStack().isEmpty()) return;
                slot.removeRecipeIndex();
            }
            // サーバーに送信
            PacketByteBuf buf = PacketByteBufs.create();
            CompoundTag nbt = new CompoundTag();
            nbt.putInt("control", 0);
            buf.writeCompoundTag(nbt);
            ClientPlayNetworking.send(UncraftingTable.id("network"), buf);
        }));

        this.addButton(new TexturedButtonWidget( x + 45, y + 58, 12, 12, 16, 168, 16, GUI, (buttonWidget) -> {
            // クライアントの反映
            if (handler.getSlot(0) instanceof InsertSlot) {
                InsertSlot slot = (InsertSlot) handler.getSlot(0);
                if (slot.getStack().isEmpty()) return;
                slot.addRecipeIndex();
            }
            // サーバーに送信
            PacketByteBuf buf = PacketByteBufs.create();
            CompoundTag nbt = new CompoundTag();
            nbt.putInt("control", 1);
            buf.writeCompoundTag(nbt);
            ClientPlayNetworking.send(UncraftingTable.id("network"), buf);
        }));
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;

        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        MinecraftClient.getInstance().getTextureManager().bindTexture(GUI);
        drawTexture(matrices, x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        super.drawForeground(matrices, mouseX, mouseY);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }
}
