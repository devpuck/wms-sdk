package com.wms.test.warehouse

import com.wms.api.warehouse.WarehouseVo
import com.wms.errorcode.ErrorCode
import com.wms.sdk.warehouse.WarehouseManager
import org.junit.Before
import org.junit.Test

/**
 * @author puck* @date 2020/12/17 9:48 上午
 */
class WarehouseManagerTest{

    public WarehouseManager manager;
    @Before
    public void setUp() throws Exception
    {
        manager = new WarehouseManager();
    }
    @Test
    void testCreateWarehouse()
    {
        String warehouseCode = UUID.randomUUID().toString().replaceAll("-", "");
        String keyID = UUID.randomUUID().toString().replaceAll("-", "");
        WarehouseVo vo = new WarehouseVo();
        vo.setWarehouseCode(warehouseCode);
        vo.setCreatedBy("1");
        vo.setKeyid(keyID);
        int code = manager.createWarehouseVo(vo);
        org.junit.Assert.assertEquals(200,code);
        code = manager.createWarehouseVo(vo);
        org.junit.Assert.assertEquals(ErrorCode.WAREHOUSE_CODE_REPEAT,code);

        //org.junit.Assert.assertEquals("1","1");
    }


    void testQueryWarehouseList()
    {

    }
    @Test
    void testUpdateWarehouseVo()
    {
        String warehouseCode = UUID.randomUUID().toString().replaceAll("-", "");
        WarehouseVo vo = new WarehouseVo();
        vo.setWarehouseCode(warehouseCode);
        int code = 0;
        code = manager.createWarehouseVo(vo);
        org.junit.Assert.assertEquals(200,code);

        WarehouseVo queryVo = manager.queryWarehouseByCode(warehouseCode);
        String Name = UUID.randomUUID().toString().replaceAll("-", "");
        queryVo.setName(Name);
        code = manager.updateWarehouseVo(queryVo);
        org.junit.Assert.assertEquals(200,code);

        WarehouseVo queryVoForTestName = manager.queryWarehouseByCode(warehouseCode);
        org.junit.Assert.assertEquals(Name,queryVoForTestName.getName());
    }

    @Test
    public void testQueryWarehouse()
    {
        String warehouseCode = UUID.randomUUID().toString().replaceAll("-", "");
        String keyID = UUID.randomUUID().toString().replaceAll("-", "");
        WarehouseVo vo = new WarehouseVo();
        vo.setWarehouseCode(warehouseCode);
        vo.setKeyid(keyID);
        int code = manager.createWarehouseVo(vo);
        org.junit.Assert.assertEquals(200,code);
        WarehouseVo queryVo = manager.queryWarehouseByCode(warehouseCode);
        org.junit.Assert.assertEquals(warehouseCode,queryVo.getWarehouseCode());
        org.junit.Assert.assertEquals(keyID,queryVo.getKeyid());
    }

    @Test
    public void testUpdateWarehouseState()
    {
        String warehouseCode = UUID.randomUUID().toString().replaceAll("-", "");
        int code = 0;
        WarehouseVo vo = new WarehouseVo();
        vo.setWarehouseCode(warehouseCode);
        code = manager.createWarehouseVo(vo);
        org.junit.Assert.assertEquals(200,code);
        WarehouseVo queryVo = manager.queryWarehouseByCode(warehouseCode);
        org.junit.Assert.assertEquals("ok",queryVo.getState());
        String state = "unUsed";
        code = manager.updateWarehouseState(warehouseCode,state);
        org.junit.Assert.assertEquals(200,code);
        WarehouseVo queryChangeVo = manager.queryWarehouseByCode(warehouseCode);
        org.junit.Assert.assertEquals(state,queryChangeVo.getState());

    }

    @Test
    public void testUpdateWarehouseConnectState()
    {
        String warehouseCode = UUID.randomUUID().toString().replaceAll("-", "");
        WarehouseVo vo = new WarehouseVo();
        vo.setWarehouseCode(warehouseCode);
        int code = manager.createWarehouseVo(vo);
        org.junit.Assert.assertEquals(200,code);
        WarehouseVo queryVo = manager.queryWarehouseByCode(warehouseCode);
        org.junit.Assert.assertEquals("offline",queryVo.getConnectState());
        String state = "online";
        code = manager.updateWarehouseConnectState(warehouseCode,state);
        org.junit.Assert.assertEquals(200,code);
        WarehouseVo queryChangeVo = manager.queryWarehouseByCode(warehouseCode);
        org.junit.Assert.assertEquals(state,queryChangeVo.getConnectState());

    }

    @Test
    public void testQueryAllWarehouseCode()
    {
        List<WarehouseVo> voList = manager.queryAllWarehouse();
        Iterator<WarehouseVo> it = voList.iterator();
        while(it.hasNext())
        {
            WarehouseVo vo = it.next();
            System.out.println(vo.getWarehouseCode());
        }

    }
}
