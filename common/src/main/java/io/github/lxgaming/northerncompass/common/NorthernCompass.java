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

package io.github.lxgaming.northerncompass.common;

import io.github.lxgaming.northerncompass.common.configuration.Configuration;
import io.github.lxgaming.northerncompass.common.util.BuildParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;

public abstract class NorthernCompass {

    public static final String ID = "northerncompass";
    public static final String NAME = "NorthernCompass";
    public static final String VERSION = BuildParameters.VERSION;

    private static NorthernCompass instance;
    private final Logger logger;
    private final Configuration configuration;

    public NorthernCompass(Path path) {
        instance = this;
        this.logger = LoggerFactory.getLogger(NorthernCompass.NAME);
        this.configuration = new Configuration(path);
    }

    public void load() {
        if (!getConfiguration().reloadConfiguration()) {
            return;
        }
    }

    public static NorthernCompass getInstance() {
        return instance;
    }

    public Logger getLogger() {
        return logger;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}