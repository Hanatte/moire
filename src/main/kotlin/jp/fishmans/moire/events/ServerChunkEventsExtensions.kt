package jp.fishmans.moire.events

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents
import net.minecraft.server.world.ServerWorld
import net.minecraft.world.chunk.WorldChunk

public class ServerChunkScope @PublishedApi internal constructor(
    public val world: ServerWorld,
    public val chunk: WorldChunk
)

public inline fun serverChunkLoadHandler(crossinline block: ServerChunkScope.() -> Unit): ServerChunkEvents.Load =
    ServerChunkEvents.Load { world, chunk -> ServerChunkScope(world, chunk).block() }

public inline fun serverChunkGenerateHandler(crossinline block: ServerChunkScope.() -> Unit): ServerChunkEvents.Generate =
    ServerChunkEvents.Generate { world, chunk -> ServerChunkScope(world, chunk).block() }

public inline fun serverChunkUnloadHandler(crossinline block: ServerChunkScope.() -> Unit): ServerChunkEvents.Unload =
    ServerChunkEvents.Unload { world, chunk -> ServerChunkScope(world, chunk).block() }

@Deprecated(
    message = "Use 'serverChunkLoadHandler' instead.",
    replaceWith = ReplaceWith("serverChunkLoadHandler(block)")
)
public inline fun serverChunkLoad(crossinline block: ServerChunkScope.() -> Unit): ServerChunkEvents.Load =
    serverChunkLoadHandler(block)

@Deprecated(
    message = "Use 'serverChunkGenerateHandler' instead.",
    replaceWith = ReplaceWith("serverChunkGenerateHandler(block)")
)
public inline fun serverChunkGenerate(crossinline block: ServerChunkScope.() -> Unit): ServerChunkEvents.Generate =
    serverChunkGenerateHandler(block)

@Deprecated(
    message = "Use 'serverChunkUnloadHandler' instead.",
    replaceWith = ReplaceWith("serverChunkUnloadHandler(block)")
)
public inline fun serverChunkUnload(crossinline block: ServerChunkScope.() -> Unit): ServerChunkEvents.Unload =
    serverChunkUnloadHandler(block)