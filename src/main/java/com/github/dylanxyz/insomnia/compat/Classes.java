package com.github.dylanxyz.insomnia.compat;

import com.misanthropy.linggango.class_enhancement.ClassEnhancement;

import javax.annotation.Nullable;

public class Classes
{
    @Nullable
    public static String getAvatarForClass(ClassEnhancement.PlayerClass playerClass) {
        final String classId = playerClass.id;

        if (classId.startsWith("@")) {
            return classId.substring(1);
        }

        return null;
    }
}
