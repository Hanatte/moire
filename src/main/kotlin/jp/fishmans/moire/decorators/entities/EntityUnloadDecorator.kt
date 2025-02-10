package jp.fishmans.moire.decorators.entities

import jp.fishmans.moire.decorators.Decorator
import jp.fishmans.moire.decorators.DecoratorContext
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.server.world.ServerWorld

public interface EntityUnloadDecorator<T : Entity> : Decorator<EntityUnloadDecoratorContext<out T>> {
    public val entityType: EntityType<out T>
}

public data class EntityUnloadDecoratorContext<T : Entity>(
    public val entity: T,
    public val world: ServerWorld
) : DecoratorContext