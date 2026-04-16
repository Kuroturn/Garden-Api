package com.garden.api.voicechat.command;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.garden.api.voicechat.Voicechat;
import com.garden.api.voicechat.voice.server.Group;
import com.garden.api.voicechat.voice.server.Server;
import net.minecraft.commands.CommandSourceStack;

import java.util.concurrent.CompletableFuture;

public class GroupNameSuggestionProvider implements SuggestionProvider<CommandSourceStack> {

    public static final GroupNameSuggestionProvider INSTANCE = new GroupNameSuggestionProvider();

    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
        Server server = Voicechat.SERVER.getServer();
        if (server == null) {
            return builder.buildFuture();
        }
        server.getGroupManager().getGroups().values().stream().map(Group::getName).distinct().map(s -> {
            if (s.contains(" ")) {
                return String.format("\"%s\"", s);
            }
            return s;
        }).forEach(builder::suggest);
        return builder.buildFuture();
    }
}
