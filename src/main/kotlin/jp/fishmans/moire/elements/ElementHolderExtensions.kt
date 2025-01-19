package jp.fishmans.moire.elements

import eu.pb4.polymer.virtualentity.api.ElementHolder
import eu.pb4.polymer.virtualentity.api.VirtualEntityUtils
import eu.pb4.polymer.virtualentity.api.attachment.ChunkAttachment
import eu.pb4.polymer.virtualentity.api.attachment.EntityAttachment
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment
import eu.pb4.polymer.virtualentity.api.elements.*
import jp.fishmans.moire.internal.ElementHolderExtensions
import net.minecraft.entity.Entity
import net.minecraft.server.network.ServerPlayNetworkHandler
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.Vec3d

public inline fun elementHolder(block: ElementHolder.() -> Unit): ElementHolder = ElementHolder().apply(block)

public inline fun ElementHolder.blockDisplayElement(block: BlockDisplayElement.() -> Unit): BlockDisplayElement =
    addElement(jp.fishmans.moire.elements.blockDisplayElement(block))

public inline fun ElementHolder.interactionElement(block: InteractionElement.() -> Unit): InteractionElement =
    addElement(jp.fishmans.moire.elements.interactionElement(block))

public inline fun ElementHolder.itemDisplayElement(block: ItemDisplayElement.() -> Unit): ItemDisplayElement =
    addElement(jp.fishmans.moire.elements.itemDisplayElement(block))

public inline fun ElementHolder.markerElement(block: MarkerElement.() -> Unit): MarkerElement =
    addElement(jp.fishmans.moire.elements.markerElement(block))

public inline fun ElementHolder.mobAnchorElement(block: MobAnchorElement.() -> Unit): MobAnchorElement =
    addElement(jp.fishmans.moire.elements.mobAnchorElement(block))

public inline fun ElementHolder.textDisplayElement(block: TextDisplayElement.() -> Unit): TextDisplayElement =
    addElement(jp.fishmans.moire.elements.textDisplayElement(block))

public class StartWatchingScope @PublishedApi internal constructor(
    public val networkHandler: ServerPlayNetworkHandler
)

public val StartWatchingScope.player: ServerPlayerEntity
    get() = networkHandler.player

public inline fun ElementHolder.onStartWatching(crossinline block: StartWatchingScope.() -> Unit): Unit =
    (this as ElementHolderExtensions).`moire$addStartWatchingListener` { StartWatchingScope(it).block() }

public class SetAttachmentScope @PublishedApi internal constructor(
    public val attachment: HolderAttachment?
)

public inline fun ElementHolder.onSetAttachment(crossinline block: SetAttachmentScope.() -> Unit): Unit =
    (this as ElementHolderExtensions).`moire$addSetAttachmentListener` { SetAttachmentScope(it).block() }

public class EntityRemoveScope @PublishedApi internal constructor(
    public val entity: Entity,
    public val reason: Entity.RemovalReason
)

public inline fun ElementHolder.onEntityRemove(crossinline block: EntityRemoveScope.() -> Unit): Unit =
    (this as ElementHolderExtensions).`moire$addEntityRemoveListener` { entity, reason ->
        EntityRemoveScope(entity, reason).block()
    }

public class TickScope @PublishedApi internal constructor() {
    public var isCancelled: Boolean = false
        private set

    public var index: Int = 0
        private set

    public fun cancel() {
        isCancelled = true
    }

    @PublishedApi
    internal fun incrementIndex() {
        index++
    }
}

public val TickScope.isFirst: Boolean
    get() = index == 0

public val TickScope.ordinal: Int
    get() = index + 1

public inline fun ElementHolder.onTick(
    dependent: VirtualElement? = null,
    crossinline block: TickScope.() -> Unit
): TickScope {
    val scope = TickScope()
    (this as ElementHolderExtensions).`moire$addTickListener` {
        if (dependent != null && dependent !in elements) {
            false
        } else {
            scope.block()
            scope.incrementIndex()
            !scope.isCancelled
        }
    }

    return scope
}

public fun ElementHolder.startRiding(entity: Entity): Unit =
    VirtualEntityUtils.addVirtualPassenger(entity, *entityIds.toIntArray())

public fun ElementHolder.chunkAttachment(world: ServerWorld, pos: Vec3d): HolderAttachment =
    ChunkAttachment.of(this, world, pos)

public fun ElementHolder.chunkAttachmentTicking(world: ServerWorld, pos: Vec3d): HolderAttachment =
    ChunkAttachment.ofTicking(this, world, pos)

public fun ElementHolder.entityAttachment(entity: Entity): EntityAttachment =
    EntityAttachment.of(this, entity)

public fun ElementHolder.entityAttachmentTicking(entity: Entity): EntityAttachment =
    EntityAttachment.ofTicking(this, entity)