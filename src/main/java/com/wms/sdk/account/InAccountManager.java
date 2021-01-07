package com.wms.sdk.account;

import com.alibaba.fastjson.JSONObject;
import com.wms.api.account.AccountResult;
import com.wms.api.account.InWarehouseAccountVo;
import com.xac.core.api.ApiResultCode;
import com.xac.core.util.ApiResultUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * 入库过账，测试接口
 * @author puck
 * This sdk just for test,please dont use in produce evn
 * @date 2020/12/25 3:56 下午
 */
public class InAccountManager
{
    private String URL = null;

    public InAccountManager()
    {
        queryURL();
    }

    public String queryURL()
    {
        URL = "http://127.0.0.1:9300/";
        return URL;
    }

//    public int accountIn(AccountVo vo)
//    {
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders requestHeaders = new HttpHeaders();
//        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<AccountVo> requestEntity = new HttpEntity<AccountVo>(vo, requestHeaders);
//
//        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL+"account/in/add", requestEntity, String.class);
//        /*        ApiResult result = ApiResultUtil.jsonToBo(responseEntity.getBody(),ApiResult.class);*/
//        JSONObject json = JSONObject.parseObject(responseEntity.getBody());
//        int code = Integer.parseInt(json.get("code").toString());
//        /*        System.out.println("APIRESULT:" + json.get("code"));*/
//        return code;
//    }

    public AccountResult createInStorageAccount(InWarehouseAccountVo vo)
    {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<InWarehouseAccountVo> requestEntity = new HttpEntity<InWarehouseAccountVo>(vo, requestHeaders);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL+"account/in/create", requestEntity, String.class);
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

    public int mergeByCode(String accountCode,BigDecimal quantity)
    {
        String requestCode = UUID.randomUUID().toString().replaceAll("-", "");

        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("accountCode", accountCode);
        postParameters.add("quantity", quantity);
        postParameters.add("requestCode", requestCode);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(postParameters, headers);
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity(URL+"/account/in/mergeByCode", httpEntity,String.class);
        JSONObject json = JSONObject.parseObject(responseEntity.getBody());
        int code = Integer.parseInt(json.get("code").toString());
        /*        System.out.println("APIRESULT:" + json.get("code"));*/
        return code;
    }

    public int mergeByID(String accountID, String inStorageBillSubID, BigDecimal quantity)
    {
        String requestCode = UUID.randomUUID().toString().replaceAll("-", "");

        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("accountID", accountID);
        postParameters.add("quantity", quantity);
        postParameters.add("requestCode", requestCode);
        postParameters.add("inStorageBillSubID",inStorageBillSubID);

        HttpHeaders headers = new HttpHeaders();
    //    headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(postParameters, headers);
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity(URL+"/account/in/mergeByID", httpEntity,String.class);
        JSONObject json = JSONObject.parseObject(responseEntity.getBody());
        int code = Integer.parseInt(json.get("code").toString());
        /*        System.out.println("APIRESULT:" + json.get("code"));*/
        return code;
    }
}
