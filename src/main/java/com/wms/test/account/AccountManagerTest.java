package com.wms.test.account;

import com.wms.api.account.AccountCertificateVo;
import com.wms.api.account.AccountResult;
import com.wms.api.account.AccountVo;
import com.wms.api.account.InWarehouseAccountVo;
import com.wms.api.instorage.InWarehouseBillSubVo;
import com.wms.api.instorage.InWarehouseBillVo;
import com.wms.sdk.account.InAccountManager;
import com.wms.sdk.account.QueryAccountManager;
import com.wms.sdk.instorage.InWarehouseManager;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * @author puck
 * @date 2020/12/25 5:10 下午
 */
public class AccountManagerTest
{
    InAccountManager manager;
    QueryAccountManager queryManager;
    InWarehouseManager inWarehouseManager;

    @Before
    public void setUp() throws Exception
    {
        manager = new InAccountManager();
        queryManager = new QueryAccountManager();
        inWarehouseManager = new InWarehouseManager();
    }

    @Test
    public void testCreateAccount()
    {
        try
        {
            int code = 0;
            String originalOrderID = UUID.randomUUID().toString().replaceAll("-", "");
            String originalCode = UUID.randomUUID().toString().replaceAll("-", "");
            String contactCode = UUID.randomUUID().toString().replaceAll("-", "");
            String requirementCode = UUID.randomUUID().toString().replaceAll("-", "");
            String requirementID = UUID.randomUUID().toString().replaceAll("-", "");
            String warehouseCode = UUID.randomUUID().toString().replaceAll("-", "");
            String warehouseLocationCode = UUID.randomUUID().toString().replaceAll("-", "");

            String productionCode = UUID.randomUUID().toString().replaceAll("-", "");
            String productionCode2 = UUID.randomUUID().toString().replaceAll("-", "");

            String billCode = UUID.randomUUID().toString().replaceAll("-", "");

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
            vo.setBillCode(billCode);

            InWarehouseBillSubVo subVo1 = new InWarehouseBillSubVo();
            InWarehouseBillSubVo subVo2 = new InWarehouseBillSubVo();
            subVo1.setProductionCode(productionCode);
            subVo2.setProductionCode(productionCode2);
            subVo1.setBillCode(billCode);
            subVo2.setBillCode(billCode);
            subVo1.setWarehouseLocationCode(warehouseLocationCode);
            subVo2.setWarehouseLocationCode(warehouseLocationCode);
            subVo1.setQuantity(new BigDecimal(1000));
            subVo2.setQuantity(new BigDecimal(1000));

            List<InWarehouseBillSubVo> subVoList = new ArrayList<InWarehouseBillSubVo>();
            subVoList.add(subVo1);
            subVoList.add(subVo2);

            System.out.println("BILL CODE:"+billCode);

            vo.setInWarehouseBillSubVoList(subVoList);

            code = inWarehouseManager.createInWarehouseBill(vo);
            org.junit.Assert.assertEquals(200,code);

            InWarehouseBillVo queryVo = inWarehouseManager.queryWarehouseBillByBillCode(vo.getBillCode());
            org.junit.Assert.assertNotEquals(null,queryVo);

            List<InWarehouseBillSubVo> querySubVoList = queryVo.getInWarehouseBillSubVoList();
            org.junit.Assert.assertNotEquals(null,querySubVoList);

            Iterator<InWarehouseBillSubVo> it = querySubVoList.iterator();
            while(it.hasNext())
            {
                InWarehouseBillSubVo querySubVo = it.next();
                InWarehouseAccountVo inStorageVo = new InWarehouseAccountVo();
                inStorageVo.setInWarehouseBillSubVo(querySubVo);
                inStorageVo.setIncreaseQuantity(new BigDecimal(100));
                inStorageVo.setInWarehouseBillVo(queryVo);

                AccountResult accountResult = manager.createInStorageAccount(inStorageVo);
                org.junit.Assert.assertNotEquals(null,accountResult);

                List<AccountVo> accountVoList = accountResult.getAccountVoList();
                List<AccountCertificateVo> accountCertificateVoList = accountResult.getAccountCertificateVoList();
                AccountVo accountVo = accountVoList.get(0);
                AccountCertificateVo accountCertificateVo = accountCertificateVoList.get(0);

                org.junit.Assert.assertEquals(querySubVo.getProductionCode(),accountVo.getProductionCode());
                org.junit.Assert.assertEquals(accountCertificateVo.getProductionCode(),accountVo.getProductionCode());
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Test
    public void testMergeByID()
    {
        try
        {
            int code = 0;
            AccountVo vo = new AccountVo();
            String accountCode = UUID.randomUUID().toString().replaceAll("-", "");
            TestAccountTools.generateBatchData(vo);

            String warehouseCode = UUID.randomUUID().toString().replaceAll("-", "");
            String warehouseLocationCode = UUID.randomUUID().toString().replaceAll("-", "");
            String productionCode = UUID.randomUUID().toString().replaceAll("-", "");
            vo.setWarehouseCode(warehouseCode);
            vo.setWarehouseLocationCode(warehouseLocationCode);
            vo.setAccountCode(accountCode);
            vo.setProductionCode(productionCode);

            vo.setQuantity(new BigDecimal(1000));
            //code = manager.accountIn(vo);
            org.junit.Assert.assertEquals(200,code);

            AccountVo sameAccount = queryManager.querySameAccount(vo);
            org.junit.Assert.assertNotEquals(null,sameAccount);
            org.junit.Assert.assertEquals(new BigDecimal(1000),sameAccount.getQuantity());

            String accountID = sameAccount.getId().toString();
            System.out.println("ACCOUNTID:"+accountID);
            code = manager.mergeByID(accountID,"1",new BigDecimal(1000));
            org.junit.Assert.assertEquals(200,code);

            AccountVo sameAccount2 = queryManager.querySameAccount(vo);
            org.junit.Assert.assertNotEquals(null,sameAccount2);
            org.junit.Assert.assertEquals(200,code);
            org.junit.Assert.assertEquals(new BigDecimal(2000),sameAccount2.getQuantity());

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
