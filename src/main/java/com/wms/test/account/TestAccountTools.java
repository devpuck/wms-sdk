package com.wms.test.account;

import com.wms.api.account.AccountVo;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author puck
 * @date 2020/12/26 3:32 下午
 */
public class TestAccountTools
{
    public static void generateBatchData(AccountVo vo) throws ParseException
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
        vo.setProjectCode(UUID.randomUUID().toString().replaceAll("-", ""));
        vo.setContractCode(UUID.randomUUID().toString().replaceAll("-", ""));
        vo.setSupplierType("SUPPLIER");
        vo.setProductionSize("111");
        vo.setOccupyQuantity(new BigDecimal(0));
        vo.setThreePeriodCode(UUID.randomUUID().toString().replaceAll("-", ""));
        Date date = new Date();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        Date nowDate = formatter.parse(dateString);

        vo.setManufactureDate(nowDate);
        vo.setExpirationDate(nowDate);
        vo.setProductionOwnerCode(UUID.randomUUID().toString().replaceAll("-", ""));
        vo.setProductionOwnerType("SUPPLIER");
    }
}
