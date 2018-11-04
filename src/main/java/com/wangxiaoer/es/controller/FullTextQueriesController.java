package com.wangxiaoer.es.controller;

import org.elasticsearch.client.transport.TransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class FullTextQueriesController {

    @Autowired
    TransportClient client;


}
