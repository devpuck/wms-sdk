package com.wms.test.warehouse;

import com.wms.api.autoconfig.AutoWarehouseConfigVo;
import com.wms.api.warehouse.WarehouseVo;
import com.wms.sdk.warehouse.AutoWarehouseConfigManager;
import com.wms.sdk.warehouse.WarehouseManager;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

/**
 * @author puck
 * @date 2020/12/17 4:17 下午
 */
public class AutoWarehouseConfigManagerTest
{
    AutoWarehouseConfigManager manager = null;
    WarehouseManager whManager;
    @Before
    public void setUp() throws Exception
    {
        manager = new AutoWarehouseConfigManager();
        whManager = new WarehouseManager();
    }

    @Test
    public void testUpdate()
    {
        String warehouseCode = UUID.randomUUID().toString().replaceAll("-", "");
        WarehouseVo vo = new WarehouseVo();
        vo.setWarehouseCode(warehouseCode);
        int code = whManager.createWarehouseVo(vo);
        org.junit.Assert.assertEquals(200,code);

        AutoWarehouseConfigVo configVo = new AutoWarehouseConfigVo();
        configVo.setWarehouseCode(warehouseCode);
        code = manager.updateAutoConfig(configVo);
        org.junit.Assert.assertEquals(200,code);
    }

    @Test
    public void queryConfig()
    {
        String warehouseCode = UUID.randomUUID().toString().replaceAll("-", "");
        WarehouseVo vo = new WarehouseVo();
        vo.setWarehouseCode(warehouseCode);
        int code = whManager.createWarehouseVo(vo);
        org.junit.Assert.assertEquals(200,code);

        AutoWarehouseConfigVo configVo = new AutoWarehouseConfigVo();
        configVo.setWarehouseCode(warehouseCode);
        code = manager.updateAutoConfig(configVo);
        org.junit.Assert.assertEquals(200,code);

        AutoWarehouseConfigVo queryConfigVo = manager.queryAutoConfig(warehouseCode);
        String appKey = queryConfigVo.getAppKey();
        String appSecret = queryConfigVo.getAppSecret();
        System.out.println("APPKEY:"+appKey);
        System.out.println("APPSECRET:"+appSecret);
    }

    @Test
    public void reCreateAppSecret()
    {
        String warehouseCode = UUID.randomUUID().toString().replaceAll("-", "");
        WarehouseVo vo = new WarehouseVo();
        vo.setWarehouseCode(warehouseCode);
        int code = whManager.createWarehouseVo(vo);
        org.junit.Assert.assertEquals(200,code);

        AutoWarehouseConfigVo configVo = new AutoWarehouseConfigVo();
        configVo.setWarehouseCode(warehouseCode);
        code = manager.updateAutoConfig(configVo);
        org.junit.Assert.assertEquals(200,code);

        AutoWarehouseConfigVo queryConfigVo = manager.queryAutoConfig(warehouseCode);
        String appKey = queryConfigVo.getAppKey();
        String appSecret = queryConfigVo.getAppSecret();
        System.out.println("APPKEY:"+appKey);
        System.out.println("APPSECRET:"+appSecret);

        code = manager.reCreateAppSecret(warehouseCode);
        AutoWarehouseConfigVo queryConfigVo2 = manager.queryAutoConfig(warehouseCode);
        String afterKey = queryConfigVo2.getAppKey();
        String afterappSecret = queryConfigVo2.getAppSecret();
        org.junit.Assert.assertNotEquals(appSecret,afterappSecret);

    }
}
