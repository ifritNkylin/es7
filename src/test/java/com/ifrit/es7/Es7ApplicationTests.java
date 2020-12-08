package com.ifrit.es7;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import okio.BufferedSink;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.UUID;


//@SpringBootTest
class Es7ApplicationTests {

    @Test
    void contextLoads() {
        Integer i = null;
        System.out.println((i + "as"));
    }

    public static void main(String[] args) throws IOException {

        File file = new File("/Users/ifrit2/IdeaProjects/slave/down/a/a/a/a/a.txt");
        FileUtils.writeStringToFile(file, "sss");

//        String s = FileUtils.readFileToString(new File("searchResult.json"));
//        JSONObject json = JSONObject.parseObject(s);
//
//        JSONArray entries = json.getJSONArray("entries");
//        entries.forEach(entry -> {
//            JSONObject jsonObject = (JSONObject) entry;
////            System.out.println("\n");
//            System.out.println("\n");
////            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
//            String title = jsonObject.get("title").toString();
////            System.out.println(title);
////            System.out.println("\n");
//            String content = jsonObject.get("content").toString();
////            System.out.println(content);
//            Document parse = Jsoup.parse(title);
//            Element body = parse.body();
//            System.out.println(body.text());
//        });


//        downloadPage();

//        addUrlParam();


//        syncExecute();

//        asyncExecute();


    }

    public static void downloadPage() throws IOException {
//        System.out.println(System.getProperty("user.home"));
//        Properties properties = System.getProperties();
//        properties.forEach((a, b) -> {
//            System.out.println(a + " " + b);
//        });

        String url = "http://www.177pic.pw/html/2020/07/3714992.html";

        String name = StringUtils.substring(url, StringUtils.lastIndexOf(url, "/") + 1);
        System.out.println(name);
        if (!StringUtils.endsWithAny(name, ".html", ".htm")) {
            name += ".html";
        }
        System.out.println(name);

//        File destination = new File(System.getProperty("user.home") + "/AAA/" + name);
        File destination = new File(name);
//        File destination = new File(System.getProperty("user.home") + "/Girl.jpg");
//        File destination = new File(System.getProperty("user.home") + "/events_with_failures_and_retries.png");
//        File destination = new File( "searchResult.json");

//        System.setProperty("https.proxyHost", "127.0.0.1");
//        System.setProperty("https.proxyPort", "8866");
        OkHttpClient okHttpClient = new OkHttpClient();
//        RequestBody requestBody = RequestBody.create(MediaType.parse(org.springframework.http.MediaType.APPLICATION_JSON_VALUE), "");
//        RequestBody requestBody = RequestBody.create(okhttp3.MediaType.parse(MediaType.APPLICATION_PROBLEM_JSON_VALUE), "");
        RequestBody requestBody = RequestBody.create(null, "");
        Request request = new Request.Builder()
                .post(requestBody)
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36")
//                .header("content-type", "application/json")
//                .header("accept", MediaType.APPLICATION_JSON_VALUE)
//                .url("https://www.jianshu.com/search/do?q=无界面chrome&type=note&page=7&order_by=default")
//                .url("http://img.177pic.pw/uploads/2020/06a/a006-387.jpg")
                .url(url)
//                .url("https://square.github.io/okhttp/images/events_with_failures_and_retries%402x.png")
//                .url("https://www.jianshu.com/search?q=%E6%97%A0%E7%95%8C%E9%9D%A2chrome&page=1&type=note")
//                .url("https://www.jianshu.com/search/do?q=%E6%97%A0%E7%95%8C%E9%9D%A2chrome&type=note&page=1")
                .build();
        Call call = okHttpClient.newCall(request);
        try (Response response = call.execute()) {
            if (response.isSuccessful()) {
                ResponseBody body = response.body();
                byte[] bytes = body.bytes();
                FileUtils.writeByteArrayToFile(destination, bytes);
                System.out.println(FileUtils.readFileToString(destination));
            } else {
                System.out.println(response.code());
                System.out.println(response.body().string());
                System.out.println(response.headers());
            }
        }

        System.out.println(destination.getPath());
        System.out.println(destination.getAbsolutePath());
    }

    /**
     * 在okhttp中构造url
     * @throws IOException
     */
    public static void addUrlParam() throws IOException {
        HttpUrl parse = HttpUrl.parse("https://www.jianshu.com/p/01afc031ada6")
                .newBuilder()
                .addQueryParameter("s","a")
                .build();
        String s = parse.toString();
        System.out.println(s);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().get()
//                .url(parse.url())
                .url(parse.toString())
//                .header()
//                .addHeader()
                .build();

        try (Response response = okHttpClient.newCall(request).execute()) {
            System.out.println(response.body().string());
            Headers headers = response.headers();
        }
    }

    /**
     * 同步执行
     * @throws IOException
     */
    private static void syncExecute() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .get()
//                .url("http://img.177pic.pw/uploads/2020/06a/a006-387.jpg")
//                .url("http://n.sinaimg.cn/sinacn10112/566/w1018h1148/20191111/fd6e-iieqapt6530904.jpg")
                .url("https://square.github.io/okhttp/calls/")
                .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36")
                .build();
        Call call = client.newCall(request);
        try (Response response = call.execute()) {
            ResponseBody body = response.body();
            String string = body.string();
            System.out.println(string);
            FileUtils.writeStringToFile(new File("/Users/ifrit2/okhttpCalls.html"), string);
        }
    }

    /**
     * 异步执行
     */
    private static void asyncExecute() {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .get()
//                .url("http://img.177pic.pw/uploads/2020/06a/a006-387.jpg")
//                .url("http://n.sinaimg.cn/sinacn10112/566/w1018h1148/20191111/fd6e-iieqapt6530904.jpg")
                .url("https://square.github.io/okhttp/calls/")
                .addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36")
                .build();
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //拿到字节流
                InputStream is = response.body().byteStream();

                int len = 0;
//                File file  = new File("/Users/ifrit2/beautifulGirl.jpg");
                File file  = new File("/Users/ifrit2/okhttpCalls.html");
                FileOutputStream fos = new FileOutputStream(file);
                byte[] buf = new byte[1024];

                while ((len = is.read(buf)) != -1){
                    fos.write(buf, 0, len);
                }

                fos.flush();
                //关闭流
                fos.close();
                is.close();
            }
        });
    }

}
