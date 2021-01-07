package com.wms.test.warehouse;

import com.wms.api.deal.WarehouseDealsVo;
import com.wms.api.transaction.WarehouseTransactionsVo;
import com.wms.errorcode.ErrorCode;
import com.wms.sdk.warehouse.TransactionsManager;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * @author puck
 * @date 2020/12/21 7:11 下午
 */
public class TransactionManagerTest
{
    public TransactionsManager manager = null;

    @Before
    public void setUp() throws Exception
    {
        manager = new TransactionsManager();
    }

    @Test
    public void testCreateDeals()
    {
        WarehouseTransactionsVo vo = new WarehouseTransactionsVo();
        String transactionCode = UUID.randomUUID().toString().replaceAll("-", "");
        vo.setWorkCode(transactionCode);
        vo.setTransactionsType("in");
        int code = 0;
        code = manager.createTransactions(vo);
        org.junit.Assert.assertEquals(200,code);
        code = manager.createTransactions(vo);
        org.junit.Assert.assertEquals(ErrorCode.WAREHOUSE_TRANSACTIONS_CODE_REPEAT,code);
    }

    @Test
    public void testQueryDeals()
    {
        WarehouseTransactionsVo vo = new WarehouseTransactionsVo();
        String transactionCode = UUID.randomUUID().toString().replaceAll("-", "");
        vo.setWorkCode(transactionCode);
        vo.setTransactionsType("in");
        int code = 0;
        code = manager.createTransactions(vo);
        org.junit.Assert.assertEquals(200,code);

        WarehouseTransactionsVo dealsVo = manager.query(transactionCode);
        org.junit.Assert.assertEquals(transactionCode,vo.getWorkCode());

    }

    @Test
    public void testQueryList()
    {
        WarehouseTransactionsVo vo = new WarehouseTransactionsVo();
        String transactionCode = UUID.randomUUID().toString().replaceAll("-", "");
        vo.setWorkCode(transactionCode);
        vo.setTransactionsType("in");
        int code = 0;
        code = manager.createTransactions(vo);
        org.junit.Assert.assertEquals(200,code);

        List<WarehouseTransactionsVo> voList = manager.queryWarehouseTransactionsVoList();
        Iterator<WarehouseTransactionsVo> it = voList.iterator();
        boolean flag = false;
        while(it.hasNext())
        {
            WarehouseTransactionsVo queryVo = it.next();
            if(transactionCode.equals(queryVo.getWorkCode()))
            {
                flag = true;
            }
        }
        org.junit.Assert.assertEquals(true,flag);

    }

    @Test
    public void testCreateDate()
    {
        WarehouseTransactionsVo vo = new WarehouseTransactionsVo();
        String transactionCode = "CGRK";
        String name = "采购入库";
        vo.setWorkCode(transactionCode);
        vo.setTransactionsType("in");
        vo.setName(name);
        int code = 0;
        code = manager.createTransactions(vo);
    }

}
