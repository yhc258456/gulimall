package com.rachel.hulimall.gulimallsearch;

import com.alibaba.fastjson.JSON;
import com.rachel.hulimall.gulimallsearch.config.GulimallElasticsearchConfig;
import lombok.Data;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContent;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;


@SpringBootTest
class GulimallSearchApplicationTests {

    @Autowired
    private RestHighLevelClient esClient;

    @Test
    public void indexData() throws IOException {
        IndexRequest indexRequest = new IndexRequest("user");
        indexRequest.id("1");
        User user = new User();
        user.setName("rachel");
        user.setAge(25);
        indexRequest.source(JSON.toJSON(user), XContentType.JSON);
        IndexResponse indexResponse = esClient.index(indexRequest, GulimallElasticsearchConfig.COMMON_OPTIONS);
        System.out.println(indexResponse);
    }

    @Data
    class User {
        private String name;
        private Integer age;
    }


    @Test
    public void contextLoads() {
        System.out.println(esClient);

    }

}
