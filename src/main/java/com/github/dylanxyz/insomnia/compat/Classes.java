package com.github.dylanxyz.insomnia.compat;

import com.misanthropy.linggango.class_enhancement.ClassEnhancement;

import javax.annotation.Nullable;

public class Classes
{
    @Nullable
    public static String getAvatarForClass(ClassEnhancement.PlayerClass playerClass) {
        if (playerClass.commands.isEmpty())
            return null;

        final String command = playerClass.commands.get(0);

        if (command.startsWith("insomnia")) {
            String[] args = command.split(" ");
            if (args.length > 0) return args[args.length - 1];
        }

        return null;
    }
}
