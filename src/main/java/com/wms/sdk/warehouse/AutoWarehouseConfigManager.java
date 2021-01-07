package com.wms.sdk.warehouse;

import com.alibaba.fastjson.JSONObject;
import com.wms.api.autoconfig.AutoWarehouseConfigVo;
import com.xac.core.util.ApiResultUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author puck
 * @date 2020/12/17 3:52 下午
 */
public class AutoWarehouseConfigManager
{
    private String URL = null;

    public AutoWarehouseConfigManager()
    {
        queryURL();
    }

    public String queryURL()
    {
        URL = "http://127.0.0.1:8880/";
        return URL;
    }

    public int updateAutoConfig(AutoWarehouseConfigVo vo)
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL+"autoconfig/update", vo, String.class);
        JSONObject json = JSONObject.parseObject(responseEntity.getBody());
        int code = Integer.parseInt(json.get("code").toString());
        System.out.println("APIRESULT:" + json.get("code"));
        return code;
    }

    public AutoWarehouseConfigVo queryAutoConfig(String warehouseCode)
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate
                .getForEntity(URL+"autoconfig/query/" + warehouseCode, String.class);

        AutoWarehouseConfigVo vo = ApiResultUtil.jsonToBo(responseEntity.getBody(),AutoWarehouseConfigVo.class);
        return vo;
    }

    public int reCreateAppSecret(String warehouseCode)
    {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("warehouseCode", warehouseCode);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(postParameters, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL+"autoconfig/secret/recreate",httpEntity, String.class);
        JSONObject json = JSONObject.parseObject(responseEntity.getBody());
        int code = Integer.parseInt(json.get("code").toString());
        System.out.println("APIRESULT:" + json.get("code"));
        return code;
    }
}
