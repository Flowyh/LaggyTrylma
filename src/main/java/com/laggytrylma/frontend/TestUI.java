package com.laggytrylma.frontend;


import com.laggytrylma.common.ClassicTrylmaBuilder;
import com.laggytrylma.common.Game;
import com.laggytrylma.common.GameBuilderDirector;
import jdk.jshell.spi.ExecutionControl;

import javax.swing.*;

public class TestUI {
    public static void main(String[] args) {
        JFrame f=new JFrame();//creating instance of JFrame

        f.setLayout(new BoxLayout(f.getContentPane(), BoxLayout.Y_AXIS));

        GameBuilderDirector director = new GameBuilderDirector(new ClassicTrylmaBuilder());
        Game game = director.build();

        GameDisplay display = new GameDisplay();
        display.attachGame(game);
        f.add(display);

        f.setSize(400,500);//400 width and 500 height
        f.setVisible(true);//making the frame visible
    }
}
