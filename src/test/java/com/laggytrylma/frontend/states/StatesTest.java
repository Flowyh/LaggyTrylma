package com.laggytrylma.frontend.states;


import com.laggytrylma.common.rules.RuleInterface;
import com.laggytrylma.frontend.pages.PageManager;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class StatesTest {
    Context ctx;
    PageManager pm;

    static class MiniServer implements Runnable{
        ServerSocket serverSocket;
        boolean active = true;

        @Override
        public void run() {
            try {
                serverSocket = new ServerSocket(21375);
                Socket sock = serverSocket.accept();
                ObjectInputStream is = new ObjectInputStream(sock.getInputStream());
                ObjectOutputStream os = new ObjectOutputStream(sock.getOutputStream());
                while(!sock.isClosed() && active){
                    sleep(100);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                return;
            }
        }

        public void close(){
            active = false;
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Before
    public void setup(){
        ctx = new Context();
        pm = mock(PageManager.class);
        ctx.setPageManager(pm);
    }

    @Test
    public void testInstantiation(){
        assertNotNull(ctx);
        assertTrue(ctx.state instanceof DisconnectedState);
    }

//    @Test
//    public void testStateTransitions(){
//        MiniServer ms = new MiniServer();
//        Thread thread = new Thread(ms);
//        thread.start();
//        ctx.connect("127.0.0.1:21375", "kris");
//
//        assertTrue(ctx.state instanceof ConnectedState);
//        ctx.join(0);
//        assertTrue(ctx.state instanceof InGameState);
//        ctx.leave();
//        assertTrue(ctx.state instanceof ConnectedState);
//        ctx.createLobby(2);
//        assertTrue(ctx.state instanceof InGameState);
//        ctx.leave();
//        assertTrue(ctx.state instanceof ConnectedState);
//        ctx.disconnect();
//        assertTrue(ctx.state instanceof DisconnectedState);
//
//        ms.close();
//        try {
//            thread.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void testIncorrectIP(){
        ctx.connect("127:0:0", "abc");
        assertTrue(ctx.state instanceof DisconnectedState);
    }

}
