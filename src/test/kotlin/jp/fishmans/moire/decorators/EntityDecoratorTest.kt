package jp.fishmans.moire.decorators

import jp.fishmans.moire.elements.*
import jp.fishmans.moire.matrices.rotateLocalYDegrees
import net.fabricmc.api.ModInitializer
import net.minecraft.entity.EntityType
import net.minecraft.entity.mob.CreeperEntity
import net.minecraft.item.Items

class EntityDecoratorTest : ModInitializer {
    private class TestEntityDecorator(entity: CreeperEntity) : EntityDecorator<CreeperEntity>(entity) {
        override fun getElementHolder() = elementHolder {
            itemDisplayElement {
                transformation {
                    scaleLocal(0.5f)
                    translateLocal(0.0f, 0.5f, 0.0f)
                }

                onTick {
                    transform {
                        rotateLocalYDegrees(11.25f)
                    }

                    startInterpolation(1)
                }

                item = Items.TNT.defaultStack
                ignorePositionUpdates()
            }

            startRiding(entity)
        }
    }

    override fun onInitialize() {
        EntityDecorators.register(EntityType.CREEPER, ::TestEntityDecorator)
    }
}