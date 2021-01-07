package com.wms.sdk.warehouse;

import com.alibaba.fastjson.JSONObject;
import com.wms.api.location.LocationProductionVo;
import com.wms.api.location.WarehouseLocationVo;
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
 * @date 2020/12/18 2:16 下午
 */
public class LocationManager
{
    private String URL = null;

    public String queryURL()
    {
        URL = "http://127.0.0.1:8880/";
        return URL;
    }

    public LocationManager()
    {
        queryURL();
    }

    public int createLocation(WarehouseLocationVo vo)
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL+"location/add", vo, String.class);
        JSONObject json = JSONObject.parseObject(responseEntity.getBody());
        int code = Integer.parseInt(json.get("code").toString());
        System.out.println("APIRESULT:" + json.get("code"));
        return code;
    }

    public int updateLocation(WarehouseLocationVo vo)
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL+"location/update", vo, String.class);
        JSONObject json = JSONObject.parseObject(responseEntity.getBody());
        int code = Integer.parseInt(json.get("code").toString());
        System.out.println("APIRESULT:" + json.get("code"));
        return code;
    }

    public int updateLocationState(String warehouseCode,String locationCode,String state)
    {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("warehouseCode", warehouseCode);
        postParameters.add("locationCode", locationCode);
        postParameters.add("state", state);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(postParameters, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL+"location/updateState",httpEntity,String.class);
        JSONObject json = JSONObject.parseObject(responseEntity.getBody());
        int code = Integer.parseInt(json.get("code").toString());
        System.out.println("APIRESULT:" + json.get("code"));
        return code;
    }

    public List<WarehouseLocationVo> queryWarehouseLocation(String warehouseCode)
    {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("warehouseCode", warehouseCode);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(postParameters, headers);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL+"location/query/warehouseLocation", httpEntity, String.class);
        ApiResult<List<WarehouseLocationVo>> apiResult = ApiResultUtil.parseListResult(responseEntity.getBody(), WarehouseLocationVo.class);
        return apiResult.getData();
    }

    public WarehouseLocationVo queryWarehouseLocationInfo(String warehouseCode,String locationCode)
    {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("warehouseCode", warehouseCode);
        postParameters.add("locationCode", locationCode);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(postParameters, headers);
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity(URL+"location/query/info", httpEntity,String.class);
        WarehouseLocationVo vo = ApiResultUtil.jsonToBo(responseEntity.getBody(), WarehouseLocationVo.class);
        //System.out.println("VVVVVVVO:" + vo.getWarehouseCode());
        return vo;
    }

    public int bindProduction(LocationProductionVo vo)
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL+"location/bindProduction", vo, String.class);
        JSONObject json = JSONObject.parseObject(responseEntity.getBody());
        int code = Integer.parseInt(json.get("code").toString());
        System.out.println("APIRESULT:" + json.get("code"));
        return code;
    }

    public int unBindProduction(String id)
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL+"location/unBindProduction/"+id, null, String.class);
        JSONObject json = JSONObject.parseObject(responseEntity.getBody());
        int code = Integer.parseInt(json.get("code").toString());
        System.out.println("APIRESULT:" + json.get("code"));
        return code;
    }

    public List<LocationProductionVo> queryLocationProductionInfo(String warehouseCode,String locationCode)
    {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("warehouseCode", warehouseCode);
        postParameters.add("locationCode", locationCode);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(postParameters, headers);
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity(URL+"location/query/queryLocationBindProduction", httpEntity,String.class);
        ApiResult<List<LocationProductionVo>> apiResult = ApiResultUtil.parseListResult(responseEntity.getBody(), LocationProductionVo.class);
        return apiResult.getData();
    }

    public List<LocationProductionVo> queryProductionLocation(String productionCode)
    {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("productionCode", productionCode);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(postParameters, headers);
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity(URL+"location/query/queryProductionBindLocation", httpEntity,String.class);
        ApiResult<List<LocationProductionVo>> apiResult = ApiResultUtil.parseListResult(responseEntity.getBody(), LocationProductionVo.class);
        return apiResult.getData();
    }
}
