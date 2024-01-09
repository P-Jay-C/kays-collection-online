package edu.tcu.cs.kayscollectiononline.artifact.client.ai.chat;

import edu.tcu.cs.kayscollectiononline.artifact.client.ai.chat.dto.ChatRequest;
import edu.tcu.cs.kayscollectiononline.artifact.client.ai.chat.dto.ChatResponse;

public interface ChatClient {

    ChatResponse generate(ChatRequest chatRequest);

}
