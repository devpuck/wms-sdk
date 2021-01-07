package com.wms.test.jinhang;

import com.wms.api.requirements.InRequirementSubVo;
import com.wms.api.requirements.InRequirementVo;
import com.wms.sdk.account.InAccountForXacJinhang;
import com.wms.sdk.instorage.InWarehouseManager;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author puck
 * @date 2021/1/6 2:59 下午
 */
public class JInhang
{
    InAccountForXacJinhang manager;

    @Before
    public void setUp() throws Exception
    {
        manager = new InAccountForXacJinhang();
    }

    @Test
    public void testCreateInRequirement()
    {
        int code = 0;

        InRequirementVo vo = new InRequirementVo();
        String warehouseCode = UUID.randomUUID().toString().replaceAll("-", "");
        String originalOrderID = UUID.randomUUID().toString().replaceAll("-", "");
        String originalCode = UUID.randomUUID().toString().replaceAll("-", "");
        String contactCode = UUID.randomUUID().toString().replaceAll("-", "");
        String requirementCode = UUID.randomUUID().toString().replaceAll("-", "");
        String productionCode = UUID.randomUUID().toString().replaceAll("-", "");
        String productionCode2 = UUID.randomUUID().toString().replaceAll("-", "");

        String originalFrom = "SALE";
        String transactionCode = "CGRK";
        String dealCode = "CONTRACT_DELIVER";

        vo.setOriginalOrderId(originalOrderID);
        vo.setOriginalFrom(originalFrom);
        vo.setRequirementCode(requirementCode);
        vo.setOriginalCode(originalCode);
        vo.setContractCode(contactCode);
        vo.setRequirementType(transactionCode);
        vo.setOriginalType(dealCode);
        vo.setWarehouseCode(warehouseCode);

        InRequirementSubVo subVo1 = new InRequirementSubVo();
        subVo1.setRequirementCode(requirementCode);
        subVo1.setProductionCode(productionCode);
        subVo1.setReceiveQuantity(new BigDecimal(100.32));
        subVo1.setBuyPrice(new BigDecimal(10000));
        generateBatchData(subVo1);

        InRequirementSubVo subVo2 = new InRequirementSubVo();
        subVo2.setRequirementCode(requirementCode);
        subVo2.setProductionCode(productionCode2);
        subVo2.setReceiveQuantity(new BigDecimal(200));
        subVo2.setBuyPrice(new BigDecimal(10000));
        generateBatchData(subVo2);

        List<InRequirementSubVo> subVoList = new ArrayList<InRequirementSubVo>();
        subVoList.add(subVo1);
        subVoList.add(subVo2);
        vo.setInRequirementSubVoList(subVoList);

        code = manager.createRequirement(vo);
        org.junit.Assert.assertEquals(200,code);
    }

    public void generateBatchData(InRequirementSubVo vo)
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
        vo.setSupplierBy(UUID.randomUUID().toString().replaceAll("-", ""));
        vo.setSupplierType("SUPPLIER");
        vo.setWeight(new BigDecimal(100));
        vo.setWeightUnit("kg");
        vo.setProductionSize("111");
    }
}
