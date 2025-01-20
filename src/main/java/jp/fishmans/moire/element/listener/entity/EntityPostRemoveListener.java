package jp.fishmans.moire.element.listener.entity;

import jp.fishmans.moire.element.listener.Listener;
import net.minecraft.entity.Entity;

@FunctionalInterface
public interface EntityPostRemoveListener extends Listener {
    void onEntityPostRemove(Entity entity, Entity.RemovalReason reason);
}
