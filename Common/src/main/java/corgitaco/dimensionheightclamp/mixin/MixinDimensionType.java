package corgitaco.dimensionheightclamp.mixin;

import corgitaco.dimensionheightclamp.config.Config;
import corgitaco.dimensionheightclamp.mixin.access.DimensionTypeAccess;
import com.mojang.serialization.DataResult;
import net.minecraft.world.level.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DimensionType.class)
public class MixinDimensionType {

    @Inject(method = "guardY", at = @At("HEAD"))
    private static void clampY(DimensionType dimensionType, CallbackInfoReturnable<DataResult<DimensionType>> cir) {
        ((DimensionTypeAccess) dimensionType).setMinY(Config.getConfig(true).minYClamp().clampAndGet(dimensionType.minY()));
        final int newMaxY = Config.getConfig(true).maxYClamp().clampAndGet(dimensionType.minY() + dimensionType.height());
        final int height = newMaxY - dimensionType.minY();
        ((DimensionTypeAccess) dimensionType).setHeight(height);
        ((DimensionTypeAccess) dimensionType).setLogicalHeight(height);
    }
}
