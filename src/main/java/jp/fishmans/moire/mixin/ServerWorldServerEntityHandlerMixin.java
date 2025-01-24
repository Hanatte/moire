package jp.fishmans.moire.mixin;

import jp.fishmans.moire.event.EntityPostLoadCallback;
import jp.fishmans.moire.event.EntityPreLoadCallback;
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
    @Inject(method = "startTracking(Lnet/minecraft/entity/Entity;)V", at = @At("HEAD"))
    private void moire$beforeStartTracking(Entity entity, CallbackInfo ci) {
        ((EntityPreLoadCallback<Entity>) EntityPreLoadCallback.EVENT.invoker()).onEntityPreLoad(entity, field_26936);
    }

    @SuppressWarnings("unchecked")
    @Inject(method = "startTracking(Lnet/minecraft/entity/Entity;)V", at = @At("TAIL"))
    private void moire$afterStartTracking(Entity entity, CallbackInfo ci) {
        ((EntityPostLoadCallback<Entity>) EntityPostLoadCallback.EVENT.invoker()).onEntityPostLoad(entity, field_26936);
    }
}
