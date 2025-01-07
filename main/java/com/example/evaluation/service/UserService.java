package com.example.evaluation.service;

import com.example.evaluation.entity.User;
import com.example.evaluation.vo.LoginVO;
import com.example.evaluation.vo.UserVO;
import java.util.List;

public interface UserService {
    // 用户登录
    UserVO login(LoginVO loginVO);
    
    // 获取所有用户
    List<User> getAllUsers();
    
    // 根据ID获取用户
    User getUserById(Long id);
    
    // 根据用户名获取用户
    User getUserByUsername(String username);
    
    // 创建用户
    User createUser(User user);
    
    // 更新用户
    User updateUser(User user);
    
    // 删除用户
    void deleteUser(Long id);
    
    // 修改密码
    void updatePassword(Long id, String oldPassword, String newPassword);
    
    // 重置密码
    void resetPassword(Long id);
    
    // 修改用户状态
    void updateStatus(Long id, Boolean enabled);
}