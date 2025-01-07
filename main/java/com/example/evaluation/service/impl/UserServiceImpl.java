package com.example.evaluation.service.impl;

import com.example.evaluation.entity.User;
import com.example.evaluation.mapper.UserMapper;
import com.example.evaluation.service.UserService;
import com.example.evaluation.vo.LoginVO;
import com.example.evaluation.vo.UserVO;
import com.example.evaluation.exception.BusinessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.evaluation.util.JwtUtil;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    
    public UserServiceImpl(UserMapper userMapper, 
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserVO login(LoginVO loginVO) {
        User user = userMapper.findByUsername(loginVO.getUsername());
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        
        if (!passwordEncoder.matches(loginVO.getPassword(), user.getPassword())) {
            throw new BusinessException("密码错误");
        }
        
        if (!user.getEnabled()) {
            throw new BusinessException("账号已被禁用");
        }
        
        if (!loginVO.getRole().equals(user.getRole())) {
            throw new BusinessException("角色不匹配");
        }
        
        // 生成token
        String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole());
        
        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setUsername(user.getUsername());
        userVO.setRole(user.getRole());
        userVO.setRealName(user.getRealName());
        userVO.setEmail(user.getEmail());
        userVO.setPhone(user.getPhone());
        userVO.setToken(token);
        
        return userVO;
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.findById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    @Transactional
    public User createUser(User user) {
        User existingUser = userMapper.findByUsername(user.getUsername());
        if (existingUser != null) {
            throw new BusinessException("用户名已存在");
        }
        
        user.setPassword(passwordEncoder.encode("123456")); // 默认密码
        user.setEnabled(true);
        userMapper.insert(user);
        return user;
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        User existingUser = userMapper.findById(user.getId());
        if (existingUser == null) {
            throw new BusinessException("用户不存在");
        }
        
        userMapper.update(user);
        return user;
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void updatePassword(Long id, String oldPassword, String newPassword) {
        User user = userMapper.findById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new BusinessException("原密码错误");
        }
        
        user.setPassword(passwordEncoder.encode(newPassword));
        userMapper.updatePassword(user);
    }

    @Override
    @Transactional
    public void resetPassword(Long id) {
        User user = userMapper.findById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        
        user.setPassword(passwordEncoder.encode("123456")); // 重置为默认密码
        userMapper.updatePassword(user);
    }

    @Override
    @Transactional
    public void updateStatus(Long id, Boolean enabled) {
        User user = userMapper.findById(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        
        user.setEnabled(enabled);
        userMapper.updateStatus(user);
    }
}