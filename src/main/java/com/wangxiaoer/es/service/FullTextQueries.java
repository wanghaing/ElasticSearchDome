package com.wangxiaoer.es.service;

import org.elasticsearch.action.search.SearchResponse;

/**
 * @author Wangxiaoer
 * @// TODO: 2018/11/9
 */
//全文查询
public interface FullTextQueries {

    //匹配查询，单字段且只能搜索一个字母、汉字
    SearchResponse matchQuery(String index, String type, String name, String text);

    //n代表两个词之间可以隔着n个词
    SearchResponse matchPhraseQuery(String index, String type, String name, String text, int n);

    //多字段查询，检索相同的内容
    SearchResponse multiMatchQuery(String text, String... name);

    //更专业的查询,常用词查询
    //忽略高频词比如：is，类似于stopwords ，提高查询准确率
    SearchResponse commonTermsQuery(String name, String text);

    //支持紧凑的Lucene查询字符串语法，允许您在单个查询字符串中指定AND | OR | NOT条件和多字段搜索。仅限专家用户。
    //字符串语法查询,支持通配符和正则表达式

    SearchResponse queryStringQuery(String field, String text);

    //一种更简单，更健壮的query_string语法版本，适合直接向用户公开。
    SearchResponse simpleQueryStringQuery(String field, String text);
}
