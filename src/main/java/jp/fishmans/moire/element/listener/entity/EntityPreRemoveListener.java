package jp.fishmans.moire.element.listener.entity;

import jp.fishmans.moire.element.listener.Listener;
import net.minecraft.entity.Entity;

@FunctionalInterface
public interface EntityPreRemoveListener extends Listener {
    void onEntityPreRemove(Entity entity, Entity.RemovalReason reason);
}
