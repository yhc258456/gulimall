package com.rachel.gulimall.gulimallthirdservice;

import com.rachel.gulimall.gulimallthirdservice.component.SmsComponent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GulimallThirdServiceApplicationTests {

    @Autowired
    private SmsComponent smsComponent;

    @Test
    void contextLoads() {
    }


    @Test
    public void testSendCode() {
        smsComponent.sendCode("18716196733", "123456");
    }

}
