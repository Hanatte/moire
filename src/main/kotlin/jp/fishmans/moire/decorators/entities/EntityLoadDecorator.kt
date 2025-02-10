package jp.fishmans.moire.decorators.entities

import jp.fishmans.moire.decorators.Decorator
import jp.fishmans.moire.decorators.DecoratorContext
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.server.world.ServerWorld

public interface EntityLoadDecorator<T : Entity> : Decorator<EntityLoadDecoratorContext<out T>> {
    public val entityType: EntityType<out T>
}

public data class EntityLoadDecoratorContext<T : Entity>(
    public val entity: T,
    public val world: ServerWorld
) : DecoratorContext