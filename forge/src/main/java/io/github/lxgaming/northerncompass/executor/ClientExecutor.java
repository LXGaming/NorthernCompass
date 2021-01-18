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

package io.github.lxgaming.northerncompass.executor;

import io.github.lxgaming.northerncompass.item.property.AngleCompassProperty;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.item.Items;

public class ClientExecutor {
    
    public static void onRegisterItem() {
        // getPropertyGetter
        IItemPropertyGetter angleCompassProperty = ItemModelsProperties.func_239417_a_(Items.COMPASS, AngleCompassProperty.RESOURCE_LOCATION);
        ItemModelsProperties.registerProperty(Items.COMPASS, AngleCompassProperty.RESOURCE_LOCATION, new AngleCompassProperty(angleCompassProperty));
    }
}