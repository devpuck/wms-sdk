package com.wms.sdk.instorage;

import com.alibaba.fastjson.JSONObject;
import com.wms.api.account.AccountVo;
import com.wms.api.instorage.InWarehouseBillVo;
import com.wms.api.instorage.query.QueryInStorageAttribute;
import com.wms.api.requirements.InRequirementVo;
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
 * @date 2020/12/24 4:02 下午
 */
public class InWarehouseManager
{
    private String URL = null;

    public InWarehouseManager()
    {
        queryURL();
    }

    public String queryURL()
    {
        URL = "http://127.0.0.1:9200/";
        return URL;
    }

    /**
     * 创建入库单据
     * @param vo
     * @return
     */
    public int createInWarehouseBill(InWarehouseBillVo vo)
    {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<InWarehouseBillVo> requestEntity = new HttpEntity<InWarehouseBillVo>(vo, requestHeaders);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL+"instorage/add", requestEntity, String.class);
        /*        ApiResult result = ApiResultUtil.jsonToBo(responseEntity.getBody(),ApiResult.class);*/
        JSONObject json = JSONObject.parseObject(responseEntity.getBody());
        int code = Integer.parseInt(json.get("code").toString());
        /*        System.out.println("APIRESULT:" + json.get("code"));*/
        return code;
    }

    public List<InWarehouseBillVo> queryInWarehouseBillByProduction(String productionCode, String status)
    {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("productionCode", productionCode);
        postParameters.add("status", status);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(postParameters, headers);
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity(URL+"instorage/query/queryByProduction", httpEntity,String.class);
        ApiResult<List<InWarehouseBillVo>> apiResult = ApiResultUtil.parseListResult(responseEntity.getBody(), InWarehouseBillVo.class);
        return apiResult.getData();
    }

    /**
     * 根据入库单据查询条件，查询入库单据。查询列表使用，如果为空，则查询此字段所有符合条件的数据
     * @param queryAttribute
     * @return
     */
    public List<InWarehouseBillVo> queryInWarehouseBill(QueryInStorageAttribute queryAttribute)
    {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<QueryInStorageAttribute> requestEntity = new HttpEntity<QueryInStorageAttribute>(queryAttribute, requestHeaders);

        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity(URL+"instorage/query/queryByCondition", requestEntity,String.class);
        ApiResult<List<InWarehouseBillVo>> apiResult = ApiResultUtil.parseListResult(responseEntity.getBody(), InWarehouseBillVo.class);
        return apiResult.getData();
    }

    /**
     * 根据入库单据号，查询入库订单
     * @param billCode
     * @return
     */
    public InWarehouseBillVo queryWarehouseBillByBillCode(String billCode)
    {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("billCode", billCode);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(postParameters, headers);
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity(URL+"instorage/query/queryByBillCode", httpEntity,String.class);
        InWarehouseBillVo vo = ApiResultUtil.jsonToBo(responseEntity.getBody(), InWarehouseBillVo.class);
        return vo;
    }

    /**
     * 根据需求ID，查询入库订单
     * @param requirementID
     * @return
     */
    public InWarehouseBillVo queryWarehouseBillByRequirementID(String requirementID)
    {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("requirementID", requirementID);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(postParameters, headers);
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity(URL+"instorage/query/queryByRequirementID", httpEntity,String.class);
        InWarehouseBillVo vo = ApiResultUtil.jsonToBo(responseEntity.getBody(), InWarehouseBillVo.class);
        return vo;
    }
}
