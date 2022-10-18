package com.maxio.maxioapi.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.maxio.maxioapi.entity.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

@Service
@Slf4j
public class MaxioApiService {

    @Value("${elastic.hostname}")
    private String hostname;

    @Value("${elastic.port}")
    private int port;

    @Value("${elastic.index}")
    private String indexName;

    public List<Log> search(String query, String accessToken) throws IOException {
        //TODO: preferred_username searched for queryString log

        RestClient restClient = RestClient
                .builder(new HttpHost(hostname, port))
                .setDefaultHeaders(new Header[]{
                        new BasicHeader("Authorization", accessToken)
                })
                .build();

        ElasticsearchTransport transport = new RestClientTransport(
                restClient,
                new JacksonJsonpMapper()
        );
        ElasticsearchClient esClient = new ElasticsearchClient(transport);

        SearchRequest request = SearchRequest.of(b -> b
                .index(indexName)
                .withJson(new StringReader(query))
                .ignoreUnavailable(true)
        );

        SearchResponse<Log> response = esClient.search(request, Log.class);

        TotalHits total = response.hits().total();
        log.info("Total hit {}", total.value());
        restClient.close();

        return response.hits().hits().stream().map(Hit::source).toList();
    }
}
