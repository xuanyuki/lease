package cc.xianyu.lease.web.admin.service.impl;

import cc.xianyu.lease.common.exception.LeaseException;
import cc.xianyu.lease.common.result.ResultCodeEnum;
import cc.xianyu.lease.common.utils.JwtUtil;
import cc.xianyu.lease.model.entity.SystemUser;
import cc.xianyu.lease.model.enums.BaseStatus;
import cc.xianyu.lease.web.admin.mapper.SystemUserMapper;
import cc.xianyu.lease.web.admin.service.LoginService;
import cc.xianyu.lease.web.admin.vo.login.CaptchaVo;
import cc.xianyu.lease.web.admin.vo.login.LoginVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.wf.captcha.SpecCaptcha;
import org.apache.commons.codec.cli.Digest;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static cc.xianyu.lease.common.constant.RedisConstant.ADMIN_LOGIN_PREFIX;

@Service
public class LoginServiceImpl implements LoginService {

  @Autowired
  private StringRedisTemplate redisTemplate;

  @Autowired
  private SystemUserMapper systemUserMapper;

  @Override
  public CaptchaVo getCaptcha() {
    String uuid = ADMIN_LOGIN_PREFIX + UUID.randomUUID();
    SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);
    String code = specCaptcha.text().toLowerCase();
    String base64 = specCaptcha.toBase64();
    redisTemplate.opsForValue().set(uuid, code, 1, TimeUnit.MINUTES);
    System.out.println("验证码：" + code);
    return new CaptchaVo(base64, uuid);
  }

  @Override
  public String login(LoginVo loginVo) {
    if (loginVo.getCaptchaCode() == null || loginVo.getCaptchaCode().isEmpty()) {
      throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_NOT_FOUND);
    }
    String code = redisTemplate.opsForValue().get(loginVo.getCaptchaKey());
    if (code == null) {
      throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_EXPIRED);
    }
    if (!code.equals(loginVo.getCaptchaCode().toLowerCase())) {
      throw new LeaseException(ResultCodeEnum.ADMIN_CAPTCHA_CODE_ERROR);
    }
    LambdaQueryWrapper<SystemUser> queryWrapper = new LambdaQueryWrapper<>();
    queryWrapper.eq(SystemUser::getUsername, loginVo.getUsername());
    SystemUser user = systemUserMapper.selectOne(queryWrapper);
    if (user == null) {
      throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_NOT_EXIST_ERROR);
    }
    if (user.getStatus() == BaseStatus.DISABLE) {
      throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_DISABLED_ERROR);
    }
    if (!user.getPassword().equals(DigestUtils.md5Hex(loginVo.getPassword()))) {
      throw new LeaseException(ResultCodeEnum.ADMIN_ACCOUNT_ERROR);
    }
    String jwt = JwtUtil.getJwtToken(loginVo.getUsername(), user.getId().toString());
    return jwt;
  }
}
