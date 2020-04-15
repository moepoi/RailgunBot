/*
 * Copyright (C) 2020 Arseniy Graur
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.argraur.railgun.commands;

import static me.argraur.railgun.RailgunBot.ignoreHelper;
import me.argraur.railgun.interfaces.RailgunOrder;

import net.dv8tion.jda.api.entities.Message;

public class IgnoreCommand implements RailgunOrder {
    private String ignoreCommand = "ignore";
    public static String senpaiId = "356723086009171969";
    public Message msg;
    private String usage = ignoreCommand + " <@user>";
    private String description = "Disallow `<@user>` to use the bot";

    @Override
    public String getUsage() {
        return usage;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void call(Message message) {
        if (message.getAuthor().getId().equals(senpaiId) && !message.getMentionedUsers().get(0).getId().equals(senpaiId)) {
            ignoreHelper.addIgnored(message.getMentionedUsers().get(0).getId(), message);
        }
    }

    @Override
    public String getCommand() {
        return ignoreCommand;
    }
}