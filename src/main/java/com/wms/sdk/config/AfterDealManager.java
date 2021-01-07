package com.wms.sdk.config;

import com.alibaba.fastjson.JSONObject;
import com.wms.api.config.ConfigAfterDealVo;
import com.wms.api.instorage.InWarehouseBillVo;
import com.xac.core.api.ApiResult;
import com.xac.core.util.ApiResultUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author puck
 * @date 2020/12/30 2:39 下午
 */
public class AfterDealManager
{
    private String URL = null;

    public AfterDealManager()
    {
        queryURL();
    }

    public String queryURL()
    {
        URL = "http://127.0.0.1:9300/";
        return URL;
    }

    public int createConfig(ConfigAfterDealVo vo)
    {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ConfigAfterDealVo> requestEntity = new HttpEntity<ConfigAfterDealVo>(vo, requestHeaders);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL+"config/add", requestEntity, String.class);
        /*        ApiResult result = ApiResultUtil.jsonToBo(responseEntity.getBody(),ApiResult.class);*/
        JSONObject json = JSONObject.parseObject(responseEntity.getBody());
        int code = Integer.parseInt(json.get("code").toString());
        /*        System.out.println("APIRESULT:" + json.get("code"));*/
        return code;
    }

    public List<ConfigAfterDealVo> queryConfigAfterDealVoList(String systemFrom, String workCode,String dealCode)
    {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("systemFrom", systemFrom);
        postParameters.add("workCode", workCode);
        postParameters.add("workCode", workCode);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(postParameters, headers);
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity(URL+"config/query/afterDeal/queryConfigList", httpEntity,String.class);
        ApiResult<List<ConfigAfterDealVo>> apiResult = ApiResultUtil.parseListResult(responseEntity.getBody(), ConfigAfterDealVo.class);
        return apiResult.getData();
    }


}
