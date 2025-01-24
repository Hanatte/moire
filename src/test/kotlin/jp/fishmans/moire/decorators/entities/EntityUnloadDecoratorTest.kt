package jp.fishmans.moire.decorators.entities

import jp.fishmans.moire.decorators.DecoratorRegistries
import jp.fishmans.moire.elements.*
import net.fabricmc.api.ModInitializer
import net.minecraft.entity.decoration.DisplayEntity
import net.minecraft.entity.mob.CreeperEntity
import net.minecraft.text.Text

class EntityUnloadDecoratorTest : ModInitializer {
    private class TestEntityUnloadDecorator : EntityUnloadDecorator<CreeperEntity>(CreeperEntity::class) {
        override fun decorate(context: EntityUnloadDecoratorContext<CreeperEntity>) {
            elementHolder {
                textDisplayElement {
                    transformation {
                        scaleLocal(2.0f)
                        translateLocal(0.0f, 1.0f, 0.0f)
                    }

                    onTick {
                        textOpacity = (255 * (1 - index / 10.0f)).toInt().toByte()
                    }

                    billboardMode = DisplayEntity.BillboardMode.CENTER
                    text = Text.literal("UNLOAD")
                }

                onTick {
                    if (index >= 10) {
                        destroy()
                    }
                }

                chunkAttachment(context.world, context.entity.pos, isTicking = true)
            }
        }
    }

    override fun onInitialize() {
        DecoratorRegistries.ENTITY_UNLOAD.register(TestEntityUnloadDecorator())
    }
}