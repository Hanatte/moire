package jp.fishmans.moire.mixin;

import eu.pb4.polymer.virtualentity.impl.HolderAttachmentHolder;
import jp.fishmans.moire.element.ElementHolderExtensions;
import jp.fishmans.moire.element.listener.entity.EntityPostRemoveListener;
import jp.fishmans.moire.element.listener.entity.EntityPreRemoveListener;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = Entity.class)
public abstract class EntityMixin {
    @Inject(method = "setRemoved", at = @At(value = "HEAD"))
    private void moire$beforeSetRemove(Entity.RemovalReason reason, CallbackInfo ci) {
        var entity = (Entity) (Object) this;
        for (var attachment : ((HolderAttachmentHolder) this).polymerVE$getHolders()) {
            ((ElementHolderExtensions) attachment.holder()).moire$triggerEvent(EntityPreRemoveListener.class, listener -> listener.onEntityPreRemove(entity, reason));
        }
    }

    @Inject(method = "setRemoved", at = @At(value = "TAIL"))
    private void moire$afterSetRemove(Entity.RemovalReason reason, CallbackInfo ci) {
        var entity = (Entity) (Object) this;
        for (var attachment : ((HolderAttachmentHolder) this).polymerVE$getHolders()) {
            ((ElementHolderExtensions) attachment.holder()).moire$triggerEvent(EntityPostRemoveListener.class, listener -> listener.onEntityPostRemove(entity, reason));
        }
    }
}
