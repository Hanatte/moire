# moire

**moire** is a Kotlin library providing extension functions and a DSL
for [polymer-virtual-entity](https://github.com/Patbox/polymer). With moire, you can easily create and manage
`ElementHolder` instance.

## Usage

Here is a simple example.

```kotlin
import jp.fishmans.moire.elements.*
import jp.fishmans.moire.matrices.rotateLocalYDegrees
import net.minecraft.item.Items

fun create() = elementHolder {
    itemDisplayElement {
        transformation {
            scaleLocal(0.5f)
        }

        onTick {
            transform {
                rotateLocalYDegrees(11.25f)
            }

            startInterpolationIfDirty(1)
        }

        item = Items.TNT.defaultStack
        teleportDuration = 1
    }
}
```
