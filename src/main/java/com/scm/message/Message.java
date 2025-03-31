package com.scm.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor

public class Message {
    @Builder.Default
    private MessageType messageType = MessageType.blue;
    private String content;
}
