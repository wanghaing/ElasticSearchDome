package com.wangxiaoer.es.service;

import org.elasticsearch.action.search.SearchResponse;

/**
 * @author Wangxiaoer
 */
//复合查询
public interface CompoundQueries {

    public SearchResponse boolQuery(String name, String value);
}

