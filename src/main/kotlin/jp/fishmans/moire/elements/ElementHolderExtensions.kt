package jp.fishmans.moire.elements

import eu.pb4.polymer.virtualentity.api.ElementHolder
import eu.pb4.polymer.virtualentity.api.VirtualEntityUtils
import eu.pb4.polymer.virtualentity.api.attachment.ChunkAttachment
import eu.pb4.polymer.virtualentity.api.attachment.EntityAttachment
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment
import eu.pb4.polymer.virtualentity.api.elements.*
import jp.fishmans.moire.element.ElementHolderExtensions
import jp.fishmans.moire.element.listener.*
import jp.fishmans.moire.element.listener.entity.EntityPostRemoveListener
import jp.fishmans.moire.element.listener.entity.EntityPreRemoveListener
import net.minecraft.entity.Entity
import net.minecraft.server.network.ServerPlayNetworkHandler
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d

public val ElementHolder.blockDisplayElements: List<BlockDisplayElement>
    get() = elements.filterIsInstance<BlockDisplayElement>()

public val ElementHolder.displayElements: List<DisplayElement>
    get() = elements.filterIsInstance<DisplayElement>()

public val ElementHolder.entityElements: List<EntityElement<*>>
    get() = elements.filterIsInstance<EntityElement<*>>()

public val ElementHolder.genericEntityElements: List<GenericEntityElement>
    get() = elements.filterIsInstance<GenericEntityElement>()

public val ElementHolder.interactionElements: List<InteractionElement>
    get() = elements.filterIsInstance<InteractionElement>()

public val ElementHolder.itemDisplayElements: List<ItemDisplayElement>
    get() = elements.filterIsInstance<ItemDisplayElement>()

public val ElementHolder.markerElements: List<MarkerElement>
    get() = elements.filterIsInstance<MarkerElement>()

public val ElementHolder.mobAnchorElements: List<MobAnchorElement>
    get() = elements.filterIsInstance<MobAnchorElement>()

public val ElementHolder.simpleEntityElements: List<SimpleEntityElement>
    get() = elements.filterIsInstance<SimpleEntityElement>()

public val ElementHolder.textDisplayElements: List<TextDisplayElement>
    get() = elements.filterIsInstance<TextDisplayElement>()

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

public fun ElementHolder.blockDisplayElements(block: BlockDisplayElement.() -> Unit): Unit =
    blockDisplayElements.forEach(block)

public fun ElementHolder.displayElements(block: DisplayElement.() -> Unit): Unit =
    displayElements.forEach(block)

public fun ElementHolder.elements(block: VirtualElement.() -> Unit): Unit =
    elements.forEach(block)

public fun ElementHolder.entityElements(block: EntityElement<*>.() -> Unit): Unit =
    entityElements.forEach(block)

public fun ElementHolder.genericEntityElements(block: GenericEntityElement.() -> Unit): Unit =
    genericEntityElements.forEach(block)

public fun ElementHolder.interactionElements(block: InteractionElement.() -> Unit): Unit =
    interactionElements.forEach(block)

public fun ElementHolder.itemDisplayElements(block: ItemDisplayElement.() -> Unit): Unit =
    itemDisplayElements.forEach(block)

public fun ElementHolder.markerElements(block: MarkerElement.() -> Unit): Unit =
    markerElements.forEach(block)

public fun ElementHolder.mobAnchorElements(block: MobAnchorElement.() -> Unit): Unit =
    mobAnchorElements.forEach(block)

public fun ElementHolder.simpleEntityElements(block: SimpleEntityElement.() -> Unit): Unit =
    simpleEntityElements.forEach(block)

public fun ElementHolder.textDisplayElements(block: TextDisplayElement.() -> Unit): Unit =
    textDisplayElements.forEach(block)

public fun ElementHolder.addListener(listener: Listener): Unit =
    (this as ElementHolderExtensions).`moire$addListener`(listener)

public fun ElementHolder.removeListener(listener: Listener): Unit =
    (this as ElementHolderExtensions).`moire$removeListener`(listener)

public fun <T : Listener> ElementHolder.subscribe(listener: T): T =
    listener.also { addListener(it) }

