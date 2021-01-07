package com.wms.sdk.account;

import com.alibaba.fastjson.JSONObject;
import com.wms.api.requirements.InRequirementVo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @author puck
 * This sdk just for test,please dont use in produce evn
 * @date 2021/1/6 2:55 下午
 */
public class InAccountForXacJinhang
{
    private String URL = null;

    public InAccountForXacJinhang()
    {
        queryURL();
    }

    public String queryURL()
    {
        URL = "http://localhost:8080/XacSdkTest/";
        return URL;
    }

    public int createRequirement(InRequirementVo vo)
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL+"test", vo, String.class);
        /*        ApiResult result = ApiResultUtil.jsonToBo(responseEntity.getBody(),ApiResult.class);*/
        JSONObject json = JSONObject.parseObject(responseEntity.getBody());
        int code = Integer.parseInt(json.get("code").toString());
        /*        System.out.println("APIRESULT:" + json.get("code"));*/
        return code;
    }


}
