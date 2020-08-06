/*
 * Copyright 2020 Alex Thomson
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

import net.minecraft.client.item.ModelPredicateProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.item.CompassItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class AngleCompassProperty implements ModelPredicateProvider {
    
    public static final Identifier IDENTIFIER = new Identifier("angle");
    private final ModelPredicateProvider angleCompassProperty;
    
    public AngleCompassProperty(ModelPredicateProvider angleCompassProperty) {
        this.angleCompassProperty = angleCompassProperty;
    }
    
    @Override
    public float call(ItemStack itemStack, ClientWorld world, LivingEntity entity) {
        // hasLodestoneData
        if (CompassItem.hasLodestone(itemStack)) {
            return angleCompassProperty.call(itemStack, world, entity);
        }
        
        if (entity == null && !itemStack.isInFrame()) {
            return 0.0F;
        }
        
        Entity currentEntity;
        if (entity != null) {
            currentEntity = entity;
        } else {
            currentEntity = itemStack.getFrame();
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
        double modulo = MathHelper.floorMod(angle / 360.0D, 1.0D);
        return (float) modulo;
    }
    
    private double getRotation(ItemFrameEntity itemFrameEntity) {
        Direction direction = itemFrameEntity.getHorizontalFacing();
        double entityRotation;
        if (direction == Direction.UP) {
            entityRotation = 0.0D;
        } else if (direction == Direction.DOWN) {
            entityRotation = 180.0D;
        } else {
            entityRotation = direction.getHorizontal() * 90;
        }
        
        int itemRotation = itemFrameEntity.getRotation() * 45;
        return (entityRotation + itemRotation) % 360.0D;
    }
    
    private double getRotation(Entity entity) {
        return (entity.yaw + 180.0D) % 360.0D;
    }
}