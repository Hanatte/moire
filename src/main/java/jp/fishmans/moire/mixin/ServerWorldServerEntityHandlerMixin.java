package jp.fishmans.moire.mixin;

import jp.fishmans.moire.decorators.DecoratorRegistries;
import jp.fishmans.moire.decorators.entities.EntityLoadDecorator;
import jp.fishmans.moire.decorators.entities.EntityLoadDecoratorContext;
import jp.fishmans.moire.decorators.entities.EntityUnloadDecorator;
import jp.fishmans.moire.decorators.entities.EntityUnloadDecoratorContext;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net/minecraft/server/world/ServerWorld$ServerEntityHandler")
abstract class ServerWorldServerEntityHandlerMixin {
    @Shadow
    @Final
    ServerWorld field_26936;

    @SuppressWarnings("unchecked")
    @Inject(method = "startTracking(Lnet/minecraft/entity/Entity;)V", at = @At("TAIL"))
    private void moire$startTracking(Entity entity, CallbackInfo ci) {
        var context = new EntityLoadDecoratorContext<>(entity, field_26936);
        for (var decorator : DecoratorRegistries.ENTITY_LOAD.getDecorators()) {
            if (decorator.getEntityClass().isInstance(entity)) {
                ((EntityLoadDecorator<Entity>) decorator).decorate(context);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Inject(method = "stopTracking(Lnet/minecraft/entity/Entity;)V", at = @At("TAIL"))
    private void moire$stopTracking(Entity entity, CallbackInfo ci) {
        var context = new EntityUnloadDecoratorContext<>(entity, field_26936);
        for (var decorator : DecoratorRegistries.ENTITY_UNLOAD.getDecorators()) {
            if (decorator.getEntityClass().isInstance(entity)) {
                ((EntityUnloadDecorator<Entity>) decorator).decorate(context);
            }
        }
    }
}
