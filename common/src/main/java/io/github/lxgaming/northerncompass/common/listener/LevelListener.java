/*
 * Copyright 2023 Alex Thomson
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

package io.github.lxgaming.northerncompass.common.listener;

import io.github.lxgaming.northerncompass.common.NorthernCompass;
import io.github.lxgaming.northerncompass.common.configuration.Config;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelAccessor;

public class LevelListener {

    public static void onLoadLevel(LevelAccessor level) {
        Config config = NorthernCompass.getInstance().getConfiguration().getConfig();
        if (config == null) {
            return;
        }

        boolean save = false;
        for (ResourceLocation resourceLocation : Registry.DIMENSION_TYPE.keySet()) {
            if (config.getDimensionTypes().containsKey(resourceLocation.toString())) {
                continue;
            }

            config.getDimensionTypes().put(resourceLocation.toString(), null);
            save = true;
        }

        if (save) {
            NorthernCompass.getInstance().getConfiguration().saveConfiguration();
        }
    }
}