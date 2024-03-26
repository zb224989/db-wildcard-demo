package cn.tryhi.service;

import cn.tryhi.model.entity.WildcardEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface WildcardService extends IService<WildcardEntity> {

    List<WildcardEntity> getByFileName(String fileName);

    List<WildcardEntity> getByFileNameNoLike(String fileName);


}
