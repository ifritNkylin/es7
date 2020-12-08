package com.ifrit.es7.service;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ElasticsearchDataAccessor {
    @Autowired
    RestHighLevelClient elasticClient;
}
