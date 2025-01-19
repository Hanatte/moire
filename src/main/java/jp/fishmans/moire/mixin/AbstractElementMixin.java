package jp.fishmans.moire.mixin;

import eu.pb4.polymer.virtualentity.api.elements.AbstractElement;
import jp.fishmans.moire.internal.AbstractElementExtensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(value = AbstractElement.class, remap = false)
public abstract class AbstractElementMixin implements AbstractElementExtensions {
    @Unique
    private int moire$duration = -1;
    @Unique
    private int moire$tickCount = 0;

    @Override
    public int moire$getDuration() {
        return moire$duration;
    }

    @Override
    public void moire$setDuration(int duration) {
        moire$duration = duration;
    }

    @Override
    public int moire$getTickCount() {
        return moire$tickCount;
    }

    @Override
    public void moire$setTickCount(int tickCount) {
        moire$tickCount = tickCount;
    }
}
