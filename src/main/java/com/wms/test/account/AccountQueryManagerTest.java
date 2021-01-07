package com.wms.test.account;

import com.wms.api.account.AccountVo;
import com.wms.api.batch.BatchAttribute;
import com.wms.sdk.account.InAccountManager;
import com.wms.sdk.account.QueryAccountManager;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * @author puck
 * @date 2020/12/26 3:30 下午
 */
public class AccountQueryManagerTest
{
    QueryAccountManager manager;
    InAccountManager inAccountManager;

    @Before
    public void setUp() throws Exception
    {
        manager = new QueryAccountManager();
        inAccountManager = new InAccountManager();
    }

    @Test
    public void testQueryAccountByWarehouse() throws ParseException
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
       // code = inAccountManager.accountIn(vo);
        org.junit.Assert.assertEquals(200,code);

        List<AccountVo> accountVoList = manager.queryAccountByWarehouse(warehouseCode,null);
        Iterator<AccountVo> it = accountVoList.iterator();
        while(it.hasNext())
        {
            AccountVo queryAccountAo = it.next();
            org.junit.Assert.assertEquals(warehouseCode,queryAccountAo.getWarehouseCode());
        }

        BatchAttribute batchAttribute = new BatchAttribute();
    }

    @Test
    public void querySameAccount() throws ParseException
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
        //code = inAccountManager.accountIn(vo);
        org.junit.Assert.assertEquals(200,code);

        AccountVo sameAccount = manager.querySameAccount(vo);
        org.junit.Assert.assertNotEquals(null,sameAccount);

    }
}
