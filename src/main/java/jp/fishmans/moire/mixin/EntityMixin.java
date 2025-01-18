package jp.fishmans.moire.mixin;

import eu.pb4.polymer.virtualentity.impl.HolderAttachmentHolder;
import jp.fishmans.moire.internal.ElementHolderExtensions;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Entity.class)
public abstract class EntityMixin {
    @Inject(method = "onRemove", at = @At(value = "HEAD"))
    private void moire$injectOnRemove(Entity.RemovalReason reason, CallbackInfo info) {
        var entity = (Entity) (Object) this;
        for (var attachment : ((HolderAttachmentHolder) entity).polymerVE$getHolders()) {
            for (var consumer : ((ElementHolderExtensions) attachment.holder()).moire$getEntityRemoveListeners()) {
                consumer.accept(entity, reason);
            }
        }
    }
}