public fun <T : Listener> ElementHolder.unsubscribe(listener: T): T =
    listener.also { removeListener(it) }

public class EntityRemoveScope @PublishedApi internal constructor(
    public val entity: Entity,
    public val reason: Entity.RemovalReason
)

public inline fun ElementHolder.onEntityPreRemove(crossinline block: EntityRemoveScope.() -> Unit): EntityPreRemoveListener =
    subscribe(EntityPreRemoveListener { entity, reason -> EntityRemoveScope(entity, reason).block() })

public inline fun ElementHolder.onEntityPostRemove(crossinline block: EntityRemoveScope.() -> Unit): EntityPostRemoveListener =
    subscribe(EntityPostRemoveListener { entity, reason -> EntityRemoveScope(entity, reason).block() })

public class AttachmentRemovedScope @PublishedApi internal constructor(
    public val oldAttachment: HolderAttachment
)

public inline fun ElementHolder.onAttachmentRemoved(crossinline block: AttachmentRemovedScope.() -> Unit): AttachmentRemovedListener =
    subscribe(AttachmentRemovedListener { oldAttachment ->
        AttachmentRemovedScope(oldAttachment).block()
    })

public class AttachmentSetScope @PublishedApi internal constructor(
    public val attachment: HolderAttachment,
    public val oldAttachment: HolderAttachment?
)

public inline fun ElementHolder.onAttachmentSet(crossinline block: AttachmentSetScope.() -> Unit): AttachmentSetListener =
    subscribe(AttachmentSetListener { attachment, oldAttachment ->
        AttachmentSetScope(attachment, oldAttachment).block()
    })

public class WatchingScope @PublishedApi internal constructor(
    public val networkHandler: ServerPlayNetworkHandler
) {
    public val player: ServerPlayerEntity
        get() = networkHandler.player
}

public inline fun ElementHolder.onWatchingStarted(crossinline block: WatchingScope.() -> Unit): WatchingStartedListener =
    subscribe(WatchingStartedListener { networkHandler -> WatchingScope(networkHandler).block() })

public inline fun ElementHolder.onWatchingStopped(crossinline block: WatchingScope.() -> Unit): WatchingStoppedListener =
    subscribe(WatchingStoppedListener { networkHandler -> WatchingScope(networkHandler).block() })

public class TickScope @PublishedApi internal constructor(
    public val index: Int,
    private val cancelAction: () -> Unit
) {
    public val isFirst: Boolean
        get() = index == 0

    public val ordinal: Int
        get() = index + 1

    public fun cancel(): Unit = cancelAction()
}

public inline fun ElementHolder.onPreTick(crossinline block: TickScope.() -> Unit): PreTickListener {
    var index = 0
    return subscribe(
        object : PreTickListener {
            override fun onPreTick() = TickScope(index++) { removeListener(this) }.block()
        }
    )
}

public inline fun ElementHolder.onPostTick(crossinline block: TickScope.() -> Unit): PostTickListener {
    var index = 0
    return subscribe(
        object : PostTickListener {
            override fun onPostTick() = TickScope(index++) { removeListener(this) }.block()
        }
    )
}

public inline fun ElementHolder.onTick(crossinline block: TickScope.() -> Unit): PreTickListener =
    onPreTick(block)

public fun ElementHolder.startRiding(entity: Entity): Unit =
    VirtualEntityUtils.addVirtualPassenger(entity, *entityIds.toIntArray())

public fun ElementHolder.chunkAttachment(
    world: ServerWorld,
    pos: Vec3d,
    isTicking: Boolean = false
): HolderAttachment =
    if (isTicking) ChunkAttachment.ofTicking(this, world, pos) else ChunkAttachment.of(this, world, pos)

public fun ElementHolder.chunkAttachment(
    world: ServerWorld,
    pos: BlockPos,
    isTicking: Boolean = false
): HolderAttachment =
    if (isTicking) ChunkAttachment.ofTicking(this, world, pos) else ChunkAttachment.of(this, world, pos)

public fun ElementHolder.entityAttachment(
    entity: Entity,
    isTicking: Boolean = false
): EntityAttachment =
    if (isTicking) EntityAttachment.ofTicking(this, entity) else EntityAttachment.of(this, entity)