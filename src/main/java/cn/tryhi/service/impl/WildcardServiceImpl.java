package cn.tryhi.service.impl;

import cn.tryhi.model.entity.WildcardEntity;
import cn.tryhi.mapper.WildcardMapper;
import cn.tryhi.service.WildcardService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("sysUploadTaskService")
public class WildcardServiceImpl extends ServiceImpl<WildcardMapper, WildcardEntity> implements WildcardService {

    @Resource
    private WildcardMapper wildcardMapper;

    @Override
    public List<WildcardEntity> getByFileName(String fileName) {
        QueryWrapper<WildcardEntity> wildcardEntityQueryWrapper = new QueryWrapper<>();
        wildcardEntityQueryWrapper.lambda().like(WildcardEntity::getFileName, fileName);
        return wildcardMapper.selectList(wildcardEntityQueryWrapper);
    }

    @Override
    public List<WildcardEntity> getByFileNameNoLike(String fileName) {
        QueryWrapper<WildcardEntity> wildcardEntityQueryWrapper = new QueryWrapper<>();
        wildcardEntityQueryWrapper.lambda().eq(WildcardEntity::getFileName, fileName);
        return wildcardMapper.selectList(wildcardEntityQueryWrapper);
    }


}
