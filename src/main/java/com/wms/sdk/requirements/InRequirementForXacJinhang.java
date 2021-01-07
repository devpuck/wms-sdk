package com.wms.sdk.requirements;

import com.alibaba.fastjson.JSONObject;
import com.wms.api.requirements.InRequirementVo;
import com.wms.errorcode.ErrorCode;
import io.netty.handler.codec.json.JsonObjectDecoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 金航项目使用的sdk，主要因为金航sdk及技术栈比较老
 * @author puck
 * @date 2021/1/6 11:14 上午
 */
public class InRequirementForXacJinhang
{
    private String URL = null;

    public InRequirementForXacJinhang()
    {
        queryURL();
    }

    public String queryURL()
    {
        URL = "http://127.0.0.1:9100/";
        return URL;
    }

    public int createRequirement(InRequirementVo vo)
    {
        int code = ErrorCode.ERROR;
        String path = "";
        try
        {
            JSONObject jsonObject = (JSONObject)JSONObject.toJSON(vo);

            URL += "requirement/in/add";
            URL url = new URL(URL);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestProperty("content-Type", "application/json");

            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            printWriter.write(jsonObject.toString());
            // flush输出流的缓冲
            printWriter.flush();
            // 开始获取数据

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = reader.readLine()) != null)
            {
                buffer.append(line);
            }

            reader.close();

            JSONObject resultJson = JSONObject.parseObject(buffer.toString());
            Integer codeObj = (Integer) resultJson.get("code");
            System.out.println("XXXXXXXXX******:"+buffer.toString());
            return codeObj;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return code;
    }

}
