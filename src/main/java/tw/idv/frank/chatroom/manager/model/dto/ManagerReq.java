package tw.idv.frank.chatroom.manager.model.dto;

import lombok.Data;
import tw.idv.frank.chatroom.common.constant.RoleName;

@Data
public class ManagerReq {

    private Integer managerId;

    private String name;

    private String password;

    private RoleName role;
}
