package com.rachel.gulimall.member.exception;

public class PhoneExistsException extends RuntimeException {

    public PhoneExistsException() {
        super("phone存在");
    }

}
