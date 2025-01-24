package jp.fishmans.moire.decorators.entities

import jp.fishmans.moire.decorators.Decorator
import jp.fishmans.moire.decorators.DecoratorContext
import net.minecraft.entity.Entity
import net.minecraft.server.world.ServerWorld
import kotlin.reflect.KClass

public abstract class EntityUnloadDecorator<T : Entity>(
    public val entityClass: KClass<T>
) : Decorator<EntityUnloadDecoratorContext<T>>

public class EntityUnloadDecoratorContext<T : Entity>(
    public val entity: T,
    public val world: ServerWorld
) : DecoratorContext