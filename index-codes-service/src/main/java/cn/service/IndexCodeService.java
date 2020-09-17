package cn.service;

import cn.hutool.core.collection.CollUtil;
import cn.pojo.Index;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 胡建德
 */
@Service
@CacheConfig(cacheNames = "indices")
public class IndexCodeService {

    public List<Index> indices;

    @Cacheable("'all_codes'")
    public List<Index> get(){
        Index index = new Index();
        index.setName("未找到数据");
        index.setCode("null");
        return CollUtil.toList(index);
    }


}
