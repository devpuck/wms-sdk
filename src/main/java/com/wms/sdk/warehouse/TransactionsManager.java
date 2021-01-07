package com.wms.sdk.warehouse;

import com.alibaba.fastjson.JSONObject;
import com.wms.api.transaction.WarehouseTransactionsVo;
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
public class TransactionsManager
{
    private String URL = null;

    public TransactionsManager()
    {
        queryURL();
    }

    public String queryURL()
    {
        URL = "http://127.0.0.1:8880/";
        return URL;
    }

    public int createTransactions(WarehouseTransactionsVo warehouseTransactions)
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL+"transaction/add", warehouseTransactions, String.class);
        /*        ApiResult result = ApiResultUtil.jsonToBo(responseEntity.getBody(),ApiResult.class);*/
        JSONObject json = JSONObject.parseObject(responseEntity.getBody());
        int code = Integer.parseInt(json.get("code").toString());
        System.out.println("APIRESULT:" + json.get("code"));
        return code;
    }

    public int updateTransactions(WarehouseTransactionsVo warehouseTransactions)
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL+"transaction/update", warehouseTransactions, String.class);
        /*        ApiResult result = ApiResultUtil.jsonToBo(responseEntity.getBody(),ApiResult.class);*/
        JSONObject json = JSONObject.parseObject(responseEntity.getBody());
        int code = Integer.parseInt(json.get("code").toString());
        System.out.println("APIRESULT:" + json.get("code"));
        return code;
    }

    public int deleteTransactions(String id)
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL+"transaction/delete/"+id, null, String.class);
        JSONObject json = JSONObject.parseObject(responseEntity.getBody());
        int code = Integer.parseInt(json.get("code").toString());
        System.out.println("APIRESULT:" + json.get("code"));
        return code;
    }

    public WarehouseTransactionsVo query(String workCode)
    {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("workCode", workCode);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(postParameters, headers);
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity(URL+"transaction/query/queryByCode", httpEntity,String.class);
        WarehouseTransactionsVo vo = ApiResultUtil.jsonToBo(responseEntity.getBody(), WarehouseTransactionsVo.class);
        //System.out.println("VVVVVVVO:" + vo.getWarehouseCode());
        return vo;
    }

    public List<WarehouseTransactionsVo> queryWarehouseTransactionsVoList()
    {
        RestTemplate restTemplate = new RestTemplate();
/*        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("warehouseCode", warehouseCode);
        postParameters.add("locationCode", locationCode);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(postParameters, headers);*/
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity(URL+"transaction/query/transactionsList", null,String.class);
        ApiResult<List<WarehouseTransactionsVo>> apiResult = ApiResultUtil.parseListResult(responseEntity.getBody(), WarehouseTransactionsVo.class);
        return apiResult.getData();
    }
}
