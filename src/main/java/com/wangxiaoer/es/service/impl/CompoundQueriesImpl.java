package com.wangxiaoer.es.service.impl;

import com.wangxiaoer.es.service.CompoundQueries;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;

/**
 * @author Wangxiaoer
 * @// TODO: 2018/11/9
 */
@Service
public class CompoundQueriesImpl implements CompoundQueries {

    @Autowired
    TransportClient client;


    @Override
    public SearchResponse boolQuery(String name, String value) {
        QueryBuilder qb=QueryBuilders.boolQuery()
                .mustNot(termQuery(name,value));
        SearchResponse response=client.prepareSearch("my_index")
                .setQuery(qb)
                .get();
        SearchHits hits=response.getHits();
        for (SearchHit hit:hits){
            hit.field("content").toString();
        }
//        AnalyzeRequest analyzeRequest=new AnalyzeRequest()
//                .text(content)
//                .analyzer("ik_max_word");
//        List<AnalyzeResponse.AnalyzeToken> tokens = client.admin().indices().analyze(analyzeRequest).actionGet().getTokens();
//        for (AnalyzeResponse.AnalyzeToken token : tokens){
//            QueryBuilder qb=QueryBuilders.boolQuery().mustNot()
//            System.out.println(token.getTerm()); // 在控制台输出
//        }

        return response;
    }
}
