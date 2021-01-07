package com.wms.sdk.warehouse;

import com.alibaba.fastjson.JSONObject;
import com.wms.api.deal.WarehouseDealsVo;
import com.xac.core.api.ApiResult;
import com.xac.core.util.ApiResultUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author puck
 * @date 2020/12/21 5:35 下午
 */
public class DealsManager
{
    private String URL = null;

    public DealsManager()
    {
        queryURL();
    }

    public String queryURL()
    {
        URL = "http://127.0.0.1:8880/";
        return URL;
    }

    public int createDeal(WarehouseDealsVo warehouseDeals)
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL+"deal/add", warehouseDeals, String.class);
        /*        ApiResult result = ApiResultUtil.jsonToBo(responseEntity.getBody(),ApiResult.class);*/
        JSONObject json = JSONObject.parseObject(responseEntity.getBody());
        int code = Integer.parseInt(json.get("code").toString());
        System.out.println("APIRESULT:" + json.get("code"));
        return code;
    }

    public int updateDeal(WarehouseDealsVo warehouseDeals)
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL+"deal/update", warehouseDeals, String.class);
        /*        ApiResult result = ApiResultUtil.jsonToBo(responseEntity.getBody(),ApiResult.class);*/
        JSONObject json = JSONObject.parseObject(responseEntity.getBody());
        int code = Integer.parseInt(json.get("code").toString());
        System.out.println("APIRESULT:" + json.get("code"));
        return code;
    }

    public int deleteDeal(String id)
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL+"deal/delete/"+id, null, String.class);
        JSONObject json = JSONObject.parseObject(responseEntity.getBody());
        int code = Integer.parseInt(json.get("code").toString());
        System.out.println("APIRESULT:" + json.get("code"));
        return code;
    }

    public WarehouseDealsVo query(String dealCode)
    {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("dealCode", dealCode);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(postParameters, headers);
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity(URL+"deal/query/queryByCode", httpEntity,String.class);
        WarehouseDealsVo vo = ApiResultUtil.jsonToBo(responseEntity.getBody(), WarehouseDealsVo.class);
        //System.out.println("VVVVVVVO:" + vo.getWarehouseCode());
        return vo;
    }

    public List<WarehouseDealsVo> queryWarehouseDeasList()
    {
        RestTemplate restTemplate = new RestTemplate();
/*        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("warehouseCode", warehouseCode);
        postParameters.add("locationCode", locationCode);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(postParameters, headers);*/
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity(URL+"deal/query/queryDealList", null,String.class);
        ApiResult<List<WarehouseDealsVo>> apiResult = ApiResultUtil.parseListResult(responseEntity.getBody(), WarehouseDealsVo.class);
        return apiResult.getData();
    }
}
