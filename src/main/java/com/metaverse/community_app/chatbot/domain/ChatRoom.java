package com.metaverse.community_app.chatbot.domain;

import com.metaverse.community_app.auth.domain.User;
import com.metaverse.community_app.common.domain.TimeStamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "chat_room")
public class ChatRoom extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_room_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String title;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatDialog> chatDialogs = new ArrayList<>();

    public ChatRoom(User user, String title) {
        this.user = user;
        this.title = title;
    }
}