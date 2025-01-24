package jp.fishmans.moire.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;

import java.util.Arrays;

public interface EntityPreLoadCallback<T extends Entity> {
    Event<EntityPreLoadCallback<?>> EVENT = EventFactory.createArrayBacked(
            EntityPreLoadCallback.class,
            callbacks -> new EntityPreLoadCallback<>() {
                @Override
                public Class<Entity> getEntityClass() {
                    return Entity.class;
                }

                @SuppressWarnings("unchecked")
                @Override
                public void onEntityPreLoad(Entity entity, ServerWorld world) {
                    Arrays.stream(callbacks)
                            .filter(callback -> callback.getEntityClass().isInstance(entity))
                            .map(callback -> (EntityPreLoadCallback<Entity>) callback)
                            .forEach(callback -> callback.onEntityPreLoad(entity, world));
                }
            }
    );

    Class<T> getEntityClass();

    void onEntityPreLoad(T entity, ServerWorld world);
}
