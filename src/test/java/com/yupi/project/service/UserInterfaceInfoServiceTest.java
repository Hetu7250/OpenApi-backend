package com.yupi.project.service;

import com.yupi.project.model.entity.UserInterfaceInfo;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserInterfaceInfoServiceTest {
    @Resource
    UserInterfaceInfoService userInterfaceInfoService;
    @Test
    public void invokeCount() {
        userInterfaceInfoService.invokeCount(1,1);
    }
}