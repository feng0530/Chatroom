package tw.idv.frank.chatroom.friend.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "friend_list")
public class FriendList {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull
    private String member;

    @NotNull
    private String friend;

    @NotNull
    private byte status;

    @NotNull
    @Column(name = "create_time")
    private Date createTime;

    @NotNull
    @Column(name = "update_time")
    private Date updateTime;
}
