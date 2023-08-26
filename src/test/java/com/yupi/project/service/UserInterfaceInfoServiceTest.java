package com.yupi.project.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import service.UserInterfaceInfoService;

import javax.annotation.Resource;

@SpringBootTest
class UserInterfaceInfoServiceTest {
    @Resource
    UserInterfaceInfoService userInterfaceInfoService;
    @Test
    public void invokeCount() {
        userInterfaceInfoService.invokeCount(1,1);
    }
}