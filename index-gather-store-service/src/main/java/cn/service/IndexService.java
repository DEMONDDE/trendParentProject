package cn.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.pojo.Index;
import cn.util.SpringContextUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 胡建德
 */
@Service
@CacheConfig(cacheNames = "indices")
public class IndexService {

    @Autowired
    private RestTemplate restTemplate;

    private List<Index> indices;

    @CacheEvict(allEntries = true)
    public void remove(){

    }

    @Cacheable("'all_codes'")
    public List<Index> get(){
        return CollUtil.toList();
    }

    @Cacheable("'all_codes'")
    public List<Index> store(){
        return indices;
    }

    @HystrixCommand(fallbackMethod = "third_part_not_connected")
    public List<Index> fresh(){
        indices = fetch_indexes_from_third_part();
        IndexService indexService = SpringContextUtil.getBean(IndexService.class);
        indexService.remove();
        return indexService.store();
    }


    public List<Index> fetch_indexes_from_third_part(){
        List<Map> mapList = restTemplate.getForObject("http://127.0.0.1:8090/indexes/codes.json", List.class);
        return mapListToIndex(mapList);
    }

    public List<Index> mapListToIndex(List<Map> mapList){
        List<Index> indexs = new ArrayList<>();
        for(Map map : mapList){
            Index index = new Index();
            String code = map.get("code").toString();
            String name = map.get("name").toString();
            index.setCode(code);
            index.setName(name);
            indexs.add(index);
        }
        return indexs;
    }

    public List<Index> third_part_not_connected(){
        System.out.println("third_part_not_connected()");
        Index index= new Index();
        index.setCode("000000");
        index.setName("无效指数代码");
        return CollectionUtil.toList(index);
    }

}
