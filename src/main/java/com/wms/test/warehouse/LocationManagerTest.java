package com.wms.test.warehouse;

import com.wms.api.location.LocationProductionVo;
import com.wms.api.location.WarehouseLocationVo;
import com.wms.errorcode.ErrorCode;
import com.wms.sdk.warehouse.LocationManager;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

/**
 * @author puck
 * @date 2020/12/18 3:13 下午
 */
public class LocationManagerTest
{
    public LocationManager manager = null;

    @Before
    public void setUp() throws Exception
    {
        manager = new LocationManager();
    }

    @Test
    public void testCreateLocation()
    {
        String warehouseCode =  UUID.randomUUID().toString().replaceAll("-", "");
        String locationCode =  UUID.randomUUID().toString().replaceAll("-", "");
        WarehouseLocationVo vo = new WarehouseLocationVo();
        vo.setWarehouseCode(warehouseCode);
        vo.setLocationCode(locationCode);

        int code = 0;
        code = manager.createLocation(vo);
        org.junit.Assert.assertEquals(200,code);
        code = manager.createLocation(vo);
        org.junit.Assert.assertEquals(ErrorCode.WAREHOUSE_LOCATION_CODE_REPEAT,code);
    }

    @Test
    public void testQueryLocation()
    {
        String warehouseCode =  UUID.randomUUID().toString().replaceAll("-", "");
        String locationCode =  UUID.randomUUID().toString().replaceAll("-", "");
        WarehouseLocationVo vo = new WarehouseLocationVo();
        vo.setWarehouseCode(warehouseCode);
        vo.setLocationCode(locationCode);

        int code = 0;
        code = manager.createLocation(vo);
        org.junit.Assert.assertEquals(200,code);

        WarehouseLocationVo queryVo = manager.queryWarehouseLocationInfo(warehouseCode,locationCode);
        org.junit.Assert.assertEquals(warehouseCode,queryVo.getWarehouseCode());
    }

    @Test
    public void testUpdateLocation()
    {
        String warehouseCode =  UUID.randomUUID().toString().replaceAll("-", "");
        String locationCode =  UUID.randomUUID().toString().replaceAll("-", "");
        WarehouseLocationVo vo = new WarehouseLocationVo();
        vo.setWarehouseCode(warehouseCode);
        vo.setLocationCode(locationCode);

        int code = 0;
        code = manager.createLocation(vo);
        org.junit.Assert.assertEquals(200,code);

        WarehouseLocationVo queryVo = manager.queryWarehouseLocationInfo(warehouseCode,locationCode);
        queryVo.setWidth(new BigDecimal(100));
        code = manager.updateLocation(queryVo);

        WarehouseLocationVo queryVo2 = manager.queryWarehouseLocationInfo(warehouseCode,locationCode);
        org.junit.Assert.assertEquals(new BigDecimal(100),queryVo2.getWidth());
    }

    @Test
    public void testUpdateLocationState()
    {
        String warehouseCode =  UUID.randomUUID().toString().replaceAll("-", "");
        String locationCode =  UUID.randomUUID().toString().replaceAll("-", "");
        WarehouseLocationVo vo = new WarehouseLocationVo();
        vo.setWarehouseCode(warehouseCode);
        vo.setLocationCode(locationCode);

        int code = 0;
        code = manager.createLocation(vo);
        org.junit.Assert.assertEquals(200,code);

        String state = "unUsed";
        code = manager.updateLocationState(warehouseCode,locationCode,state);
        org.junit.Assert.assertEquals(200,code);

        WarehouseLocationVo queryVo = manager.queryWarehouseLocationInfo(warehouseCode,locationCode);
        org.junit.Assert.assertEquals(state,queryVo.getState());

    }

    @Test
    public void queryWarehouseLocation()
    {
        boolean flag = false;
        String warehouseCode =  UUID.randomUUID().toString().replaceAll("-", "");
        String locationCode =  UUID.randomUUID().toString().replaceAll("-", "");
        WarehouseLocationVo vo = new WarehouseLocationVo();
        vo.setWarehouseCode(warehouseCode);
        vo.setLocationCode(locationCode);

        int code = 0;
        code = manager.createLocation(vo);
        org.junit.Assert.assertEquals(200,code);

        List<WarehouseLocationVo> voList = manager.queryWarehouseLocation(warehouseCode);
        Iterator<WarehouseLocationVo> it = voList.iterator();
        while (it.hasNext())
        {
            WarehouseLocationVo location = it.next();
            if(locationCode.equals(location.getLocationCode()))
            {
                flag = true;
            }
        }
        org.junit.Assert.assertEquals(true,flag);
    }

