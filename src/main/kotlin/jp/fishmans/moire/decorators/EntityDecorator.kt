package jp.fishmans.moire.decorators

import eu.pb4.polymer.virtualentity.api.ElementHolder
import net.minecraft.entity.Entity

public abstract class EntityDecorator<T : Entity>(public val entity: T) {
    public abstract fun getElementHolder(): ElementHolder
}

public fun interface EntityDecoratorFactory<T : Entity> {
    public fun create(entity: T): EntityDecorator<in T>
}