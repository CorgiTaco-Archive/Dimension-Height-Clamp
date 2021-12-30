package corgitaco.dimensionheightclamp.mixin.client;

import corgitaco.dimensionheightclamp.config.Config;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import net.minecraft.client.main.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.File;


@Mixin(value = Main.class)
public class MixinClientMain {

    @Inject(method = "parseArgument", at = @At("RETURN"))
    private static <T> void setConfigDir(OptionSet set, OptionSpec<T> spec, CallbackInfoReturnable<T> cir) {
        if (cir.getReturnValue() instanceof File file && spec.options().contains("gameDir")) {
            Config.configDir = file.toPath().resolve("config");
        }
    }
}
