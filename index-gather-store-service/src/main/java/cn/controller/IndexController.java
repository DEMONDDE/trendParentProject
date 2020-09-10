package cn.controller;

import cn.pojo.Index;
import cn.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 胡建德
 */
@RestController
public class IndexController {
    @Autowired
    private IndexService indexService;

    @GetMapping("/getcodes")
    public List<Index> get(){
        return indexService.fetch_indexes_from_third_part();
    }
}
