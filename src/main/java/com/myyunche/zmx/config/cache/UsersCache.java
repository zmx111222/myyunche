/**
 * @author: ZhongMingxiao
 * @create: 2018-08-12 13:12
 * @description: 所有用户缓存
 **/
package com.myyunche.zmx.config.cache;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.myyunche.zmx.domain.entity.User;
import com.myyunche.zmx.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Component
public class UsersCache
{
    private static final String ALL_USERS_NAME="users:all:name";
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserMapper userMapper;



    @PostConstruct
    private void refreshAllOnlyProperty()
    {
        List<User> allOnlyProperty = userMapper.selectUser();

        if (!CollectionUtils.isEmpty(allOnlyProperty))
        {
            List<String> usernames = Lists.newArrayList();

            allOnlyProperty.stream()
                    .forEach(e->
                    {
                        String username = e.getUsername();
                        if(StringUtils.isNotBlank(username))
                        usernames.add(username);

                    });
            // 刷新缓存
            BoundValueOperations<String, String> boundValueOps = stringRedisTemplate.boundValueOps(ALL_USERS_NAME);
            boundValueOps.set(JSON.toJSONString(usernames));
        }
    }
}
