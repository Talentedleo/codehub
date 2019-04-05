package com.codehub.sms;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author Weiping Li
 * @Version 1.0
 * File Created on 2018/11/23
 *
 * 短信监听类
 */
@Component
@RabbitListener(queues = "codehub_sms")
public class SmsListener {

    //注入短信工具
    @Autowired
    private SmsUtil smsUtil;

    //模板ID
    @Value("${aliyun.sms.temp_code}")
    private String tempCode;

    //短信签名
    @Value("${aliyun.sms.sign_name}")
    private String signName;

    /**
     * 发送短信
     */
    @RabbitHandler
    public void sendSms(Map<String, String> map, Channel channel, Message message){
        System.out.println("mobile:" + map.get("mobile"));
        System.out.println("code:" + map.get("code"));

        try {
            //使用阿里短信发送手机验证码
            SendSmsResponse sendSmsResponse = smsUtil.sendSms(map.get("mobile"), tempCode, signName, "{'code': '" + map.get("code") + "'}");

            //判断短信是否发送成功
            if (sendSmsResponse.getCode().equals("OK")){

                //为了防止短信发送失败,手动返回确认消息,如果失败,MQ是会重新发送消息的
                //确认消息收到,返回确认消息给MQ
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

                System.out.println("短信发送成功");
            }else {
                System.out.println("短信发送失败: " + sendSmsResponse.getCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
