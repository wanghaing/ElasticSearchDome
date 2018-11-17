package com.wangxiaoer.es.config;


import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 数据配置
 * @author Wangxiaoer
 * @// TODO: 2018/11/4
 *
 *
 */


@Configuration
public class ESConfiguration {
    @Value("elasticsearch") //
    private String clusterName;

    @Value("localhost")//202.196.37.169
    private String hostName;

    @Value("9300")
    private String port;

    @Value("5")
    private String poolSize;

    @Value("5")
    private  String pingTimeout;

    @Value("5")
    private  String nodesSamplerInterval;

    @Bean
    public TransportClient client() throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name", clusterName)
//                .put("thread_pool.search.size", Integer.parseInt(poolSize))
//                .put("client.transport.sniff", true)
//                .put("client.transport.ignore_cluster_name",true)    //设置为true忽略已连接节点的群集名称验证。
//                .put("client.transport.ping_timeout",Integer.parseInt(pingTimeout))               //等待节点ping响应的时间。默认为5s。
//                .put("client.transport.nodes_sampler_interval",Integer.parseInt(nodesSamplerInterval))   //对列出和连接的节点进行采样/ ping的频率。默认为5s。
                .build();

        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName(hostName), Integer.parseInt(port)));

        return  client;

    }
}