package com.ifrit.es7.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ifrit.es7.service.ElasticsearchDataAccessor;
import com.ifrit.es7.service.ElasticsearchUtil;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.cluster.settings.ClusterGetSettingsRequest;
import org.elasticsearch.action.admin.cluster.settings.ClusterGetSettingsResponse;
import org.elasticsearch.action.admin.cluster.settings.ClusterUpdateSettingsRequest;
import org.elasticsearch.action.admin.cluster.settings.ClusterUpdateSettingsResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.rest.RestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("es")
public class EsControl {

    public static final String ALISQL = "/_alisql";
    public static final String SQL = "/_sql";
    public static final String CONSOLE_RED_FONT = "\033[31m";
    public static final String ALIYUN_SQL_PLUGIN_NAME = "aliyun-sql";

    public static Map<String, Object> resMap = new LinkedHashMap<>();

    @Qualifier("elasticClient")
    @Autowired
    private RestHighLevelClient highLevelClient;

    @Autowired
    ElasticsearchDataAccessor accessor;

    @GetMapping("test")
    public Object testES() throws IOException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        resMap.clear();
//        testXpackJdbc();

//        esApiTest();

//        trySQL();
//        MainResponse info = highLevelClient.info(RequestOptions.DEFAULT);
//        return LocalDateTime.now();

//        aliyunSQLPluginInstalled();


//        test();
//        insertData();
        createIndex();
        return JSONObject.toJSONString(resMap);
    }

    public void createIndex() throws IOException {

        Settings.Builder builder = Settings.builder().put("index.number_of_shards", 1).put("index.number_of_replicas", 1);
        Map<String, Object> mapping = JSONObject.parseObject(AAA.tableMapping, Map.class);

        String indexName = "aaaa_" + LocalDateTime.now().toString().replace(":", "").replace(".", "").replace("T","").replace("-", "");
//        String indexName = "xstest_es_test";
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~             " + indexName);
        CreateIndexRequest request = new CreateIndexRequest(indexName)
                .settings(builder)
                .mapping(mapping);
        request.setTimeout(TimeValue.timeValueMinutes(2));// 设置超时时间，可选
        System.out.println(JSON.toJSONString(request));
        CreateIndexResponse createIndexResponse = highLevelClient.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);
        resMap.put("a", indexName);
    }

    public void insertData()  {
        try {

            String s = "{\"name\":\"name1\",\"notes\":\"A\",\"notes2\":\"notesA\",\"amount\":1111111}";
            IndexRequest requestsssss = new IndexRequest("product");
            requestsssss.source(JSONObject.parseObject(s));
            requestsssss.id(UUID.randomUUID().toString().replace("-", ""));
            IndexResponse indexssss = highLevelClient.index(requestsssss, RequestOptions.DEFAULT);

            String aaa = "{\"name\":\"name1\",\"notes\":\"A\",\"notes2\":\"notesA\",\"amount\":1111111}";
            IndexRequest request = new IndexRequest("product");
            request.source(JSONObject.parseObject(aaa));
//            request.id(UUID.randomUUID().toString().replace("-", ""));
            request.id("2");
            IndexResponse index = highLevelClient.index(request, RequestOptions.DEFAULT);



//            String bbb = "{\"name\":\"name1\",\"notes\":\"A\",\"notes2\":\"notesA\",\"amount\":\"asgdhahsljhdjkas\"}";
            String bbb = "{\"name\":\"name1\",\"notes\":\"A\",\"notes2\":\"notesA\",\"amount\":22}";
            IndexRequest request2 = new IndexRequest("product");
            request2.source(JSONObject.parseObject(bbb));
//            request2.id(UUID.randomUUID().toString().replace("-", ""));
            request2.id("2");
            IndexResponse index2 = highLevelClient.index(request2, RequestOptions.DEFAULT);
            System.out.println(index.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ElasticsearchException e) {
            e.printStackTrace();
        }

    }

    private void test() throws IOException {
        getClusterSetting();
        try {
//            启用"aliyun-sql"插件
            if (ElasticsearchUtil.aliyunSQLPluginInstalled(highLevelClient)
                    && !ElasticsearchUtil.isAliyunSQLPluginEnabled(highLevelClient)) {
                ElasticsearchUtil.enableAliyunSQLPlugin(highLevelClient);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        getClusterSetting();
    }

    private boolean aliyunSQLPluginInstalled() throws IOException {
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
            resMap.put("aliyunSQLPluginInstalled", true);
            return true;
        }
        resMap.put("aliyunSQLPluginInstalled", true);
        return false;
    }

    private void esApiTest() throws IOException {
        String indexName = "library";

/*        GetIndexRequest request = new GetIndexRequest(indexName);
        GetIndexResponse getIndexResponse = restHighLevelClient.indices().get(request, RequestOptions.DEFAULT);*/

/*        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        String s = JSONObject.toJSONString(search.getHits().getHits());*/

/*//        get mappings
        GetMappingsRequest getMappingRequest = new GetMappingsRequest();
        getMappingRequest.indices("library");
        GetMappingsResponse mappingsResponse = highLevelClient.indices().getMapping(getMappingRequest, RequestOptions.DEFAULT);
        String s3 = JSONObject.toJSONString(mappingsResponse.mappings());*/

//        update mapping


//        query by id
        GetRequest request = new GetRequest("library", "Leviathan Wakes");
        GetResponse documentFields = highLevelClient.get(request, RequestOptions.DEFAULT);
        Map<String, DocumentField> fields = documentFields.getFields();
        GetRequest request22 = new GetRequest("library", "Leviathan Wake22222222s");
        GetResponse documentFields2 = highLevelClient.get(request22, RequestOptions.DEFAULT);
        System.out.println(fields);

//        get cluster settings
        getClusterSetting();


        enableAliyunSQLPlugin();

        getClusterSetting();
    }

    private boolean enableAliyunSQLPlugin() throws IOException {
//        change cluster settings
        ClusterUpdateSettingsRequest settingsRequest = new ClusterUpdateSettingsRequest();
        Map<String, Object> map = new HashMap<>();
        map.put("aliyun.sql.enabled", true);
        settingsRequest.persistentSettings(map);
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
                        String s = EntityUtils.toString(response.getEntity());
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
        Settings persistentSettings = clusterUpdateSettingsResponse.getPersistentSettings();
        boolean acknowledged = clusterUpdateSettingsResponse.isAcknowledged();
        return true;
    }

    private void getClusterSetting() throws IOException {
        ClusterGetSettingsRequest request = new ClusterGetSettingsRequest();
//        request.includeDefaults(true);
        ClusterGetSettingsResponse settings = highLevelClient.cluster().getSettings(request, RequestOptions.DEFAULT);
        String s = settings.toString();
        System.out.println();
        System.out.println(s);
    }

    private void trySQL() throws IOException {
        pairRequest("SELECT * FROM library WHERE release_date < '2000-01-01'");
        pairRequest("SELECT * FROM library WHERE _id = 'Leviathan Wakes'");
        pairRequest("SELECT * FROM library WHERE name like '%Wakes%'");
        pairRequest("SELECT * FROM library WHERE myId in ('24','25')");
        pairRequest("SELECT * FROM library WHERE myId between '23' and '26'");
        pairRequest("SELECT * FROM library WHERE myId is not null");
        pairRequest("SELECT * FROM library order by name desc");
        pairRequest("SELECT name FROM library");
        pairRequest("SELECT name FROM library as library");
        pairRequest("SELECT name,age FROM library");
        pairRequest("SELECT distinct name FROM library");
//        pairRequest("SELECT * FROM library order by author");
        pairRequest("SELECT * FROM library order by page_count");
        pairRequest("SELECT * FROM library limit 1");
        pairRequest("SELECT * FROM library offset 1");
        pairRequest("SELECT avg(page_count) FROM library offset 1");
        pairRequest("SELECT count(1) FROM library");
        pairRequest("SELECT count(*) FROM library");
        pairRequest("SELECT count(name) FROM library");
        pairRequest("SELECT first(name) FROM library where name is not null");
        pairRequest("SELECT last(name) FROM library where name is not null");
        pairRequest("SELECT max(page_count) FROM library where page_count is not null");
        pairRequest("SELECT min(page_count) FROM library where page_count is not null");
        pairRequest("SELECT sum(page_count) FROM library where page_count is not null");
        pairRequest("SELECT name FROM library group by name");
        pairRequest("SELECT name,sum(page_count) FROM library where page_count is not null and name is not null group by name having sum(page_count) > 0");
    }

    private void pairRequest(String sql) throws IOException {
        System.out.println("\n");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~   " + sql + "   ~~~~~~~~~~~~~~~~~~~~~~~~~");
        sqlRequest(sql, ALISQL);

//        try {
//            sqlRequest(sql, SQL);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private void sqlRequest(String sql, String type) throws IOException {
        Request xPackReq = new Request(HttpPost.METHOD_NAME, type);
        String xPackPostBodyParam = "{\n" +
                "  \"query\":\"" + sql + "\"\n" +
                "}";
        xPackReq.setJsonEntity(xPackPostBodyParam);
        try {
            Response xPackResponse = highLevelClient.getLowLevelClient().performRequest(xPackReq);
            String s4 = EntityUtils.toString(xPackResponse.getEntity());
            resMap.put(sql, s4);
            resMap.put(sql, true);
            System.out.println(s4);
        } catch (IOException e) {
            resMap.put(sql, e.getMessage());
            resMap.put(sql, false);
            e.printStackTrace();
        } catch (ParseException e) {
            resMap.put(sql, e.getMessage());
            resMap.put(sql, false);
            e.printStackTrace();
        }
    }


    private void testXpackJdbc() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        String driver = "org.elasticsearch.xpack.sql.jdbc.EsDriver";
        Class.forName(driver).newInstance();
        String address = "jdbc:es://http://es-cn-zz11tte5q001seayb.public.elasticsearch.aliyuncs.com";
        Properties connectionProperties = new Properties();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(address, connectionProperties);
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM flower");
            while (results.next()) {
                System.out.println(results.getString(1));
                System.out.println(results.getString(2));
                System.out.println(results.getString(3));
                System.out.println(results.getString(4));
                System.out.println(results.getString(5));
                System.out.println(results.getString(6));

            }
            results.close();
            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

//关闭资源


        connection.close();
    }
}
