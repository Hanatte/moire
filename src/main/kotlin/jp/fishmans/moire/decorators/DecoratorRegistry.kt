package jp.fishmans.moire.decorators

import jp.fishmans.moire.decorators.entities.EntityLoadDecorator
import jp.fishmans.moire.decorators.entities.EntityUnloadDecorator
import java.util.concurrent.CopyOnWriteArrayList

public class DecoratorRegistry<T : Decorator<*>> {
    private val _decorators: MutableList<T> = CopyOnWriteArrayList()

    public val decorators: List<T> get() = _decorators

    public fun register(decorator: T) {
        _decorators.add(decorator)
    }
}

public object DecoratorRegistries {
    @JvmField
    public val ENTITY_LOAD: DecoratorRegistry<EntityLoadDecorator<*>> = DecoratorRegistry()

    @JvmField
    public val ENTITY_UNLOAD: DecoratorRegistry<EntityUnloadDecorator<*>> = DecoratorRegistry()
}