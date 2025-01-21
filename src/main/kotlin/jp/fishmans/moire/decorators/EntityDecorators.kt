package jp.fishmans.moire.decorators

import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.CopyOnWriteArraySet

public object EntityDecorators {
    private val type2Decorators: MutableMap<EntityType<*>, MutableSet<EntityDecoratorFactory<*>>> = ConcurrentHashMap()

    public fun <T : Entity> register(type: EntityType<T>, decorator: EntityDecoratorFactory<T>) {
        type2Decorators.computeIfAbsent(type) { CopyOnWriteArraySet() }.add(decorator)
    }

    @Suppress("UNCHECKED_CAST")
    internal fun <T : Entity> getFactories(type: EntityType<T>): Set<EntityDecoratorFactory<T>> =
        type2Decorators[type] as? Set<EntityDecoratorFactory<T>> ?: emptySet()
}