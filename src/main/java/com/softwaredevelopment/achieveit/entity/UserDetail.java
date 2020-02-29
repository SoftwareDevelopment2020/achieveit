package com.softwaredevelopment.achieveit.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

/**
 * User
 *
 * @author RainkQ
 * @version 1.0.0
 * created on 2020-02-28 13:33
 */
@Data
@ApiModel(value = "用户的逻辑上的类")
public class UserDetail implements UserDetails {

    @ApiModelProperty(value = "map , 项目ID:该项目中的权限的list")
    Map<Integer, List<String>> permissionsMap;
    private Integer id;
    private String username;
    private String password;
    @ApiModelProperty(value = "可能包含了一个员工信息（在这个系统里的话）")
    private Integer employeeId;
    private String name;
    private String emailAddress;
    private String department;
    private String tel;
    @ApiModelProperty(value = "账户未过期")
    private Boolean accountNonExpired;

    @ApiModelProperty(value = "账户未锁定")
    private Boolean accountNonLocked;

    @ApiModelProperty(value = "密码未过期")
    private Boolean credentialsNonExpired;

    @ApiModelProperty(value = "账户启用")
    private Boolean enabled;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for (Role role : roles) {
//            authorities.add( new SimpleGrantedAuthority( role.getName() ) );
//        }
//        return authorities;
        // TODO 本该返回一个role的list
        return new ArrayList<GrantedAuthority>(Arrays.asList(new SimpleGrantedAuthority("authenticated")));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }


}
