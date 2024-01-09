package edu.tcu.cs.kayscollectiononline.artifact.client.ai.chat.dto;

import java.util.List;

public record ChatResponse(List<Choice> choices) {
}
