package com.lcx;

import com.getui.push.v2.sdk.ApiHelper;
import com.getui.push.v2.sdk.GtApiConfiguration;
import com.getui.push.v2.sdk.api.PushApi;
import com.getui.push.v2.sdk.common.ApiResult;
import com.getui.push.v2.sdk.dto.req.Audience;
import com.getui.push.v2.sdk.dto.req.Settings;
import com.getui.push.v2.sdk.dto.req.message.PushChannel;
import com.getui.push.v2.sdk.dto.req.message.PushDTO;
import com.getui.push.v2.sdk.dto.req.message.PushMessage;
import com.getui.push.v2.sdk.dto.req.message.android.AndroidDTO;
import com.getui.push.v2.sdk.dto.req.message.android.ThirdNotification;
import com.getui.push.v2.sdk.dto.req.message.android.Ups;
import com.getui.push.v2.sdk.dto.req.message.ios.Alert;
import com.getui.push.v2.sdk.dto.req.message.ios.Aps;
import com.getui.push.v2.sdk.dto.req.message.ios.IosDTO;
import com.getui.push.v2.sdk.dto.res.TaskIdDTO;

public class Test {
    public static void main(String[] args) {
        GtApiConfiguration apiConfiguration = new GtApiConfiguration();
        //填写应用配置，参数在“Uni Push”下的“应用配置”页面中获取
        apiConfiguration.setAppId("hqIDvNuYndAD3HJO8LgWe8");
        apiConfiguration.setAppKey("eQOdixIbd09art428jNBy8");
        apiConfiguration.setMasterSecret("ifni2aWed4AWRgcrlDrMS");
        apiConfiguration.setDomain("https://restapi.getui.com/v2/");
        // 实例化ApiHelper对象，用于创建接口对象
        ApiHelper apiHelper = ApiHelper.build(apiConfiguration);
        // 创建对象，建议复用。目前有PushApi、StatisticApi、UserApi
        PushApi pushApi = apiHelper.creatApi(PushApi.class);


//        PushDTO<Audience> pushDTO = setPushDTO("9e19f41cd45899c422fb26d15aa5225e");
//        PushDTO<Audience> pushDTO1 = setPushDTO("f78d7b1d6fc92f39219a134ab50c2937");

//        // 进行cid单推
//        ApiResult<Map<String, Map<String, String>>> apiResult = pushApi.pushToSingleByCid(pushDTO);
//        //批量推送
//        PushBatchDTO pushBatchDTO = new PushBatchDTO();
//        pushBatchDTO.addPushDTO(pushDTO);
//        pushBatchDTO.addPushDTO(pushDTO1);
//        ApiResult<Map<String, Map<String, String>>> apiResult = pushApi.pushBatchByCid(pushBatchDTO);
        PushDTO<String> pushDTO = pushAll();
        ApiResult<TaskIdDTO> apiResult = pushApi.pushAll(pushDTO);
        if (apiResult.isSuccess()) {
            // success
            System.out.println(apiResult);
        } else {
            // failed
            System.out.println("code:" + apiResult.getCode() + ", msg: " + apiResult.getMsg());
        }

    }

    private static PushDTO<String> pushAll() {
        PushDTO<String> pushDTO = new PushDTO<>();
        // 设置推送参数，requestid需要每次变化唯一
        pushDTO.setRequestId(System.currentTimeMillis() + "");
        Settings settings = new Settings();
        pushDTO.setSettings(settings);
        //消息有效期，走厂商消息必须设置该值
        settings.setTtl(3600000);
        //在线走个推通道时推送的消息体
        PushMessage pushMessage = new PushMessage();
        pushDTO.setPushMessage(pushMessage);
        //此格式的透传消息由 unipush 做了特殊处理，会自动展示通知栏。开发者也可自定义其它格式，在客户端自己处理。
        pushMessage.setTransmission("{\"title\":\"订单提醒\",\"content\":\"您的外卖订单已接单，请耐心等候\"," +
                "\"payload\":\"您的外卖订单已接单，请耐心等候\"}");
        pushDTO.setPushChannel(setMsg());
        return pushDTO;
    }

    private static PushDTO<Audience> setPushDTO(String cid) {
        //根据cid进行单推
        PushDTO<Audience> pushDTO = new PushDTO<>();
        // 设置推送参数，requestid需要每次变化唯一
        pushDTO.setRequestId(System.currentTimeMillis() + "");
        Settings settings = new Settings();
        pushDTO.setSettings(settings);
        //消息有效期，走厂商消息必须设置该值
        settings.setTtl(3600000);
        //在线走个推通道时推送的消息体
        PushMessage pushMessage = new PushMessage();
        pushDTO.setPushMessage(pushMessage);
        //此格式的透传消息由 unipush 做了特殊处理，会自动展示通知栏。开发者也可自定义其它格式，在客户端自己处理。
        pushMessage.setTransmission("{\"title\":\"订单提醒\",\"content\":\"您的外卖订单已接单，请耐心等候\"," +
                "\"payload\":\"您的外卖订单已接单，请耐心等候\"}");
        // 设置接收人信息
        Audience audience = new Audience();
        pushDTO.setAudience(audience);
        audience.addCid(cid);
        pushDTO.setPushChannel(setMsg());
        return pushDTO;
    }

    private static PushChannel setMsg() {
        //设置离线推送时的消息体
        PushChannel pushChannel = new PushChannel();
        //安卓离线厂商通道推送的消息体
        AndroidDTO androidDTO = new AndroidDTO();
        Ups ups = new Ups();
        ThirdNotification thirdNotification = new ThirdNotification();
        ups.setNotification(thirdNotification);
        thirdNotification.setTitle("订单提醒");
        thirdNotification.setBody("您的外卖订单已接单，请耐心等候");
        thirdNotification.setClickType("intent");
        //注意：intent参数必须按下方文档（特殊参数说明）要求的固定格式传值，intent错误会导致客户端无法收到消息
        thirdNotification.setIntent("intent://io.dcloud.unipush/?#Intent;scheme=unipush;launchFlags=0x4000000;" +
                "component=com.sxspv/io.dcloud.PandoraEntry;S.UP-OL-SU=true;S.title=订单提醒;" +
                "S.content=您的外卖订单已接单，请耐心等候;S.payload=test;end");
        androidDTO.setUps(ups);
        pushChannel.setAndroid(androidDTO);

        //ios离线apn通道推送的消息体
        Alert alert = new Alert();
        alert.setTitle("订单提醒");
        alert.setBody("您的外卖订单已接单，请耐心等候");
        Aps aps = new Aps();
        aps.setContentAvailable(0);
        aps.setSound("default");
        aps.setAlert(alert);
        IosDTO iosDTO = new IosDTO();
        iosDTO.setAps(aps);
        iosDTO.setType("notify");
        pushChannel.setIos(iosDTO);
        return pushChannel;
    }
}
