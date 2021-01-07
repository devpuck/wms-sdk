package com.wms.sdk.requirements;

import com.alibaba.fastjson.JSONObject;
import com.wms.api.requirements.InRequirementVo;
import com.wms.api.transaction.WarehouseTransactionsVo;
import com.wms.api.warehouse.WarehouseVo;
import com.xac.core.util.ApiResultUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * @author puck
 * @date 2020/12/23 3:47 下午
 */
public class InRequirementManager
{
    private String URL = null;

    public InRequirementManager()
    {
        queryURL();
    }

    public String queryURL()
    {
        URL = "http://127.0.0.1:9100/";
        return URL;
    }

    public int createRequirement(InRequirementVo vo)
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL+"requirement/in/add", vo, String.class);
        /*        ApiResult result = ApiResultUtil.jsonToBo(responseEntity.getBody(),ApiResult.class);*/
        JSONObject json = JSONObject.parseObject(responseEntity.getBody());
        int code = Integer.parseInt(json.get("code").toString());
/*        System.out.println("APIRESULT:" + json.get("code"));*/
        return code;
    }

    public InRequirementVo queryRequirementByOriginalOrderId(String originalOrderId)
    {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("originalOrderId", originalOrderId);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(postParameters, headers);
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity(URL+"requirement/in/queryByOriginal", httpEntity,String.class);
        InRequirementVo vo = ApiResultUtil.jsonToBo(responseEntity.getBody(), InRequirementVo.class);
        //System.out.println("VVVVVVVO:" + vo.getWarehouseCode());
        return vo;
    }

    public InRequirementVo queryRequirementById(String id)
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate
                .getForEntity(URL+"requirement/in/info/" + id, String.class);
        InRequirementVo vo = ApiResultUtil.jsonToBo(responseEntity.getBody(), InRequirementVo.class);
        System.out.println("VVVVVVVO:" + vo.getWarehouseCode());

        return vo;
    }

}
