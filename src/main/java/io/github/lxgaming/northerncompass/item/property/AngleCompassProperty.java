/*
 * Copyright 2019 Alex Thomson
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

package io.github.lxgaming.northerncompass.item.property;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

public class AngleCompassProperty implements IItemPropertyGetter {
    
    public static final ResourceLocation RESOURCE_LOCATION = new ResourceLocation("angle");
    
    @OnlyIn(Dist.CLIENT)
    @Override
    public float call(ItemStack itemStack, @Nullable World world, @Nullable LivingEntity entity) {
        if (entity == null && !itemStack.isOnItemFrame()) {
            return 0.0F;
        }
        
        Entity currentEntity;
        if (entity != null) {
            currentEntity = entity;
        } else {
            currentEntity = itemStack.getItemFrame();
        }
        
        if (currentEntity == null) {
            return 0.0F;
        }
        
        World currentWorld;
        if (world != null) {
            currentWorld = world;
        } else {
            currentWorld = currentEntity.world;
        }
        
        if (currentWorld == null) {
            return 0.0F;
        }
        
        double rotation;
        if (currentEntity instanceof ItemFrameEntity) {
            rotation = getRotation((ItemFrameEntity) currentEntity);
        } else {
            rotation = getRotation(currentEntity);
        }
        
        double angle = 360.0D - rotation;
        double modulo = MathHelper.positiveModulo(angle / 360.0D, 1.0D);
        return (float) modulo;
    }
    
    @OnlyIn(Dist.CLIENT)
    private double getRotation(ItemFrameEntity itemFrameEntity) {
        Direction direction = itemFrameEntity.getHorizontalFacing();
        double entityRotation;
        if (direction == Direction.UP) {
            entityRotation = 0.0D;
        } else if (direction == Direction.DOWN) {
            entityRotation = 180.0D;
        } else {
            entityRotation = direction.getHorizontalIndex() * 90;
        }
        
        int itemRotation = itemFrameEntity.getRotation() * 45;
        return (entityRotation + itemRotation) % 360.0D;
    }
    
    @OnlyIn(Dist.CLIENT)
    private double getRotation(Entity entity) {
        return (entity.rotationYaw + 180.0D) % 360.0D;
    }
}