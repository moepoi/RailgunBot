/*
 * Copyright (C) 2018 The Pixel3ROM Project
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

package me.argraur.railgun.commands.admin;

import static me.argraur.railgun.RailgunBot.giphyHelper;

import me.argraur.railgun.interfaces.RailgunOrder;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;

public class KickCommand implements RailgunOrder {
    private String kickCommand = "kick";
    private String usage = kickCommand + " <@user>";
    private String description = "Kick `<@user>` from server";

    @Override
    public String getUsage() {
        return usage;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getCommand() {
        return kickCommand;
    }

    @Override
    public void call(Message message) {
        if (message.getMember().hasPermission(Permission.KICK_MEMBERS)) {
            message.getGuild().kick(message.getMentionedMembers().get(0)).queue();
            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setTitle("KICK!");
            embedBuilder.setDescription("Sayounara " + message.getContentRaw().split(" ")[1] + "!");
            embedBuilder.setImage(giphyHelper.searchRandomGif("kick"));
            message.getChannel().sendMessage(embedBuilder.build()).queue();
        }
    }
}