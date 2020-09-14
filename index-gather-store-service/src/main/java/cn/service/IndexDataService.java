package cn.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.pojo.IndexData;
import cn.util.SpringContextUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 胡建德
 */
@Service
@CacheConfig(cacheNames = "indexData")
public class IndexDataService {

    private Map<String,List<IndexData>> data = new HashMap<>();

    @Autowired
    private RestTemplate restTemplate;

    public IndexDataService() {
    }

    @CacheEvict(key = "'indexData-code-'+ #p0")
    public void remove(String code){

    }

    @Cacheable(key = "'indexData-code-'+ #p0")
    public List<IndexData> get(String code){
        return CollUtil.toList();
    }

    @CachePut(key = "'indexData-code-'+ #p0")
    public List<IndexData> store(String code){
        return data.get(code);
    }


    @HystrixCommand(fallbackMethod = "third_part_not_connected")
    public List<IndexData> fresh(String code){
        IndexDataService indexDataService = SpringContextUtil.getBean(IndexDataService.class);
        try{
            List<IndexData> indexData = fetch_indexes_from_third_part(code);
            data.put(code,indexData);
            indexDataService.remove(code);
        }catch (Exception e){
            e.printStackTrace();
        }

        return indexDataService.store(code);
    }


    public List<IndexData> fetch_indexes_from_third_part(String code){
        List<Map> mapList = restTemplate.getForObject("http://127.0.0.1:8090/indexes/"+code+".json", List.class);
        return mapListToIndex(mapList);
    }

    public List<IndexData> mapListToIndex(List<Map> mapList){
        List<IndexData> indexdata = new ArrayList<>();
        for(Map map : mapList){
            IndexData indexData = new IndexData();
            String date = map.get("date").toString();
            float closePoint = Convert.toFloat(map.get("closePoint"));
            indexData.setDate(date);
            indexData.setClosePoint(closePoint);
            indexdata.add(indexData);
        }
        return indexdata;
    }

    public List<IndexData> third_part_not_connected(String code){
        System.out.println("third_part_not_connected()");
        IndexData indexData = new IndexData();
        indexData.setDate("n/a");
        indexData.setClosePoint(0);
        return CollectionUtil.toList(indexData);
    }
}
