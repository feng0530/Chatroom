package tw.idv.frank.chatroom.manager.model.dto;


import lombok.Data;
import tw.idv.frank.chatroom.common.constant.RoleName;

import java.util.Date;

@Data
public class ManagerRes {

    private Integer managerId;

    private String name;

    private String account;

    private RoleName role;

    private Date createTime;

    private Date updateTime;
}
