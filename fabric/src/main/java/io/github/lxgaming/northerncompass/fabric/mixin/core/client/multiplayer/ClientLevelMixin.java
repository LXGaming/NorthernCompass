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

package io.github.lxgaming.northerncompass.fabric.mixin.core.client.multiplayer;

import io.github.lxgaming.northerncompass.fabric.event.LoadLevelCallback;
import net.minecraft.client.multiplayer.MultiPlayerLevel;
import net.minecraft.world.level.LevelAccessor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MultiPlayerLevel.class)
public abstract class ClientLevelMixin {

    @Inject(
            method = "<init>",
            at = @At(
                    value = "RETURN"
            )
    )
    private void onInit(CallbackInfo callbackInfo) {
        LoadLevelCallback.EVENT.invoker().onLoadLevel((LevelAccessor) this);
    }
}