//package com.rachel.hulimall.gulimallsearch.config;
//
//import org.apache.http.HttpHost;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestClientBuilder;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class GulimallElasticsearchConfig {
//
//    @Bean
//    public RestHighLevelClient esClient() {
//        RestClientBuilder builder = RestClient.builder(new HttpHost("127.0.0.1", 9200, "http"));
//        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(builder);
//        return restHighLevelClient;
//    }
//}
