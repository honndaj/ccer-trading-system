package com.example.service;

import com.example.controller.dto.UserDTO;
import com.example.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lzq
 * @since 2023-04-16
 */
public interface IUserService extends IService<User> {

    UserDTO login(UserDTO userDTO);

    User register(UserDTO userDTO);// TODO is return User necessary? true or false is better?
}
