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

package me.argraur.railgun.commands.fun;

import me.argraur.railgun.RailgunBot;

import me.argraur.railgun.interfaces.RailgunOrder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.awt.Color;

public class GifCommand implements RailgunOrder {
    private String gifCommand = "gif";
    private String usage = gifCommand + " <query> [<@user>]";
    private String description = "Send GIF for a given query, address to `<@user>` if mentioned";

    @Override
    public String getCommand() {
        return gifCommand;
    }

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
        String temp = message.getContentRaw().replaceAll(gifCommand + " ", "");
        EmbedBuilder embedBuilder = new EmbedBuilder();
        String url = "";
        if (!message.getMentionedMembers().isEmpty()) {
            embedBuilder.setDescription("<@" + message.getMentionedMembers().get(0).getId() + ">");
            temp.replaceAll(" <@" + message.getMentionedMembers().get(0).getId() + ">", "");
        }
        try {
            url = RailgunBot.giphyHelper.searchRandomGif(temp);
            embedBuilder.setImage(url);
        } catch (IllegalStateException e) { return; }
        try {
            embedBuilder.setColor(Color.decode("#" + RailgunBot.colorHelper.getColor(url)));
        } catch (ArrayIndexOutOfBoundsException e) {
            embedBuilder.setColor(Color.PINK);
        }
        message.getChannel().sendMessage(embedBuilder.build()).queue();
    }
}
