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

package io.github.lxgaming.northerncompass;

import io.github.lxgaming.northerncompass.item.property.AngleCompassProperty;
import io.github.lxgaming.northerncompass.mixin.core.client.renderer.item.ItemPropertiesAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.Items;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NorthernCompass implements ClientModInitializer {
    
    public static final String ID = "northerncompass";
    public static final String NAME = "NorthernCompass";
    public static final String VERSION = "@version@";
    
    private static NorthernCompass instance;
    private final Logger logger;
    
    public NorthernCompass() {
        instance = this;
        this.logger = LogManager.getLogger(NorthernCompass.NAME);
    }
    
    @Override
    public void onInitializeClient() {
        ClampedItemPropertyFunction angleCompassProperty = (ClampedItemPropertyFunction) ItemProperties.getProperty(Items.COMPASS, AngleCompassProperty.RESOURCE_LOCATION);
        ItemPropertiesAccessor.accessor$register(Items.COMPASS, AngleCompassProperty.RESOURCE_LOCATION, new AngleCompassProperty(angleCompassProperty));
        getLogger().info("{} v{} Initialized", NorthernCompass.NAME, NorthernCompass.VERSION);
    }
    
    public static NorthernCompass getInstance() {
        return instance;
    }
    
    public Logger getLogger() {
        return logger;
    }
}