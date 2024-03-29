package com.mic.controller;

import com.mic.common.dto.ProducerMessageDTO;
import com.mic.service.SmsManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * @author tianp
 **/
@RestController
@RequestMapping("/")
public class TestController {
    @Autowired
    private SmsManageService smsManageService;

    @GetMapping("/sendMessage")
    public String sendMessage(@RequestParam(required = true) String msg) {
        ProducerMessageDTO dto = new ProducerMessageDTO();
        dto.setBody(msg);
        dto.setTag("A");
        dto.setPubKey(msg + new Random().nextInt(100));
        smsManageService.sendSms(dto);
        return dto.toString();
    }
}
