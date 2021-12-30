package corgitaco.dimensionheightclamp.mixin.access;

import net.minecraft.world.level.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(DimensionType.class)
public interface DimensionTypeAccess {

    @Accessor @Mutable
    void setMinY(int minY);

    @Accessor @Mutable
    void setHeight(int height);

    @Accessor @Mutable
    void setLogicalHeight(int logicalHeight);
}
