package jp.fishmans.moire.decorators.entities

import jp.fishmans.moire.decorators.DecoratorRegistries
import jp.fishmans.moire.elements.*
import jp.fishmans.moire.matrices.rotateLocalYDegrees
import net.fabricmc.api.ModInitializer
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.item.Items

class EntityLoadDecoratorTest : ModInitializer {
    private class TestEntityLoadDecorator(override val entityType: EntityType<out Entity>) :
        EntityLoadDecorator<Entity>
    {
        override fun decorate(context: EntityLoadDecoratorContext<out Entity>) {
            elementHolder {
                itemDisplayElement {
                    transformation {
                        scaleLocal(0.5f)
                        translateLocal(1.0f, 1.0f, 0.0f)
                    }

                    onTick {
                        transform {
                            rotateLocalYDegrees(11.25f)
                        }

                        startInterpolation(1)
                    }

                    item = Items.TNT.defaultStack
                    teleportDuration = 1
                }

                entityAttachment(context.entity, isTicking = true)
            }
        }
    }

    override fun onInitialize() {
        DecoratorRegistries.ENTITY_LOAD.register(TestEntityLoadDecorator(EntityType.CREEPER))
    }
}