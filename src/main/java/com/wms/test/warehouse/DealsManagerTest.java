package com.wms.test.warehouse;

import com.wms.api.deal.WarehouseDealsVo;
import com.wms.errorcode.ErrorCode;
import com.wms.sdk.warehouse.DealsManager;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * @author puck
 * @date 2020/12/21 7:11 下午
 */
public class DealsManagerTest
{
    public DealsManager manager = null;

    @Before
    public void setUp() throws Exception
    {
        manager = new DealsManager();
    }

    @Test
    public void testCreateDeals()
    {
        WarehouseDealsVo warehouseDeals = new WarehouseDealsVo();
        String dealCode = UUID.randomUUID().toString().replaceAll("-", "");
        warehouseDeals.setDealCode(dealCode);
        int code = 0;
        code = manager.createDeal(warehouseDeals);
        org.junit.Assert.assertEquals(200,code);
        code = manager.createDeal(warehouseDeals);
        org.junit.Assert.assertEquals(ErrorCode.WAREHOUSE_DEAL_CODE_REPEAT,code);
    }

    @Test
    public void testQueryDeals()
    {
        WarehouseDealsVo warehouseDeals = new WarehouseDealsVo();
        String dealCode = UUID.randomUUID().toString().replaceAll("-", "");
        warehouseDeals.setDealCode(dealCode);
        int code = 0;
        code = manager.createDeal(warehouseDeals);
        org.junit.Assert.assertEquals(200,code);

        WarehouseDealsVo dealsVo = manager.query(dealCode);
        org.junit.Assert.assertEquals(dealCode,dealsVo.getDealCode());

    }

    @Test
    public void testQueryList()
    {
        WarehouseDealsVo warehouseDeals = new WarehouseDealsVo();
        String dealCode = UUID.randomUUID().toString().replaceAll("-", "");
        warehouseDeals.setDealCode(dealCode);
        int code = 0;
        code = manager.createDeal(warehouseDeals);
        org.junit.Assert.assertEquals(200,code);

        List<WarehouseDealsVo> voList = manager.queryWarehouseDeasList();
        Iterator<WarehouseDealsVo> it = voList.iterator();
        boolean flag = false;
        while(it.hasNext())
        {
            WarehouseDealsVo queryVo = it.next();
            if(dealCode.equals(queryVo.getDealCode()))
            {
                flag = true;
            }
        }
        org.junit.Assert.assertEquals(true,flag);

    }

    @Test
    public void testCreateData()
    {
        WarehouseDealsVo warehouseDeals = new WarehouseDealsVo();
        String dealCode = "CONTRACT_DELIVER";
        String name = "合同到货";
        warehouseDeals.setDealCode(dealCode);
        warehouseDeals.setName(name);
        int code = 0;
        code = manager.createDeal(warehouseDeals);
    }

}
