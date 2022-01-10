package com.laggytrylma.frontend.pages;

import com.laggytrylma.frontend.states.Context;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class PageManager {
    List<Page> pages = new LinkedList<>();
    Stack<Page> stack = new Stack<>();
    JFrame parent;

    public PageManager(JFrame parent, Context ctx){
        this.parent = parent;
        pages.add(new MenuPage(ctx));
        pages.add(new GamePage(ctx));
    }

    public void push(String name){
        Page entering = find(name);
        if(entering == null)
            return;
        if(!stack.empty()){
            Page leaving = stack.pop();
            leaving.onClose();
        }

        stack.push(entering);
        entering.onOpen();
        parent.setContentPane(entering);
        parent.setVisible(true);
    }

    public void pop(){
        if(!stack.empty()){
            Page leaving = stack.pop();
            leaving.onClose();
        }
        if(!stack.empty()){
            Page entering = stack.peek();
            entering.onOpen();
            parent.setContentPane(entering);
            parent.setVisible(true);
        }

    }

    public void set(String name){
        Page entering = find(name);
        if(entering == null)
            return;
        if(!stack.empty()){
            Page leaving = stack.pop();
            leaving.onClose();
        }
        stack.clear();
        stack.push(entering);
        parent.setContentPane(entering);
        parent.setVisible(true);
    }

    Page find(String name){
        for(Page page : pages){
            if(page.getPageName().equals(name))
                return page;
        }
        return null;
    }
}
