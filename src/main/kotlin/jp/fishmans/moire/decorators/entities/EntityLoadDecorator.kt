package jp.fishmans.moire.decorators.entities

import jp.fishmans.moire.decorators.Decorator
import jp.fishmans.moire.decorators.DecoratorContext
import net.minecraft.entity.Entity
import net.minecraft.server.world.ServerWorld
import kotlin.reflect.KClass

public abstract class EntityLoadDecorator<T : Entity>(
    public val entityClass: KClass<T>
) : Decorator<EntityLoadDecoratorContext<T>>

public class EntityLoadDecoratorContext<T : Entity>(
    public val entity: T,
    public val world: ServerWorld
) : DecoratorContext