package org.jeecg.modules.management.mobile.token.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.management.mobile.token.entity.Token;
import org.jeecg.modules.management.mobile.token.mapper.TokenMapper;
import org.jeecg.modules.management.mobile.token.service.ITokenService;
import org.springframework.stereotype.Service;

/**
 * @Description: token表
 * @Author: jeecg-boot
 * @Date:   2020-03-23
 * @Version: V1.0
 */
@Service
public class TokenServiceImpl extends ServiceImpl<TokenMapper, Token> implements ITokenService {

}
