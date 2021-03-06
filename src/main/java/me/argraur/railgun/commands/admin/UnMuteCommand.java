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

package me.argraur.railgun.commands.admin;

import java.awt.Color;

import me.argraur.railgun.RailgunBot;
import me.argraur.railgun.interfaces.RailgunOrder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

public class UnMuteCommand implements RailgunOrder {
    private String unMuteCommand = "unmute";
    private String usage = unMuteCommand + " <@user>";
    private String description = "Unmutes given user";

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
        return unMuteCommand;
    }
    
    @Override
    public void call(Message message) {
        RailgunBot.muteHandler.unMute(message);
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.PINK);
        embedBuilder.setTitle("You can speak again!");
        embedBuilder.setDescription(message.getAuthor().getAsMention() + " unmuted " + message.getMentionedUsers().get(0).getAsMention() + "!");
        message.getChannel().sendMessage(embedBuilder.build()).queue();
    }
}