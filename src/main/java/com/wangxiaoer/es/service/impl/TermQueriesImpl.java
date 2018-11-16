package com.wangxiaoer.es.service.impl;

import com.wangxiaoer.es.service.TermQueries;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Wangxiaoer
 * @// TODO: 2018/11/4
 */
@Service
public class TermQueriesImpl implements TermQueries {

    @Autowired
    TransportClient client;

    @Override
    public SearchResponse termQuery(String name, Object object) {
        QueryBuilder queryBuilder=QueryBuilders.termQuery(name,object);
        SearchResponse response=client.prepareSearch("index")
                .setQuery(queryBuilder)
                .get();
        System.out.println(response.toString());
        return response;
    }

    @Override
    public SearchResponse termsQuery(String name, Object...object) {
        QueryBuilder queryBuilder=QueryBuilders.termsQuery(name,object);
        SearchResponse response=client.prepareSearch()
                .setQuery(queryBuilder)
                .get();
        System.out.println(response.toString());
        return response;
    }

    @Override
    public SearchResponse rangeQuery(String name, Object ob1, Object ob2) {
        QueryBuilder queryBuilder=QueryBuilders.rangeQuery(name)
                .from(ob1)
                .to(ob2)
                .includeLower(true)  //包含最低值
                .includeUpper(false); //不包含最大值
        SearchResponse response=client.prepareSearch()
                .setQuery(queryBuilder)
                .get();
        System.out.println(response.toString());
        return response;
    }

    @Override
    public SearchResponse existsQuery() {
        return null;
    }

    @Override
    public SearchResponse prefixQuery() {
        return null;
    }

    @Override
    public SearchResponse wildCardQuery() {
        return null;
    }

    @Override
    public SearchResponse regexpQuery() {
        return null;
    }

    @Override
    public SearchResponse fuzzyQuery() {
        return null;
    }

    @Override
    public SearchResponse typeQuery() {
        return null;
    }

    @Override
    public SearchResponse idsQuery() {
        return null;
    }
}