    @Test
    public void testLocationBindProduction()
    {
        String warehouseCode =  UUID.randomUUID().toString().replaceAll("-", "");
        String locationCode =  UUID.randomUUID().toString().replaceAll("-", "");
        String productionCode =  UUID.randomUUID().toString().replaceAll("-", "");

        LocationProductionVo vo = new LocationProductionVo();
        vo.setWarehouseCode(warehouseCode);
        vo.setWarehouseLocationCode(locationCode);
        vo.setProductionCode(productionCode);

        int code = 0;
        code = manager.bindProduction(vo);
        org.junit.Assert.assertEquals(200,code);
    }

    @Test
    public void testQueryLocationBindProduction()
    {
        String warehouseCode =  UUID.randomUUID().toString().replaceAll("-", "");
        String locationCode =  UUID.randomUUID().toString().replaceAll("-", "");
        String productionCode =  UUID.randomUUID().toString().replaceAll("-", "");

        LocationProductionVo vo = new LocationProductionVo();
        vo.setWarehouseCode(warehouseCode);
        vo.setWarehouseLocationCode(locationCode);
        vo.setProductionCode(productionCode);

        int code = 0;
        code = manager.bindProduction(vo);
        org.junit.Assert.assertEquals(200,code);

        List<LocationProductionVo> voList = manager.queryLocationProductionInfo(warehouseCode,locationCode);
        Iterator<LocationProductionVo> it = voList.iterator();
        while(it.hasNext())
        {
            LocationProductionVo queryVo = it.next();
            org.junit.Assert.assertEquals(productionCode,queryVo.getProductionCode());
        }
    }

    @Test
    public void testUnbindProduction()
    {
        String warehouseCode =  UUID.randomUUID().toString().replaceAll("-", "");
        String locationCode =  UUID.randomUUID().toString().replaceAll("-", "");
        String productionCode =  UUID.randomUUID().toString().replaceAll("-", "");

        LocationProductionVo vo = new LocationProductionVo();
        vo.setWarehouseCode(warehouseCode);
        vo.setWarehouseLocationCode(locationCode);
        vo.setProductionCode(productionCode);

        int code = 0;
        code = manager.bindProduction(vo);
        org.junit.Assert.assertEquals(200,code);

        List<LocationProductionVo> voList = manager.queryLocationProductionInfo(warehouseCode,locationCode);
        Iterator<LocationProductionVo> it = voList.iterator();
        while(it.hasNext())
        {
            LocationProductionVo queryVo = it.next();
            org.junit.Assert.assertEquals(productionCode,queryVo.getProductionCode());
            code = manager.unBindProduction(queryVo.getId().toString());
            org.junit.Assert.assertEquals(200,code);
        }

        List<LocationProductionVo> voList2 = manager.queryLocationProductionInfo(warehouseCode,locationCode);
        org.junit.Assert.assertEquals(0,voList2.size());
    }

    @Test
    public void testQueryProductionBindLocation()
    {
        String warehouseCode =  UUID.randomUUID().toString().replaceAll("-", "");
        String locationCode =  UUID.randomUUID().toString().replaceAll("-", "");
        String productionCode =  UUID.randomUUID().toString().replaceAll("-", "");

        LocationProductionVo vo = new LocationProductionVo();
        vo.setWarehouseCode(warehouseCode);
        vo.setWarehouseLocationCode(locationCode);
        vo.setProductionCode(productionCode);

        int code = 0;
        code = manager.bindProduction(vo);
        org.junit.Assert.assertEquals(200,code);
        List<LocationProductionVo> voList = manager.queryProductionLocation(locationCode);
        Iterator<LocationProductionVo> it = voList.iterator();
        while(it.hasNext())
        {
            LocationProductionVo queryVo = it.next();
            org.junit.Assert.assertEquals(locationCode,queryVo.getWarehouseLocationCode());
        }
    }
}
