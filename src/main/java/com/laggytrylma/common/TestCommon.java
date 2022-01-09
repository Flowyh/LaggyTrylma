package com.laggytrylma.common;

import com.laggytrylma.common.builders.ClassicTrylmaBuilder;
import com.laggytrylma.common.builders.GameBuilderDirector;

public class TestCommon {
    public static void main(String[] args) {
        GameBuilderDirector director = new GameBuilderDirector(new ClassicTrylmaBuilder());
        Game trylma = director.build();

    }
}
