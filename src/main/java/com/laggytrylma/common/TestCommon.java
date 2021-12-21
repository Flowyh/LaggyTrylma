package com.laggytrylma.common;

public class TestCommon {
    public static void main(String[] args) {
        GameBuilderDirector director = new GameBuilderDirector(new ClassicTrylmaBuilder());
        Game trylma = director.build();

    }
}
