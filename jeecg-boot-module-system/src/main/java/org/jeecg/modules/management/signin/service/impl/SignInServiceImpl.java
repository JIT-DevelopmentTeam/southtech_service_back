package org.jeecg.modules.management.signin.service.impl;

import org.jeecg.modules.management.signin.entity.SignIn;
import org.jeecg.modules.management.signin.mapper.SignInMapper;
import org.jeecg.modules.management.signin.service.ISignInService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 签到签出
 * @Author: jeecg-boot
 * @Date:   2020-03-16
 * @Version: V1.0
 */
@Service
public class SignInServiceImpl extends ServiceImpl<SignInMapper, SignIn> implements ISignInService {

}
