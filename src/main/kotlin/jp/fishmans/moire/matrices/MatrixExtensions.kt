package jp.fishmans.moire.matrices

import org.joml.Matrix4f

public inline fun matrix4f(block: Matrix4f.() -> Unit): Matrix4f = Matrix4f().apply(block)

public fun Matrix4f.rotateLocalXDegrees(angle: Float): Matrix4f = rotateLocalX(angle.toRadians())

public fun Matrix4f.rotateLocalYDegrees(angle: Float): Matrix4f = rotateLocalY(angle.toRadians())

public fun Matrix4f.rotateLocalZDegrees(angle: Float): Matrix4f = rotateLocalZ(angle.toRadians())

private fun Float.toRadians() = Math.toRadians(toDouble()).toFloat()