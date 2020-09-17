package cn.controller;

import cn.config.IpConfiguration;
import cn.pojo.Index;
import cn.service.IndexCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 胡建德
 */
@RestController
public class IndexController {
    @Autowired
    private IndexCodeService indexCodeService;

    @Autowired
    private IpConfiguration ipConfiguration;

    @RequestMapping("/codes")
    @CrossOrigin
    public List<Index> get(){
        System.out.println("current instance's port is "+ ipConfiguration.getPort());
        return indexCodeService.get();
    }
}
