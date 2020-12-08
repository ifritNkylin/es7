package com.ifrit.es7.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.cluster.settings.ClusterGetSettingsRequest;
import org.elasticsearch.action.admin.cluster.settings.ClusterGetSettingsResponse;
import org.elasticsearch.action.admin.cluster.settings.ClusterUpdateSettingsRequest;
import org.elasticsearch.action.admin.cluster.settings.ClusterUpdateSettingsResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.rest.RestStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ElasticsearchUtil {
    public static final String ALIYUN_SQL_PLUGIN_NAME = "aliyun-sql";

    /**
     * ES 集群所有节点是否安装了"aliyun-sql"插件
     * @param highLevelClient
     * @return
     * @throws IOException
     */
    public static boolean aliyunSQLPluginInstalled(RestHighLevelClient highLevelClient) throws IOException {
        RestClient lowLevelClient = highLevelClient.getLowLevelClient();
        Request request = new Request(HttpGet.METHOD_NAME, "_nodes/plugins");
        Response response = lowLevelClient.performRequest(request);
        String resStr = EntityUtils.toString(response.getEntity());
        JSONObject jsonObject = JSONObject.parseObject(resStr);
        JSONObject nodes = jsonObject.getObject("nodes", JSONObject.class);
        AtomicInteger pluginInstalledNodeCount = new AtomicInteger(0);
        nodes.keySet().forEach((node) -> {
            JSONObject nodeInfo = nodes.getObject(node, JSONObject.class);
            JSONArray plugins = nodeInfo.getObject("plugins", JSONArray.class);
            plugins.forEach(plugin -> {
                if (ALIYUN_SQL_PLUGIN_NAME.equals(((JSONObject) plugin).getObject("name", String.class))) {
                    pluginInstalledNodeCount.incrementAndGet();
                }
            });
        });
        if (nodes.size() == pluginInstalledNodeCount.intValue()) {
            int i = pluginInstalledNodeCount.intValue();
            System.out.println(i);
            return true;
        }
        return false;
    }

    /**
     * "aliyun-sql"插件是否已开启
     * @param highLevelClient
     * @return 是否启用
     * @throws IOException
     */
    public static boolean isAliyunSQLPluginEnabled(RestHighLevelClient highLevelClient) throws IOException {
        ClusterGetSettingsRequest request = new ClusterGetSettingsRequest();
        ClusterGetSettingsResponse settings = highLevelClient.cluster().getSettings(request, RequestOptions.DEFAULT);
        String boolStr = settings.getPersistentSettings().get("aliyun.sql.enabled");
        return Boolean.parseBoolean(boolStr);
    }

    /**
     * 启用"aliyun-sql"插件
     * @param highLevelClient
     * @return 是否启用成功
     * @throws IOException
     */
    public static boolean enableAliyunSQLPlugin(RestHighLevelClient highLevelClient) throws IOException {
        ClusterUpdateSettingsRequest settingsRequest = new ClusterUpdateSettingsRequest();
        Map<String, Object> map = new HashMap<>();
        map.put("aliyun.sql.enabled", true);
        settingsRequest.persistentSettings(map);
        return updateClusterSettings(highLevelClient, settingsRequest);
    }

    /**
     * 更新 cluster 设置
     * @param highLevelClient
     * @param settingsRequest
     * @return 是否更新成功
     */
    public static boolean updateClusterSettings(RestHighLevelClient highLevelClient, ClusterUpdateSettingsRequest settingsRequest) {
        ClusterUpdateSettingsResponse clusterUpdateSettingsResponse;
        try {
            clusterUpdateSettingsResponse = highLevelClient.cluster().putSettings(settingsRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (ElasticsearchException e) {
            if (RestStatus.BAD_REQUEST.equals(e.status())) {
                Throwable[] suppressed = e.getSuppressed();
                if (suppressed.length > 0) {
                    Throwable throwable = suppressed[0];
                    if (throwable instanceof ResponseException) {
                        ResponseException responseException = (ResponseException) throwable;
                        Response response = responseException.getResponse();
                        String s = null;
                        try {
                            s = EntityUtils.toString(response.getEntity());
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        JSONObject jsonObject = JSONObject.parseObject(s);
                        JSONObject error = jsonObject.getObject("error", JSONObject.class);
                        Object o = error.get("type");
                        Object o1 = error.get("reason");
                        Object o2 = error.get("root_cause");
                        JSONObject status = jsonObject.getObject("status", JSONObject.class);
                    }
                }
            }
            e.printStackTrace();
            return false;
        }
        return clusterUpdateSettingsResponse.isAcknowledged();
    }
}
