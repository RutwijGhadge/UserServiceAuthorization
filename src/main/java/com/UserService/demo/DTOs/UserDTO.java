package com.UserService.demo.DTOs;

import com.UserService.demo.Model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class UserDTO {
    private String email;
   // private Set<Role> roles=new HashSet<>();
}
