# moire

**moire** is a Kotlin library providing extension functions and a DSL
for [polymer-virtual-entity](https://github.com/Patbox/polymer). With moire, you can easily create and manage
`ElementHolder` instance.

> **Note:** The API is subject to change without backward compatibility until version 1.0.0. Please be aware of
> potential changes.

## Gradle Setup

### Groovy

```groovy
repositories {
    maven { url 'https://maven.nucleoid.xyz/' }
    maven { url 'https://api.modrinth.com/maven' }
}

dependencies {
    modImplementation 'eu.pb4:polymer-virtual-entity:POLYMER_VERSION'
    modImplementation 'maven.modrinth:moire:MOIRE_VERSION'
}
```

### Kotlin DSL

```kotlin
repositories {
    maven("https://maven.nucleoid.xyz/")
    maven("https://api.modrinth.com/maven")
}

dependencies {
    modImplementation("eu.pb4:polymer-virtual-entity:POLYMER_VERSION")
    modImplementation("maven.modrinth:moire:MOIRE_VERSION")
}
```

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