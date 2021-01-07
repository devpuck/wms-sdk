package com.wms.sdk.warehouse;

import com.alibaba.fastjson.JSONObject;
import com.wms.api.warehouse.WarehouseQueryParam;
import com.wms.api.warehouse.WarehouseVo;
import com.xac.core.api.ApiResult;
import com.xac.core.util.ApiResultUtil;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author puck
 * @date 2020/12/17 9:26 上午
 */
@Component
public class WarehouseManager
{
    //  @Autowired
    //   private RestTemplate restTemplate;

    private String URL = null;

    public WarehouseManager()
    {
        queryURL();
    }

    public String queryURL()
    {
        URL = "http://127.0.0.1:8880/";
        return URL;
    }

    public WarehouseVo queryWarehouse(String id)
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate
                .getForEntity(URL+"warehouse/info/" + id, String.class);
        WarehouseVo vo = ApiResultUtil.jsonToBo(responseEntity.getBody(), WarehouseVo.class);
        System.out.println("VVVVVVVO:" + vo.getWarehouseCode());

        return vo;
    }

    public WarehouseVo queryWarehouseByCode(String code)
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate
                .getForEntity(URL+"warehouse/query/" + code, String.class);
        WarehouseVo vo = ApiResultUtil.jsonToBo(responseEntity.getBody(), WarehouseVo.class);
        //System.out.println("VVVVVVVO:" + vo.getWarehouseCode());
        return vo;
    }

    public int createWarehouseVo(WarehouseVo vo)
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL+"warehouse/add", vo, String.class);
        /*        ApiResult result = ApiResultUtil.jsonToBo(responseEntity.getBody(),ApiResult.class);*/
        JSONObject json = JSONObject.parseObject(responseEntity.getBody());
        int code = Integer.parseInt(json.get("code").toString());
        System.out.println("APIRESULT:" + json.get("code"));
        return code;

    }

    public int updateWarehouseState(String warehouseCode,String state)
    {
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("warehouseCode", warehouseCode);
        postParameters.add("state", state);

        HttpHeaders headers = new HttpHeaders();
  //      headers.add("Content-Type", "application/x-www-form-urlencoded");

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(postParameters, headers);
      //  HttpEntity<String> formEntity = new HttpEntity<String>(param.toString(),headers);

      //  ResponseEntity<String> responseEntity = restTemplate.getForEntity(URL+"warehouse/updateState?warehouseCode="+warehouseCode+"&state="+state, String.class);
        /*        ApiResult result = ApiResultUtil.jsonToBo(responseEntity.getBody(),ApiResult.class);*/
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL+"warehouse/updateState",httpEntity,String.class);
        JSONObject json = JSONObject.parseObject(responseEntity.getBody());
        int code = Integer.parseInt(json.get("code").toString());
        System.out.println("APIRESULT:" + json.get("code"));
        return code;
    }

    public int updateWarehouseConnectState(String warehouseCode,String state)
    {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
        postParameters.add("warehouseCode", warehouseCode);
        postParameters.add("state", state);

        HttpHeaders headers = new HttpHeaders();
        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(postParameters, headers);

        //ResponseEntity<String> responseEntity = restTemplate.getForEntity(URL+"warehouse/updateConnectState?warehouseCode="+warehouseCode+"&state="+state, String.class);
        /*        ApiResult result = ApiResultUtil.jsonToBo(responseEntity.getBody(),ApiResult.class);*/
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL+"warehouse/updateConnectState",httpEntity,String.class);
        JSONObject json = JSONObject.parseObject(responseEntity.getBody());
        int code = Integer.parseInt(json.get("code").toString());
        System.out.println("APIRESULT:" + json.get("code"));
        return code;
    }

    public int updateWarehouseVo(WarehouseVo vo)
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL+"warehouse/update", vo, String.class);
        JSONObject json = JSONObject.parseObject(responseEntity.getBody());
        int code = Integer.parseInt(json.get("code").toString());
        System.out.println("APIRESULT:" + json.get("code"));
        return code;
    }

    public void queryWarehouseList(WarehouseQueryParam warehouseQueryParam)
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL+"warehouse/pagelist", warehouseQueryParam, String.class);
        /*        ApiResult result = ApiResultUtil.jsonToBo(responseEntity.getBody(),ApiResult.class);*/
        JSONObject json = JSONObject.parseObject(responseEntity.getBody());
        // System.out.println("APIRESULT:"+ json.get("code"));
        System.out.println(responseEntity.getBody());

    }

    public List<WarehouseVo> queryAllWarehouse()
    {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(URL+"warehouse/queryAllWarehouse", null, String.class);
        ApiResult<List<WarehouseVo>> apiResult = ApiResultUtil.parseListResult(responseEntity.getBody(), WarehouseVo.class);
        return apiResult.getData();
    }

    public static void main(String args[])
    {
        WarehouseManager manager = new WarehouseManager();
//        manager.queryWarehouse("2");

        WarehouseVo vo = new WarehouseVo();
        vo.setWarehouseCode("123");
        manager.createWarehouseVo(vo);
/*        Long id = 2L;
        vo.setId(id);
        vo.setWarehouseCode("xxxx");
        manager.updateWarehouseVo(vo);*/

/*        WarehouseQueryParam warehouseQueryParam = new WarehouseQueryParam();
        warehouseQueryParam.setCurrent(0);
        warehouseQueryParam.setPageSize(100);
        manager.queryWarehouseList(warehouseQueryParam);*/


    }
}
