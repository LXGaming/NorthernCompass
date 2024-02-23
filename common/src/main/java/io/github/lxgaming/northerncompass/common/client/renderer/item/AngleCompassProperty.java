/*
 * Copyright 2021 Alex Thomson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.lxgaming.northerncompass.common.client.renderer.item;

import io.github.lxgaming.northerncompass.common.NorthernCompass;
import io.github.lxgaming.northerncompass.common.configuration.Config;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.CompassItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import org.jetbrains.annotations.Nullable;

public class AngleCompassProperty implements ClampedItemPropertyFunction {

    public static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation("angle");
    private final ClampedItemPropertyFunction angleCompassProperty;

    public AngleCompassProperty(ClampedItemPropertyFunction angleCompassProperty) {
        this.angleCompassProperty = angleCompassProperty;
    }

    @Override
    public float unclampedCall(ItemStack itemStack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
        Entity currentEntity;
        if (entity != null) {
            currentEntity = entity;
        } else {
            currentEntity = itemStack.getEntityRepresentation();
        }

        Level currentLevel;
        if (level != null) {
            currentLevel = level;
        } else if (currentEntity != null) {
            currentLevel = currentEntity.level;
        } else {
            currentLevel = null;
        }

        if (angleCompassProperty != null && (CompassItem.isLodestoneCompass(itemStack) || !isDimensionAllowed(currentLevel))) {
            return angleCompassProperty.unclampedCall(itemStack, level, entity, seed);
        }

        if (currentEntity == null) {
            return 0.0F;
        }

        double rotation;
        if (currentEntity instanceof ItemFrame) {
            rotation = getRotation((ItemFrame) currentEntity);
        } else {
            rotation = getRotation(currentEntity);
        }

        double angle = 360.0D - rotation;
        double modulo = Mth.positiveModulo(angle / 360.0D, 1.0D);
        return (float) modulo;
    }

    private double getRotation(ItemFrame itemFrame) {
        Direction direction = itemFrame.getDirection();
        double entityRotation;
        if (direction == Direction.UP) {
            entityRotation = 0.0D;
        } else if (direction == Direction.DOWN) {
            entityRotation = 180.0D;
        } else {
            entityRotation = direction.get2DDataValue() * 90;
        }

        int itemRotation = itemFrame.getRotation() * 45;
        return (entityRotation + itemRotation) % 360.0D;
    }

    private double getRotation(Entity entity) {
        return (entity.getYRot() + 180.0D) % 360.0D;
    }

    private boolean isDimensionAllowed(@Nullable Level level) {
        Config config = NorthernCompass.getInstance().getConfiguration().getConfig();
        if (config == null) {
            return true;
        }

        if (level == null) {
            return config.isDimensionTypeFallback();
        }

        Registry<DimensionType> registry = level.registryAccess().registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY);
        ResourceLocation key = registry.getResourceKey(level.dimensionType()).map(ResourceKey::location).orElse(null);
        if (key == null) {
            return config.isDimensionTypeFallback();
        }

        Boolean value = config.getDimensionTypes().get(key.toString());
        return value != null ? value : config.isDimensionTypeFallback();
    }
}