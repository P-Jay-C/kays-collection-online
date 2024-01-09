package edu.tcu.cs.kayscollectiononline.artifact.client.ai.chat.dto;

import java.util.List;

public record ChatRequest(String model, List<Message> messages) {
}
