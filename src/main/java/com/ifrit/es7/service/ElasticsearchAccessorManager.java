package com.ifrit.es7.service;


import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticsearchAccessorManager {
    public static final String URL = "es-cn-zz11tte5q001seayb.public.elasticsearch.aliyuncs.com";
//    public static final String URL = "localhost";

    @Bean
    public RestHighLevelClient elasticClient() {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

        String userName = "elastic";
        String password = "QAZwsx123";
        Credentials creds = new UsernamePasswordCredentials(userName, password);

        credentialsProvider.setCredentials(AuthScope.ANY, creds);

        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost(URL, 9200))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
                        return httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                });

        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(restClientBuilder);
        System.out.println("creating elastic client ~~~~~~~~~~~~~~~");
        return restHighLevelClient;
    }
}
