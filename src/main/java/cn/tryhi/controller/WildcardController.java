package cn.tryhi.controller;

import cn.tryhi.model.dto.Result;
import cn.tryhi.model.entity.WildcardEntity;
import cn.tryhi.service.WildcardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/v1/test")
public class WildcardController {
    @Resource
    private WildcardService wildcardService;

    @GetMapping("/demo")
    public Result<List<WildcardEntity>> taskInfo (@RequestParam String fileName) {
        return Result.ok(wildcardService.getByFileName(fileName));
    }
    @GetMapping("/demo2")
    public Result<List<WildcardEntity>> taskInfoNoLike (@RequestParam String fileName) {
        return Result.ok(wildcardService.getByFileNameNoLike(fileName));
    }

}
