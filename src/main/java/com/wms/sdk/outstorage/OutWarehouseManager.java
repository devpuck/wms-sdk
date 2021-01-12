package com.wms.sdk.outstorage;

import com.alibaba.fastjson.JSONObject;
import com.wms.api.outstorage.OutWarehouseBillVo;
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
public class OutWarehouseManager
{
    private String URL = null;

    public OutWarehouseManager()
    {
        queryURL();
    }

    public String queryURL()
    {
        URL = "http://127.0.0.1:9500/";
        return URL;
    }

    /**
     * 创建出库单据
     * @param vo
     * @return
     */
    public int createOutWarehouseBill(OutWarehouseBillVo vo)
    {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OutWarehouseBillVo> requestEntity = new HttpEntity<OutWarehouseBillVo>(vo, requestHeaders);

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL+"outstorage/add", requestEntity, String.class);
        /*        ApiResult result = ApiResultUtil.jsonToBo(responseEntity.getBody(),ApiResult.class);*/
        JSONObject json = JSONObject.parseObject(responseEntity.getBody());
        int code = Integer.parseInt(json.get("code").toString());
        /*        System.out.println("APIRESULT:" + json.get("code"));*/
        return code;
    }

    /**
     * 根据入库单据号，查询入库订单
     * @param billCode
     * @return
     */
    public OutWarehouseBillVo queryWarehouseBillByBillCode(String billCode)
    {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("billCode", billCode);

        HttpHeaders headers = new HttpHeaders();

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(postParameters, headers);
        ResponseEntity<String> responseEntity = restTemplate
                .postForEntity(URL+"instorage/query/queryByBillCode", httpEntity,String.class);
        OutWarehouseBillVo vo = ApiResultUtil.jsonToBo(responseEntity.getBody(), OutWarehouseBillVo.class);
        return vo;
    }

}
