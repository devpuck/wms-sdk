package com.wms.test.config;

import com.wms.api.config.ConfigAfterDealVo;
import com.wms.sdk.account.InAccountManager;
import com.wms.sdk.account.QueryAccountManager;
import com.wms.sdk.config.AfterDealManager;
import com.wms.sdk.instorage.InWarehouseManager;
import com.wms.system.XacSystem;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

/**
 * @author puck
 * @date 2021/1/7 10:37 上午
 */
public class AfterDealManagerTest
{
    private AfterDealManager afterDealManager;

    @Before
    public void setUp() throws Exception
    {
        afterDealManager = new AfterDealManager();
    }

    @Test
    public void testCreateAfterDealManager()
    {
        ConfigAfterDealVo vo = new ConfigAfterDealVo();
        String configCode = UUID.randomUUID().toString().replaceAll("-", "");
        String systemFrom = XacSystem.SYS_SALE;
        String workCode = UUID.randomUUID().toString().replaceAll("-", "");
        String dealCode = UUID.randomUUID().toString().replaceAll("-", "");
        String rebackUrl = "http://127.0.0.1/";

        vo.setConfigCode(configCode);
        vo.setSystemFrom(systemFrom);
        vo.setWarehouseWorkCode(workCode);
        vo.setWarehouseDealCode(dealCode);
        vo.setRebackUrl(rebackUrl);

        int code = afterDealManager.createConfig(vo);
        org.junit.Assert.assertEquals(200,code);
    }
}
