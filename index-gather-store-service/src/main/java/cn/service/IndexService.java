package cn.service;

import cn.pojo.Index;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 胡建德
 */
@Service
public class IndexService {

    @Autowired
    private RestTemplate restTemplate;

    private List<Index> indices;

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
}
