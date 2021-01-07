package com.wms.test.instorage;

import com.wms.api.instorage.InWarehouseBillSubVo;
import com.wms.api.instorage.InWarehouseBillVo;
import com.wms.api.instorage.query.QueryInStorageAttribute;
import com.wms.sdk.instorage.InWarehouseManager;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * @author puck
 * @date 2020/12/24 4:05 下午
 */
public class InWarehouseManagerTest
{
    InWarehouseManager manager;

    @Before
    public void setUp() throws Exception
    {
        manager = new InWarehouseManager();
    }

    @Test
    public void testInWarehouse()
    {
        int code = 0;
        String originalOrderID = UUID.randomUUID().toString().replaceAll("-", "");
        String originalCode = UUID.randomUUID().toString().replaceAll("-", "");
        String contactCode = UUID.randomUUID().toString().replaceAll("-", "");
        String requirementCode = UUID.randomUUID().toString().replaceAll("-", "");
        String requirementID = UUID.randomUUID().toString().replaceAll("-", "");
        String warehouseCode = UUID.randomUUID().toString().replaceAll("-", "");

        String productionCode = UUID.randomUUID().toString().replaceAll("-", "");
        String productionCode2 = UUID.randomUUID().toString().replaceAll("-", "");

        String originalFrom = "SALE";
        String transactionCode = "CGRK";
        String dealCode = "CONTRACT_DELIVER";

        InWarehouseBillVo vo = new InWarehouseBillVo();
        vo.setDealCode(dealCode);
        vo.setRequirementId(requirementID);
        vo.setWorkCode(transactionCode);
        vo.setWarehouseCode(warehouseCode);
        vo.setSystemFrom(originalFrom);
        vo.setContractCode(contactCode);

        InWarehouseBillSubVo subVo1 = new InWarehouseBillSubVo();
        InWarehouseBillSubVo subVo2 = new InWarehouseBillSubVo();
        subVo1.setProductionCode(productionCode);
        subVo2.setProductionCode(productionCode2);

        List<InWarehouseBillSubVo> subVoList = new ArrayList<InWarehouseBillSubVo>();
        subVoList.add(subVo1);
        subVoList.add(subVo2);

        vo.setInWarehouseBillSubVoList(subVoList);

        code = manager.createInWarehouseBill(vo);
        org.junit.Assert.assertEquals(200,code);


    }

    @Test
    public void testQueryInBillByProduction()
    {
        int code = 0;
        String originalOrderID = UUID.randomUUID().toString().replaceAll("-", "");
        String originalCode = UUID.randomUUID().toString().replaceAll("-", "");
        String contactCode = UUID.randomUUID().toString().replaceAll("-", "");
        String requirementCode = UUID.randomUUID().toString().replaceAll("-", "");
        String requirementID = UUID.randomUUID().toString().replaceAll("-", "");
        String warehouseCode = UUID.randomUUID().toString().replaceAll("-", "");

        String productionCode = UUID.randomUUID().toString().replaceAll("-", "");
        String productionCode2 = UUID.randomUUID().toString().replaceAll("-", "");

        String originalFrom = "SALE";
        String transactionCode = "CGRK";
        String dealCode = "CONTRACT_DELIVER";

        InWarehouseBillVo vo = new InWarehouseBillVo();
        vo.setDealCode(dealCode);
        vo.setRequirementId(requirementID);
        vo.setWorkCode(transactionCode);
        vo.setWarehouseCode(warehouseCode);
        vo.setSystemFrom(originalFrom);
        vo.setContractCode(contactCode);

        InWarehouseBillSubVo subVo1 = new InWarehouseBillSubVo();
        InWarehouseBillSubVo subVo2 = new InWarehouseBillSubVo();
        subVo1.setProductionCode(productionCode);
        subVo2.setProductionCode(productionCode2);

        List<InWarehouseBillSubVo> subVoList = new ArrayList<InWarehouseBillSubVo>();
        subVoList.add(subVo1);
        subVoList.add(subVo2);

        vo.setInWarehouseBillSubVoList(subVoList);

        code = manager.createInWarehouseBill(vo);
        org.junit.Assert.assertEquals(200,code);

        List<InWarehouseBillVo> inWarehouseBillVoList = manager.queryInWarehouseBillByProduction(productionCode,"create");
        System.out.println("XXXXXX:"+inWarehouseBillVoList.size());
        Iterator<InWarehouseBillVo> it = inWarehouseBillVoList.iterator();
        while(it.hasNext())
        {
            InWarehouseBillVo queryInBill = it.next();
            org.junit.Assert.assertEquals(warehouseCode,queryInBill.getWarehouseCode());
        }
    }

    @Test
    public void testQueryByCondition()
    {
        QueryInStorageAttribute queryAttribute = new QueryInStorageAttribute();
        queryAttribute.setWarehouseCode(UUID.randomUUID().toString().replaceAll("-", ""));
//        queryAttribute.set
        queryAttribute.setContractCode(UUID.randomUUID().toString().replaceAll("-", ""));
        List<InWarehouseBillVo> inWarehouseBillVoList = manager.queryInWarehouseBill(queryAttribute);

    }

    public void generateBatchData(InWarehouseBillSubVo vo)
    {
        vo.setAircraftCode(UUID.randomUUID().toString().replaceAll("-", ""));
        vo.setBatch(UUID.randomUUID().toString().replaceAll("-", ""));
        vo.setAttribute1(UUID.randomUUID().toString().replaceAll("-", ""));
        vo.setAttribute2(UUID.randomUUID().toString().replaceAll("-", ""));
        vo.setAttribute3(UUID.randomUUID().toString().replaceAll("-", ""));
        vo.setAttribute4(UUID.randomUUID().toString().replaceAll("-", ""));
        vo.setAttribute5(UUID.randomUUID().toString().replaceAll("-", ""));
        vo.setSplysotCode(UUID.randomUUID().toString().replaceAll("-", ""));
        vo.setSortieCode(UUID.randomUUID().toString().replaceAll("-", ""));
        vo.setModelCode(UUID.randomUUID().toString().replaceAll("-", ""));
        vo.setConstructionCode(UUID.randomUUID().toString().replaceAll("-", ""));
        vo.setQualityCode(UUID.randomUUID().toString().replaceAll("-", ""));
        vo.setSupplyCertificateNo(UUID.randomUUID().toString().replaceAll("-", ""));
        vo.setProjectCode(UUID.randomUUID().toString().replaceAll("-", ""));

    }
}
