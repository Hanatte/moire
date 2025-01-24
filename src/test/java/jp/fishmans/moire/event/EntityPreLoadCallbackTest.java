package jp.fishmans.moire.event;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.server.world.ServerWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class EntityPreLoadCallbackTest implements ModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger("EntityPreLoadCallbackTest");

    @Override
    public void onInitialize() {
        EntityPreLoadCallback.EVENT.register(new EntityPreLoadCallback<CreeperEntity>() {
            @Override
            public Class<CreeperEntity> getEntityClass() {
                return CreeperEntity.class;
            }

            @Override
            public void onEntityPreLoad(CreeperEntity entity, ServerWorld world) {
                LOGGER.info("Loading entity: {}", entity);
            }
        });
    }
}