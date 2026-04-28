package com.github.dylanxyz.insomnia.compat;

import com.github.dylanxyz.insomnia.Insomnia;
import org.figuramc.figura.FiguraMod;
import org.figuramc.figura.avatar.AvatarManager;
import org.figuramc.figura.avatar.local.LocalAvatarFetcher;
import org.figuramc.figura.backend2.NetworkStuff;
import org.figuramc.figura.gui.widgets.lists.AvatarList;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class FiguraUtils
{
    private static List<LocalAvatarFetcher.AvatarPath> AVATARS = List.of();

    public static Optional<LocalAvatarFetcher.AvatarPath> getAvatar(String avatarName) {
        for (var avatar : AVATARS) {
            String pathName = avatar.getPath().toString();

            if (avatarName.equals(pathName)) {
                return Optional.of(avatar);
            }
        }

        return Optional.empty();
    }

    public static void loadAvatars() {
        AVATARS = LocalAvatarFetcher.ALL_AVATARS.stream().flatMap(avatar -> {
            if (avatar instanceof LocalAvatarFetcher.FolderPath folderPath) {
                return folderPath.getChildren().stream();
            } else {
                return Stream.of(avatar);
            }
        }).toList();
    }

    public static void setAvatar(String avatarName) {
        setAvatar(avatarName, true);
    }

    public static void setAvatar(String avatarName, boolean upload) {
        if (AVATARS.isEmpty()) {
            LocalAvatarFetcher.reloadAvatars()
                .thenRun(FiguraUtils::loadAvatars)
                .thenRun(() -> setAvatar(avatarName, upload));
            return;
        }

        final Optional<LocalAvatarFetcher.AvatarPath> avatar = getAvatar(avatarName);

        if (avatar.isPresent()) {
            AvatarManager.loadLocalAvatar(avatar.get().getPath());
            AvatarList.selectedEntry = avatar.get().getTheActualPathForThis();

            if (upload)
                NetworkStuff.uploadAvatar(AvatarManager.getAvatarForPlayer(FiguraMod.getLocalPlayerUUID()));
        } else {
            Insomnia.LOGGER.error("Could not locate avatar: {}", avatarName);
        }
    }
}
