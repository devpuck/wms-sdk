package com.wms.sdk.account;

import com.wms.api.account.AccountVo;
import com.wms.api.transaction.WarehouseTransactionsVo;
import com.wms.api.warehouse.WarehouseVo;
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
 * 台账查询SDK
 * @author puck
 * @date 2020/12/26 3:26 下午
 */
public class QueryAccountManager
{
    private String URL = null;

    public QueryAccountManager()
    {
        queryURL();
    }

    public String queryURL()
    {
        URL = "http://127.0.0.1:9300/";
        return URL;
    }

    public List<AccountVo> queryAccountByWarehouse(String warehouseCode, String state)
    {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("warehouseCode", warehouseCode);
        postParameters.add("state", state);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(postParameters, headers);
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity(URL+"/account/query/queryByWarehouse", httpEntity,String.class);
        ApiResult<List<AccountVo>> apiResult = ApiResultUtil.parseListResult(responseEntity.getBody(), AccountVo.class);
        return apiResult.getData();
    }

    public AccountVo querySameAccount(AccountVo accountVo)
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL+"account/query/querySameAccount", accountVo, String.class);
        AccountVo vo = ApiResultUtil.jsonToBo(responseEntity.getBody(), AccountVo.class);
        //System.out.println("VVVVVVVO:" + vo.getWarehouseCode());
        return vo;
    }

    public AccountVo queryAccountByAccountCode(String accountCode)
    {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("accountCode", accountCode);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(postParameters, headers);
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity(URL+"/account/query/queryByAccountCode", httpEntity,String.class);
        AccountVo vo = ApiResultUtil.jsonToBo(responseEntity.getBody(), AccountVo.class);
        //System.out.println("VVVVVVVO:" + vo.getWarehouseCode());
        return vo;
    }

    public AccountVo queryAccountById(String id)
    {

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate
                .getForEntity(URL+"warehouse/info/" + id, String.class);
        AccountVo vo = ApiResultUtil.jsonToBo(responseEntity.getBody(), AccountVo.class);
//        System.out.println("VVVVVVVO:" + vo.getWarehouseCode());
        return vo;
    }
}
