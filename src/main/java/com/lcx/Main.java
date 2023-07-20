package com.lcx;

import com.kingdee.shr.api.OSFWSClient;
import com.kingdee.shr.osf.webservice.client.UserInfo;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        String SHR_LOCAL = "https://192.168.90.249/shr";

        //OSF名称，注意是名称不是编码
        String serviceName = "getAttendanceFiles";
        //调用OSF所需要的参数
        Map<String,Object> param = new HashMap<>();
        param.put("rows",100);
        param.put("page",1);
        param.put("transmitStartTime","1900-01-01");
        param.put("flag","add");
        OSFWSClient client = new OSFWSClient();

        //webservice登录系统的用户信息
        UserInfo userInfo=new UserInfo();
        userInfo.setDcName("HTDL01");//数据中心ID可在管理控制台中查看，为数据中心代码
        userInfo.setLanguage("ch");//多语言，L1，L2，L3
        userInfo.setUserName("89970782");//用户名
        userInfo.setPassword("123456789");//用户密码
        userInfo.setSlnName("eas");//固定值eas
        userInfo.setDbType(0);//数据库类型，0-SQL Server, 1-Oracle, 2-DB2

        String res = client.proceedOSF(SHR_LOCAL,serviceName,param,userInfo);
        System.out.println(res);
    }
}