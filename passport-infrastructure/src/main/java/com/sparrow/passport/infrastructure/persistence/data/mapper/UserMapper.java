package com.sparrow.passport.infrastructure.persistence.data.mapper;

import com.sparrow.enums.Gender;
import com.sparrow.passport.po.User;
import com.sparrow.protocol.ClientInformation;
import com.sparrow.protocol.constant.magic.Symbol;
import com.sparrow.protocol.enums.StatusRecord;
import com.sparrow.support.IpSupport;
import com.sparrow.passport.domain.entity.RegisteringUserEntity;
import com.sparrow.passport.domain.entity.SecurityPrincipalEntity;
import javax.inject.Inject;
import javax.inject.Named;

@Named
public class UserMapper {
    @Inject
    private IpSupport ipSupport;

    public SecurityPrincipalEntity user2SecurityPrincipal(User user) {
        if (user == null) {
            return null;
        }
        SecurityPrincipalEntity securityPrincipal = new SecurityPrincipalEntity();
        securityPrincipal.setActivate(user.getActivate());
        securityPrincipal.setCent(user.getCent());
        securityPrincipal.setEmail(user.getEmail());
        securityPrincipal.setMobile(user.getMobile());
        securityPrincipal.setUserName(user.getUserName());
        securityPrincipal.setLastLoginTime(user.getLastLoginTime());
        securityPrincipal.setUserId(user.getUserId());
        securityPrincipal.setPassword(user.getPassword());
        securityPrincipal.setStatus(user.getStatus().ordinal());
        return securityPrincipal;
    }

    public RegisteringUserEntity user2RegisteringUser(User user) {
        if (user == null) {
            return null;
        }
        RegisteringUserEntity registeringUser = new RegisteringUserEntity();
        registeringUser.setCent(user.getCent());
        registeringUser.setEmail(user.getEmail());
        registeringUser.setMobile(user.getMobile());
        registeringUser.setUserName(user.getUserName());
        registeringUser.setUserId(user.getUserId());
        registeringUser.setPassword(user.getPassword());
        return registeringUser;
    }

    public User registeringUser2User(RegisteringUserEntity registeringUser, ClientInformation client) {
        if (registeringUser == null) {
            return null;
        }
        User user = new User();
        user.setUserId(registeringUser.getUserId());
        user.setCent(registeringUser.getCent());
        user.setUserName(registeringUser.getUserName());
        user.setEmail(registeringUser.getEmail());
        user.setPassword(registeringUser.getPassword());
        user.setDevice(client.getDevice());
        user.setChannel(client.getChannel());
        user.setDeviceModel(client.getDeviceModel());
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(user.getGmtCreate());
        user.setLastLoginTime(System.currentTimeMillis());
        user.setActivate(registeringUser.getActivate());
        if (user.getActivate()) {
            user.setActivateTime(System.currentTimeMillis());
        } else {
            user.setActivateTime(0L);
        }
        user.setAvatar(Symbol.EMPTY);
        user.setGender(Gender.NULL.ordinal());
        user.setGroupLevel(Symbol.EMPTY);
        user.setBirthday(null);
        user.setNickName(user.getUserName());
        user.setPersonalSignature(Symbol.EMPTY);
        user.setIp(this.ipSupport.toLong(client.getIp()));
        user.setStatus(StatusRecord.ENABLE);
        user.setMobile(registeringUser.getMobile());
        if (user.getMobile() == null) {
            user.setMobile(Symbol.EMPTY);
            user.setSecretMobile(Symbol.EMPTY);
        }
        return user;
    }
}
