package corgitaco.dimensionheightclamp.mixin.server;

import corgitaco.dimensionheightclamp.config.Config;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpec;
import net.minecraft.server.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.nio.file.Paths;

@Mixin(Main.class)
public class MixinServerMain {

    @SuppressWarnings("InvalidInjectorMethodSignature")
    @Inject(method = "main", at = @At(value = "INVOKE", target = "Ljoptsimple/OptionSet;has(Ljoptsimple/OptionSpec;)Z"), locals = LocalCapture.CAPTURE_FAILHARD, remap = false)
    private static void setConfigDir(String p_129699_[], CallbackInfo ci, OptionParser optionparser, OptionSpec optionspec, OptionSpec optionspec1, OptionSpec optionspec2, OptionSpec optionspec3, OptionSpec optionspec4, OptionSpec optionspec5, OptionSpec optionspec6, OptionSpec optionspec7, OptionSpec optionspec8, OptionSpec optionspec9, OptionSpec optionspec10, OptionSpec optionspec11, OptionSpec optionspec12, OptionSpec optionspec13, OptionSpec optionspec14, OptionSet optionset) {
        Config.configDir = Paths.get("config");
    }
}
