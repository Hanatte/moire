package jp.fishmans.moire.matrices

import org.joml.*
import java.lang.Math
import java.nio.FloatBuffer

public inline fun matrix4f(block: Matrix4f.() -> Unit): Matrix4f = Matrix4f().apply(block)

public inline fun matrix4f(from: Matrix3fc, block: Matrix4f.() -> Unit): Matrix4f = Matrix4f(from).apply(block)

public inline fun matrix4f(from: Matrix4fc, block: Matrix4f.() -> Unit): Matrix4f = Matrix4f(from).apply(block)

public inline fun matrix4f(from: Matrix4x3fc, block: Matrix4f.() -> Unit): Matrix4f = Matrix4f(from).apply(block)

public inline fun matrix4f(from: Matrix4dc, block: Matrix4f.() -> Unit): Matrix4f = Matrix4f(from).apply(block)

public inline fun matrix4f(from: FloatBuffer, block: Matrix4f.() -> Unit): Matrix4f = Matrix4f(from).apply(block)

public fun Matrix4f.rotateLocalXDegrees(angle: Float): Matrix4f = rotateLocalX(angle.toRadians())

public fun Matrix4f.rotateLocalYDegrees(angle: Float): Matrix4f = rotateLocalY(angle.toRadians())

public fun Matrix4f.rotateLocalZDegrees(angle: Float): Matrix4f = rotateLocalZ(angle.toRadians())

private fun Float.toRadians() = Math.toRadians(toDouble()).toFloat()