package jp.fishmans.moire.events

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerChunkEvents
import net.minecraft.server.world.ServerWorld
import net.minecraft.world.chunk.WorldChunk

public class ServerChunkScope @PublishedApi internal constructor(
    public val world: ServerWorld,
    public val chunk: WorldChunk
)

public inline fun serverChunkLoad(crossinline block: ServerChunkScope.() -> Unit): ServerChunkEvents.Load =
    ServerChunkEvents.Load { world, chunk -> ServerChunkScope(world, chunk).block() }

public inline fun serverChunkGenerate(crossinline block: ServerChunkScope.() -> Unit): ServerChunkEvents.Generate =
    ServerChunkEvents.Generate { world, chunk -> ServerChunkScope(world, chunk).block() }

public inline fun serverChunkUnload(crossinline block: ServerChunkScope.() -> Unit): ServerChunkEvents.Unload =
    ServerChunkEvents.Unload { world, chunk -> ServerChunkScope(world, chunk).block() }