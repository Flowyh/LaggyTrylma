package com.laggytrylma.frontend;

import com.laggytrylma.utils.communication.commandwrappers.JSON.JSONCommandWrapper;
import org.mockito.ArgumentMatcher;


public class CommandMatcher implements ArgumentMatcher<JSONCommandWrapper<?>> {
    private final String expectedString;
    public CommandMatcher(JSONCommandWrapper<?> expectedCommand){
        expectedString = expectedCommand.toString();
    }

    @Override
    public boolean matches(JSONCommandWrapper<?> jsonCommandWrapper) {
        return jsonCommandWrapper.toString().equals(expectedString);
    }
}
