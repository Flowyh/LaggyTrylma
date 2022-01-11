package com.laggytrylma.frontend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laggytrylma.utils.Logger;
import com.laggytrylma.utils.communication.AbstractSocket;
import com.laggytrylma.utils.communication.commandwrappers.JSON.JSONCommandWrapper;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;


public class ClientSocket extends AbstractSocket {
    private boolean force_close = false;

    public ClientSocket(Socket socket) throws IOException {
        super(socket);
       setOutput(new ObjectOutputStream(socket.getOutputStream()));
       setInput(new ObjectInputStream(socket.getInputStream()));
    }

    public boolean sendMessage(JSONCommandWrapper<?> msg){
        try {
            String serialized = msg.serialize();
            System.out.println(serialized);
            writeOutput(serialized);
        } catch(IOException e) {
            Logger.error(e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public void setup() {

    }

    @Override
    public void listen() {
        JSONCommandWrapper<?> reqJSON = null;
        System.out.println("Listening");
        while (!socket.isClosed()) {
            System.out.println("Loop");
            try {
                Object o = readInput();
                if(!(o instanceof String)) continue;
                if(!isJSONValid((String) o)) continue; // Skip if invalid json was sent
                reqJSON = new JSONCommandWrapper<>((String) o);
                Object res = socketHandler.processInput(reqJSON, getUUID());
                if(res.equals(-1)) break;
            } catch(SocketTimeoutException ignored) {
            } catch(EOFException e) { // Socket closing
                break;
            } catch (IOException | ClassNotFoundException e) {
                if(!force_close){
                    Logger.error(e.getMessage());
                }
                break;
            }
            if(reqJSON != null) Logger.debug("Incoming command: " + reqJSON);
        }
        // quit()
    }

    public void close() throws IOException{
        force_close = true;
        super.close();
    }

    private boolean isJSONValid(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(json);
            return true;
        } catch (JsonProcessingException e) {
            return false;
        }
    }
}
