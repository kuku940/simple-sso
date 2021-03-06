package cn.xiaoyu.simple.sso.client.util;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HTTPUtil {
    /**
     * 向目标url发送post请求
     */
    public static boolean post(String url, Map<String, String> params) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        // 参数处理
        if (params != null && !params.isEmpty()) {
            List<NameValuePair> list = new ArrayList<>();

            Iterator<Entry<String, String>> it = params.entrySet().iterator();
            while (it.hasNext()) {
                Entry<String, String> entry = it.next();
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }

            httpPost.setEntity(new UrlEncodedFormEntity(list, Consts.UTF_8));
        }
        // 执行请求
        try {
            CloseableHttpResponse response = httpclient.execute(httpPost);
            response.getStatusLine().getStatusCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void main(String[] args) {
        Map<String, String> params = new HashMap<>();
        params.put("clientUrl", "httputil");
        post("http://localhost:8080/", params);
    }
}