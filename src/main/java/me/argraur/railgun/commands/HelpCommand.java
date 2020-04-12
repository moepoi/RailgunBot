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

package me.argraur.railgun.commands;

import me.argraur.railgun.RailgunBot;
import me.argraur.railgun.interfaces.RailgunOrder;
import net.dv8tion.jda.api.entities.MessageChannel;

public class HelpCommand implements RailgunOrder {
    private RailgunOrder[] commands;
    private String helpCommand = RailgunBot.COMMAND_PREFIX + "help";
    private String helpHelp = helpCommand + " - returns bot help";
    private MessageChannel messageChannel;

    public HelpCommand(MessageChannel messageChannel,
                       RailgunOrder[] commands) {
        this.messageChannel = messageChannel;
        this.commands = commands;
    }

    @Override
    public void call(String args) {
        messageChannel.sendMessage(getOutput(args)).queue();
    }

    @Override
    public String getOutput(String args) {
        StringBuilder output = new StringBuilder();
        output.append("\nBot help: \n```");
        for (RailgunOrder command : commands) {
            output.append(command.getHelp());
            output.append("\n");
        }
        output.append("```");
        return output.toString();
    }

    @Override
    public String getCommand() {
        return ">help";
    }

    @Override
    public StringBuilder getHelp() {
        StringBuilder sbHelp = new StringBuilder();
        sbHelp.append(helpHelp);
        return sbHelp;
    }
}
