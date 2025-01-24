package jp.fishmans.moire.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;

import java.util.Arrays;

public interface EntityPostLoadCallback<T extends Entity> {
    Event<EntityPostLoadCallback<?>> EVENT = EventFactory.createArrayBacked(
            EntityPostLoadCallback.class,
            callbacks -> new EntityPostLoadCallback<>() {
                @Override
                public Class<Entity> getEntityClass() {
                    return Entity.class;
                }

                @SuppressWarnings("unchecked")
                @Override
                public void onEntityPostLoad(Entity entity, ServerWorld world) {
                    Arrays.stream(callbacks)
                            .filter(callback -> callback.getEntityClass().isInstance(entity))
                            .map(callback -> (EntityPostLoadCallback<Entity>) callback)
                            .forEach(callback -> callback.onEntityPostLoad(entity, world));
                }
            }
    );

    Class<T> getEntityClass();

    void onEntityPostLoad(T entity, ServerWorld world);
}
