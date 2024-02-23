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

package io.github.lxgaming.northerncompass.common.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Configuration {

    protected static final Gson GSON = new GsonBuilder()
            .disableHtmlEscaping()
            .enableComplexMapKeySerialization()
            .serializeNulls()
            .setPrettyPrinting()
            .create();

    protected final Logger logger;
    protected final Path configPath;
    protected Config config;

    public Configuration(Path path) {
        this.logger = LogManager.getLogger(Configuration.class);
        this.configPath = path.resolve("config.json");
    }

    public boolean loadConfiguration() {
        try {
            this.config = deserializeFile(configPath, Config.class);
            return true;
        } catch (IOException ex) {
            logger.error("Encountered an error while loading configuration", ex);
            return false;
        }
    }

    public boolean saveConfiguration() {
        try {
            serializeFile(configPath, config);
            return true;
        } catch (IOException ex) {
            logger.error("Encountered an error while saving configuration", ex);
            return false;
        }
    }

    public boolean reloadConfiguration() {
        return loadConfiguration() && saveConfiguration();
    }

    protected <T> T deserializeFile(Path path, Class<T> type) throws IOException {
        if (!Files.exists(path)) {
            T value;
            try {
                value = type.getConstructor().newInstance();
            } catch (Exception ex) {
                throw new RuntimeException("Encountered an error while instantiating " + type.getName(), ex);
            }

            serializeFile(path, value);
            return value;
        }

        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            return GSON.fromJson(reader, type);
        }
    }

    protected <T> void serializeFile(Path path, T value) throws IOException {
        Path parentPath = path.getParent();
        if (parentPath != null && !Files.exists(parentPath)) {
            Files.createDirectories(parentPath);
        }

        try (Writer writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
            GSON.toJson(value, writer);
        }
    }

    public @Nullable Config getConfig() {
        return config;
    }
}