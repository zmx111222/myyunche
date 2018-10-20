package com.myyunche.zmx.config.activiti;

import com.myyunche.zmx.domain.entity.Role;
import com.myyunche.zmx.domain.entity.User;
import org.activiti.engine.identity.Group;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityImpl;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.UserEntityImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: ZhongMingxiao
 * @create: 2018-09-08 10:26
 * @description: 转换成activiti实体对象
 **/
public class ActivitiUserUtil
{
    public static UserEntity toActivitiUser(User bUser){
        UserEntity userEntity = new UserEntityImpl();
        userEntity.setId(bUser.getUsername());
        userEntity.setFirstName(bUser.getUsername());
        userEntity.setLastName(bUser.getUsername());
        userEntity.setPassword(bUser.getPassword());
        userEntity.setRevision(1);
        return userEntity;
    }

    public static GroupEntity toActivitiGroup(Role sysRole){
        GroupEntity groupEntity = new GroupEntityImpl();
        groupEntity.setRevision(1);
        groupEntity.setType("assignment");
        groupEntity.setId(sysRole.getRname());
        groupEntity.setName(sysRole.getRname());
        return groupEntity;
    }

    public static List<Group> toActivitiGroups(List<Role> sysRoles){
        List<Group> groups = new ArrayList<Group>();
        for (Role sysRole : sysRoles) {
            GroupEntity groupEntity = toActivitiGroup(sysRole);
            groups.add(groupEntity);
        }
        return groups;
    }
}
