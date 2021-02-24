package net.titedog.codegadgets.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.CubeMapRenderer;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.options.AccessibilityOptionsScreen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.titedog.codegadgets.Codegadgets;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public abstract class TitleScreenMixin extends Screen {
    private final Identifier DF_BUTTON_TEXTURE = new Identifier(Codegadgets.MOD_ID + ":dfwidgetbutton.png");

    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("RETURN"), method = "initWidgetsNormal")
    private void createDiamondfireButton(int y, int spacingY, CallbackInfo ci) {

        this.addButton(new TexturedButtonWidget(this.width / 2 + 104, y + 48, 20, 20, 0, 0, 20, DF_BUTTON_TEXTURE, 32, 64, (buttonWidget) -> {
            MinecraftClient client = MinecraftClient.getInstance();
            ServerInfo serverInfo = new ServerInfo("Diamondfire", "mcdiamondfire.com", false);
            client.openScreen(new ConnectScreen(client.currentScreen, client, serverInfo));
        }, new TranslatableText(Codegadgets.MOD_ID + "text.dfbutton")));
    }
}
