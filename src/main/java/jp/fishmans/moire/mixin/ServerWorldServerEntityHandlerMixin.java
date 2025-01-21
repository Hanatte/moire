package jp.fishmans.moire.mixin;

import eu.pb4.polymer.virtualentity.api.attachment.EntityAttachment;
import jp.fishmans.moire.decorators.EntityDecoratorFactory;
import jp.fishmans.moire.decorators.EntityDecorators;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net/minecraft/server/world/ServerWorld$ServerEntityHandler")
public abstract class ServerWorldServerEntityHandlerMixin {
    @SuppressWarnings("unchecked")
    @Inject(method = "startTracking(Lnet/minecraft/entity/Entity;)V", at = @At("TAIL"))
    private void moire$startTracking(Entity entity, CallbackInfo ci) {
        for (var factory : EntityDecorators.INSTANCE.getFactories$moire(entity.getType())) {
            var decorator = ((EntityDecoratorFactory<Entity>) factory).create(entity);
            EntityAttachment.ofTicking(decorator.getElementHolder(), entity);
        }
    }
}
