/*
 * Copyright 2024 Alex Thomson
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

package io.github.lxgaming.northerncompass.neoforge;

import io.github.lxgaming.northerncompass.common.NorthernCompass;
import io.github.lxgaming.northerncompass.common.listener.LevelListener;
import io.github.lxgaming.northerncompass.neoforge.executor.ClientExecutor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.fml.loading.progress.StartupNotificationManager;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.level.LevelEvent;

@Mod(value = NorthernCompass.ID)
public class NeoForgeMod extends NorthernCompass {

    public NeoForgeMod(IEventBus eventBus) {
        super(FMLPaths.CONFIGDIR.get().resolve(NorthernCompass.ID));

        eventBus.register(this);
        NeoForge.EVENT_BUS.addListener((LevelEvent.Load event) -> LevelListener.onLoadLevel(event.getLevel()));

        StartupNotificationManager.addModMessage(String.format("%s v%s Initialized", NorthernCompass.NAME, NorthernCompass.VERSION));
        getLogger().info("{} v{} Initialized", NorthernCompass.NAME, NorthernCompass.VERSION);
    }

    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent event) {
        load();

        event.enqueueWork(ClientExecutor::onRegisterItem);
    }
}