package com.wangxiaoer.es.dao;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 基本的Index索引操作
 * ES操作数据类
 * 抽取成Dao层，数据访问类
 * @author Wangxiaoer
 */
@Slf4j
@Component
public class DocumentDao {
	
	@Autowired
	private TransportClient client;
	
	/**
	 * 创建索引
	 * @param index
	 * @return
	 */
	public boolean buildIndex(String index){
		if (!isIndexExit(index)) {
			log.info("Index is not exits!");
		}
		CreateIndexResponse createIndexResponse=client.admin().indices().prepareCreate(index).execute().actionGet();
		return createIndexResponse.isAcknowledged();
	}
	
	/**
	 * 删除索引
	 * @param index
	 * @return
	 */
	public boolean deleteIndex(String index) {
		if (isIndexExit(index)) {
			DeleteIndexResponse dResponse=client.admin().indices().prepareDelete(index).execute().actionGet();
			if (dResponse.isAcknowledged()) {
	        	log.info("删除索引**成功** index->>>>>>>" + index);
	        } else {
	        	log.info("删除索引**失败** index->>>>> " + index);
	        }
	        return dResponse.isAcknowledged();
        }
		return false;
		
	}
	
	/**
	 * 查询索引
	 * @param index 索引
	 * @param type 类型
	 * @param id 数据ID
	 * @return
	 */
	public Map<String, Object> searchDataByParam(String index,String type,String id) {
		if(index == null || type == null || id == null) {
    		log.info(" 无法查询数据，缺唯一值!!!!!!! ");
    		return null;
    	}
		// 获取查询信息
		GetResponse getResponse=client.prepareGet(index,type,id).get();
		return getResponse.getSource();
	}
	
	/**
	 * 更新索引
	 * @param data
	 * @param index
	 * @param type
	 * @param id
	 */
	public void updateDataById(JSONObject data, String index, String type, String id) {
		if(index == null || type == null || id == null) {
    		log.info(" 无法更新数据，缺唯一值!!!!!!! ");
    		return;
    	}
		UpdateRequest up = new UpdateRequest().index(index).type(type).id(id).doc(data);
		UpdateResponse response=client.update(up).actionGet();
		log.info("更新数据状态信息，status{}", response.status().getStatus());
	}
	
	/**
	 * 添加数据
	 * @param data
	 * @param index
	 * @param type
	 * @param id
	 * @return
	 */
	public String addTargetDataAll(JSONObject data, String index, String type, String id){
		//判断一下次id是否为空，为空的话就设置一个id
    	if(id == null) {
    		id = UUID.randomUUID().toString();
    	}
    	IndexResponse response=client.prepareIndex(index, type, id).get();
    	log.info("addTargetDataALL 添加数据的状态:{}", response.status().getStatus());
		return response.getId();
	}
	
	 /**
     * 通过ID删除数据
     * @param index 索引，类似数据库
     * @param type  类型，类似表
     * @param id    数据ID
     */
    public void delDataById(String index, String type, String id) {
    	if(index == null || type == null || id == null) {
    		log.info(" 无法删除数据，缺唯一值!!!!!!! ");
    		return;
    	}
    	//开始删除数据
        DeleteResponse response = client.prepareDelete(index, type, id).execute().actionGet();
        log.info("删除数据状态，status-->>>>{},", response.status().getStatus());
    }
    
    /**
     * 通过查询API删除
     * @param index
     * @param
     * @return
     */
    public void delByQuery(String index,String fieldName,String value) {
    	if (index==null) {
    		log.info(" 无法删除数据，缺唯一值!!!!!!! ");
			return;
		}
    	BulkByScrollResponse response=DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
    			.filter(QueryBuilders.matchQuery(fieldName, value))
    			.source(index)
    			.get();
    	log.info("删除数据的数量{}",response.getDeleted());
	}
    
    /**
     * 多文档查询
     * @param index
     * @param type 
     * @param ids id集合
     * @return
     */
    public List<String> multiGetdoc(String index,String type,String...ids) {
    	List<String> jsonList=new ArrayList<>();
    	MultiGetResponse multiGetResponse=client.prepareMultiGet().add(index, type, ids).get();
    	multiGetResponse.forEach(item->{
    		GetResponse getResponse=item.getResponse();
    		if (getResponse.isExists()) {
    			jsonList.add(getResponse.getSourceAsString());
			}
    	});
		return jsonList;
	}
	
	/**
	 * 判断索引是否存在
	 * @param index
	 * @return
	 */
	public boolean isIndexExit(String index) {
		IndicesExistsResponse iep=client.admin().indices().exists(new IndicesExistsRequest(index)).actionGet();
		if (iep.isExists()) {
			log.info("此索引[{}]已经存在ES集群中",index);
		} else {
			log.info("没有索引[{}]",index);
		}
		return iep.isExists();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
