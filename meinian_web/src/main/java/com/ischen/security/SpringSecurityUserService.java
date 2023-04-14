package com.ischen.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ischen.pojo.Permission;
import com.ischen.pojo.Role;
import com.ischen.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * SpringSecurityUserService
 *
 * @Author: ischen
 * @CreateTime: 2021-07-07
 * @Description:
 */
@Component
public class SpringSecurityUserService implements UserDetailsService {

    @Reference
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询数据库
        com.ischen.pojo.User user = userService.findUserByUsername(username);

        if (user==null){
            return null;
        }
        // 获取所有的角色
        Set<Role> roles = user.getRoles();
        List<GrantedAuthority> lists = new ArrayList<>();


        for (Role role : roles) {
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions) {
                lists.add(new SimpleGrantedAuthority(permission.getKeyword())) ;
            }
        }


        return new User(username,user.getPassword(),lists);
    }
}