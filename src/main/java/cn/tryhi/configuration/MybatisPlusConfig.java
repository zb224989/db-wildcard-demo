package cn.tryhi.configuration;

import cn.tryhi.interceptor_ibatisplus.HandleSqlParamInterceptor;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import java.util.Map;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class MybatisPlusConfig {

    private  Map<String, HandleSqlParamInterceptor> handleSqlParamInterceptors;

    /**
     * 设置自动填充列
     */
    @Autowired(required = false)
    public void setFillLineInterceptors(Map<String, HandleSqlParamInterceptor> handleSqlParamInterceptor) {
        this.handleSqlParamInterceptors = handleSqlParamInterceptor;
    }

    /**
     * 分页插件、自定义连接器
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        // 自动填充列
        if (!CollectionUtils.isEmpty(handleSqlParamInterceptors)) {
            handleSqlParamInterceptors.values().forEach(interceptor::addInnerInterceptor);
        }
        return interceptor;
    }


//    @Bean
//    public LikeStringEscapeInterceptor likeStringEscapeInterceptor() {
//        return new LikeStringEscapeInterceptor();
//    }

}
