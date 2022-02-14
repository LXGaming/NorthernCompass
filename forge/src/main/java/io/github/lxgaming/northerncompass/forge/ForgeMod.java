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

package io.github.lxgaming.northerncompass.forge;

import io.github.lxgaming.northerncompass.common.NorthernCompass;
import io.github.lxgaming.northerncompass.forge.executor.ClientExecutor;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.progress.StartupMessageManager;

@Mod(value = NorthernCompass.ID)
public class ForgeMod extends NorthernCompass {
    
    public ForgeMod() {
        super();
        
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
        
        StartupMessageManager.addModMessage(String.format("%s v%s Initialized", NorthernCompass.NAME, NorthernCompass.VERSION));
        getLogger().info("{} v{} Initialized", NorthernCompass.NAME, NorthernCompass.VERSION);
    }
    
    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(ClientExecutor::onRegisterItem);
    }
}