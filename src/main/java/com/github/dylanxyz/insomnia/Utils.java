package com.github.dylanxyz.insomnia;

import java.util.Set;

public class Utils
{
    public static boolean hasAny(Set<String> A, Set<String> B) {
        for (String element : A) if (B.contains(element)) return true;
        return false;
    }
}
