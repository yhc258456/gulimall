package com.rachel.gulimall.member.exception;

public class UserNameExistsException extends RuntimeException {

    public UserNameExistsException() {
        super("用户名存在");
    }

}
