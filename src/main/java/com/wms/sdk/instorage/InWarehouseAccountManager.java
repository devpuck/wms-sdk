package com.wms.sdk.instorage;

import com.alibaba.fastjson.JSONObject;
import com.wms.api.account.AccountResult;
import com.wms.api.account.InWarehouseAccountVo;
import com.xac.core.api.ApiResultCode;
import com.xac.core.util.ApiResultUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * 入库过账SDK，供立体仓自动过账、测试使用使用
 * @author puck
 * @date 2021/1/6 4:28 下午
 */
public class InWarehouseAccountManager
{
    private String URL = null;

    public InWarehouseAccountManager()
    {
        queryURL();
    }

    public String queryURL()
    {
        URL = "http://127.0.0.1:9200/";
        return URL;
    }

    public AccountResult createInStorageAccount(InWarehouseAccountVo vo)
    {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<InWarehouseAccountVo> requestEntity = new HttpEntity<InWarehouseAccountVo>(vo, requestHeaders);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL+"/in/account/create", requestEntity, String.class);
        JSONObject json = JSONObject.parseObject(responseEntity.getBody());
        System.out.println("RESULT:"+json.toString());
        int code = Integer.parseInt(json.get("code").toString());
        if(ApiResultCode.SUCCESS.getCode() == code)
        {
            AccountResult accountResult = ApiResultUtil.jsonToBo(responseEntity.getBody(), AccountResult.class);
            return accountResult;
        }
        return null;
    }
}
