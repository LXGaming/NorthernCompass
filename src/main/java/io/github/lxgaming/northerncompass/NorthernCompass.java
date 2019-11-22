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

package io.github.lxgaming.northerncompass;

import io.github.lxgaming.northerncompass.listener.RegistryListener;
import net.minecraftforge.fml.StartupMessageManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(value = NorthernCompass.ID)
public class NorthernCompass {
    
    public static final String ID = "northerncompass";
    public static final String NAME = "NorthernCompass";
    public static final String VERSION = "${version}";
    
    private static NorthernCompass instance;
    private final Logger logger;
    
    public NorthernCompass() {
        instance = this;
        this.logger = LogManager.getLogger(NorthernCompass.NAME);
        
        FMLJavaModLoadingContext.get().getModEventBus().register(new RegistryListener());
        
        StartupMessageManager.addModMessage(String.format("%s v%s Initialized", NorthernCompass.NAME, NorthernCompass.VERSION));
        getLogger().info("{} v{} Initialized", NorthernCompass.NAME, NorthernCompass.VERSION);
    }
    
    public static NorthernCompass getInstance() {
        return instance;
    }
    
    public Logger getLogger() {
        return logger;
    }
}