package com.purple.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomDTO extends ChatRelDTO {
    
    private int chatroom_pk; // 채팅방 번호 
    private int chatroom_state; // 채팅방 상태
    private String chatroom_lasttalk; // 마지막으로 보낸 또는 받은 메시지
    private String chatroom_date; // 마지막으로 채팅한 시간 

    // private String roomnumber; // 채팅방 구분을 위한 번호 - UUID
}