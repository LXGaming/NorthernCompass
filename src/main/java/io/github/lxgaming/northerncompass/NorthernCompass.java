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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLFingerprintViolationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = NorthernCompass.ID,
        name = NorthernCompass.NAME,
        version = NorthernCompass.VERSION,
        acceptedMinecraftVersions = NorthernCompass.ACCEPTED_VERSIONS,
        acceptableRemoteVersions = NorthernCompass.ACCEPTABLE_REMOTE_VERSIONS,
        certificateFingerprint = NorthernCompass.CERTIFICATE_FINGERPRINT
)
public class NorthernCompass {
    
    public static final String ID = "northerncompass";
    public static final String NAME = "NorthernCompass";
    public static final String VERSION = "${version}";
    public static final String ACCEPTED_VERSIONS = "[1.12.2]";
    public static final String ACCEPTABLE_REMOTE_VERSIONS = "*";
    public static final String CERTIFICATE_FINGERPRINT = "565fa4dbf20e7c3c4423950ca8e0bdabf7568796";
    
    private static NorthernCompass instance;
    private final Logger logger;
    
    public NorthernCompass() {
        instance = this;
        this.logger = LogManager.getLogger(NorthernCompass.NAME);
        
        MinecraftForge.EVENT_BUS.register(new RegistryListener());
        
        getLogger().info("{} v{} Initialized", NorthernCompass.NAME, NorthernCompass.VERSION);
    }
    
    @Mod.EventHandler
    public void fingerprintViolation(FMLFingerprintViolationEvent event) {
        getLogger().warn("Certificate Fingerprint Violation Detected!");
    }
    
    public static NorthernCompass getInstance() {
        return instance;
    }
    
    public Logger getLogger() {
        return logger;
    }
}