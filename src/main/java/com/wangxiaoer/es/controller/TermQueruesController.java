package com.wangxiaoer.es.controller;


import com.wangxiaoer.es.service.impl.TermQueriesImpl;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TermQueruesController {

    @Autowired
    TransportClient client;
    @Autowired
    TermQueriesImpl termQueriesImpl;

    @PostMapping("/term")
    public ResponseEntity<?> term(@RequestParam(name = "name")String name,@RequestParam(name="text")Object text){
        if (name==null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        SearchResponse searchResponse =termQueriesImpl.termQuery(name,text);
        SearchHits hits=searchResponse.getHits();
        String source=null;
        for(SearchHit hit:hits){
            source+=hit.getSourceAsString();
        }

        return  new ResponseEntity<>(source,HttpStatus.OK);
    }

    @PostMapping("/terms")
    public ResponseEntity<?> terms(@RequestParam(name = "name")String name,@RequestParam(name="object")Object...texts){
        if (name==null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        SearchResponse searchResponse =termQueriesImpl.termsQuery(name,texts);
        SearchHits hits=searchResponse.getHits();
        String source=null;
        for(SearchHit hit:hits){
            source+=hit.getSourceAsString();
        }

        return  new ResponseEntity<>(source,HttpStatus.OK);
    }

    @PostMapping("/range")
    public ResponseEntity<?> range(@RequestParam(name = "name")String name,@RequestParam(name="lower")Object lower,@RequestParam(name="upper")Object upper){
        if (name==null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        SearchResponse searchResponse =termQueriesImpl.rangeQuery(name,lower,upper);
        SearchHits hits=searchResponse.getHits();
        String source=null;
        for(SearchHit hit:hits){
            source+=hit.getSourceAsString();
        }

        return  new ResponseEntity<>(source,HttpStatus.OK);
    }
}
