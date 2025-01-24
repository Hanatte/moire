package jp.fishmans.moire.event;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.server.world.ServerWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class EntityPostLoadCallbackTest implements ModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger("EntityPostLoadCallbackTest");

    @Override
    public void onInitialize() {
        EntityPostLoadCallback.EVENT.register(new EntityPostLoadCallback<CreeperEntity>() {
            @Override
            public Class<CreeperEntity> getEntityClass() {
                return CreeperEntity.class;
            }

            @Override
            public void onEntityPostLoad(CreeperEntity entity, ServerWorld world) {
                LOGGER.info("Entity loaded: {}", entity);
            }
        });
    }
}