package com.example.chatgpt.service;

import com.example.chatgpt.dto.ChatRequest;
import com.example.chatgpt.dto.ChatResponse;

public interface ChatgptService {

    String sendMessage(String message);

    ChatResponse sendChatRequest(ChatRequest request);

}
