package jp.fishmans.moire.mixin;

import eu.pb4.polymer.virtualentity.api.elements.AbstractElement;
import jp.fishmans.moire.internal.AbstractElementExtensions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Mixin(value = AbstractElement.class, remap = false)
public abstract class AbstractElementMixin implements AbstractElementExtensions {
    @Unique
    private final List<Runnable> moire$tickListeners = new CopyOnWriteArrayList<>();

    @Override
    public List<? extends Runnable> moire$getTickListeners() {
        return moire$tickListeners;
    }

    @Override
    public void moire$addTickListener(Runnable runnable) {
        moire$tickListeners.add(runnable);
    }
}
