package jp.fishmans.moire.elements

import eu.pb4.polymer.virtualentity.api.VirtualEntityUtils
import eu.pb4.polymer.virtualentity.api.elements.*
import jp.fishmans.moire.matrices.matrix4f
import net.minecraft.entity.Entity
import org.joml.*
import java.nio.FloatBuffer

public val DisplayElement.transformation: Matrix4fc
    get() = matrix4f {
        translation(translation)
        rotate(leftRotation)
        scale(scale)
        rotate(rightRotation)
    }

public inline fun blockDisplayElement(block: BlockDisplayElement.() -> Unit): BlockDisplayElement =
    BlockDisplayElement().apply(block)

public inline fun interactionElement(block: InteractionElement.() -> Unit): InteractionElement =
    InteractionElement().apply(block)

public inline fun itemDisplayElement(block: ItemDisplayElement.() -> Unit): ItemDisplayElement =
    ItemDisplayElement().apply(block)

public inline fun markerElement(block: MarkerElement.() -> Unit): MarkerElement =
    MarkerElement().apply(block)

public inline fun mobAnchorElement(block: MobAnchorElement.() -> Unit): MobAnchorElement =
    MobAnchorElement().apply(block)

public inline fun textDisplayElement(block: TextDisplayElement.() -> Unit): TextDisplayElement =
    TextDisplayElement().apply(block)

public fun VirtualElement.remove(): Boolean = holder?.removeElement(this) != null

public fun VirtualElement.removeWithoutUpdates(): Boolean = holder?.removeElementWithoutUpdates(this) != null

public fun VirtualElement.startRiding(entity: Entity): Unit =
    VirtualEntityUtils.addVirtualPassenger(entity, *entityIds.toIntArray())

public inline fun DisplayElement.transformation(block: Matrix4f.() -> Unit): Unit = setTransformation(matrix4f(block))

public inline fun DisplayElement.transformation(from: Matrix3fc, block: Matrix4f.() -> Unit): Unit =
    setTransformation(matrix4f(from, block))

public inline fun DisplayElement.transformation(from: Matrix4fc, block: Matrix4f.() -> Unit): Unit =
    setTransformation(matrix4f(from, block))

public inline fun DisplayElement.transformation(from: Matrix4x3fc, block: Matrix4f.() -> Unit): Unit =
    setTransformation(matrix4f(from, block))

public inline fun DisplayElement.transformation(from: Matrix4dc, block: Matrix4f.() -> Unit): Unit =
    setTransformation(matrix4f(from, block))

public inline fun DisplayElement.transformation(from: FloatBuffer, block: Matrix4f.() -> Unit): Unit =
    setTransformation(matrix4f(from, block))

public inline fun DisplayElement.transform(block: Matrix4f.() -> Unit): Unit = transformation(transformation, block)

public fun DisplayElement.startInterpolation(duration: Int) {
    interpolationDuration = duration
    startInterpolation()
}

public fun DisplayElement.startInterpolationIfDirty(duration: Int) {
    if (isTransformationDirty) startInterpolation(duration)
}