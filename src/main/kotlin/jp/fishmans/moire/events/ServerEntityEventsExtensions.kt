package jp.fishmans.moire.events

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents
import net.minecraft.entity.Entity
import net.minecraft.server.world.ServerWorld

public class ServerEntityScope<T : Entity> @PublishedApi internal constructor(
    public val entity: T,
    public val world: ServerWorld
)

public inline fun <reified T : Entity> serverEntityLoadHandler(crossinline block: ServerEntityScope<out T>.() -> Unit): ServerEntityEvents.Load =
    ServerEntityEvents.Load { entity, world -> if (entity is T) ServerEntityScope(entity, world).block() }

public inline fun <reified T : Entity> serverEntityUnloadHandler(crossinline block: ServerEntityScope<out T>.() -> Unit): ServerEntityEvents.Unload =
    ServerEntityEvents.Unload { entity, world -> if (entity is T) ServerEntityScope(entity, world).block() }

@Deprecated(
    message = "Use 'serverEntityLoadHandler' instead.",
    replaceWith = ReplaceWith("serverEntityLoadHandler(block)")
)
public inline fun <reified T : Entity> serverEntityLoad(crossinline block: ServerEntityScope<out T>.() -> Unit): ServerEntityEvents.Load =
    serverEntityLoadHandler(block)

@Deprecated(
    message = "Use 'serverEntityUnloadHandler' instead.",
    replaceWith = ReplaceWith("serverEntityUnloadHandler(block)")
)
public inline fun <reified T : Entity> serverEntityUnload(crossinline block: ServerEntityScope<out T>.() -> Unit): ServerEntityEvents.Unload =
    serverEntityUnloadHandler(block)