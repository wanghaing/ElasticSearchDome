package com.wangxiaoer.es.service.impl;

import com.wangxiaoer.es.service.FullTextQueries;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author Wangxiaoer
 * @// TODO: 2018/11/9
 */
@Service
public class FullTextQueriesImpl implements FullTextQueries {

    private final Logger logger=LoggerFactory.getLogger(FullTextQueriesImpl.class);
    @Autowired
    TransportClient client;

    @Override
    public SearchResponse matchQuery(String index, String type, String name, String text) {
        QueryBuilder queryBuilder=QueryBuilders.matchQuery(name,text);
        SearchResponse response=client.prepareSearch(index)
                .setTypes(type)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(queryBuilder)
                .get();
        logger.info("matchQuery Response Staus : ",response.status().toString());
        System.out.println(response.toString());
        return response;
    }

    @Override
    public SearchResponse matchPhraseQuery(String index, String type, String name, String text, int n) {
        //slop(n)表示两个词之间可以隔着n个词
        QueryBuilder queryBuilder=QueryBuilders.matchPhraseQuery(name,text).slop(n);
        SearchResponse response=client.prepareSearch(index)
                .setTypes(type)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(queryBuilder)
                .get();
        System.out.println(response.toString());
        return response;
    }


    @Override
    public SearchResponse multiMatchQuery(String text, String...name) {
        //  .operator
        QueryBuilder queryBuilder=QueryBuilders.multiMatchQuery(text,name);
        SearchResponse response=client.prepareSearch()
                .setQuery(queryBuilder)
                .get();
        System.out.println(response.toString());
        return response;
    }

    @Override
    public SearchResponse commonTermsQuery(String name, String text) {
        //  cutoffFrequency设置高频词score因子
        QueryBuilder queryBuilder=QueryBuilders.commonTermsQuery(name,text).cutoffFrequency(0.001f);
        SearchResponse response=client.prepareSearch()
                .setQuery(queryBuilder)
                .get();
        System.out.println(response.toString());
        return response;
    }

    @Override
    public SearchResponse queryStringQuery(String field, String text) {
        //field 为检索字段
        QueryBuilder queryBuilder=QueryBuilders.queryStringQuery(text).field(field);
        SearchResponse response=client.prepareSearch()
                .setQuery(queryBuilder)
                .get();
        System.out.println(response.toString());
        return response;
    }

    @Override
    public SearchResponse simpleQueryStringQuery(String name, String text) {
        QueryBuilder queryBuilder=QueryBuilders.simpleQueryStringQuery(text).field(name);
        SearchResponse response=client.prepareSearch()
                .setQuery(queryBuilder)
                .get();
        System.out.println(response.toString());
        return response;
    }

    //查询多个字段，查询不同内容0
    public SearchResponse contentTitleQuery(String name1, String name2, String text1, String text2){
        QueryBuilder qb1=QueryBuilders.matchPhraseQuery(name1,text1);
        QueryBuilder qb2=QueryBuilders.matchPhraseQuery(name2,text2);
        QueryBuilder qb=QueryBuilders.boolQuery().should(qb1).should(qb2);
        SearchResponse response=client.prepareSearch()
                .setQuery(qb).get();
        return response;
    }
}
