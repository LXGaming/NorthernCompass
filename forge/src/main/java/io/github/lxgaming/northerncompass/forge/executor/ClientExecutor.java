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

package io.github.lxgaming.northerncompass.forge.executor;

import io.github.lxgaming.northerncompass.common.client.renderer.item.AngleCompassProperty;
import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.item.Items;

public class ClientExecutor {
    
    public static void onRegisterItem() {
        ClampedItemPropertyFunction angleCompassProperty = (ClampedItemPropertyFunction) ItemProperties.getProperty(Items.COMPASS, AngleCompassProperty.RESOURCE_LOCATION);
        ItemProperties.register(Items.COMPASS, AngleCompassProperty.RESOURCE_LOCATION, new AngleCompassProperty(angleCompassProperty));
    }
}