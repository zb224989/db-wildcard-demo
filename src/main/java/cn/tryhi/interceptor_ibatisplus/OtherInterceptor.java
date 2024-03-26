package cn.tryhi.interceptor_ibatisplus;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.BoundSql;
import org.springframework.stereotype.Component;

/**
 * 其他的自定义拦截器
 */
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Slf4j
@Component
public class OtherInterceptor extends AbstractHandleSqlParamInterceptor {

    @Override
    protected void beforeQueryHandle(BoundSql boundSql) {
        System.out.println(boundSql);
    }

    @Override
    protected String getIgnoredString() {
        return "其他处理";
    }


}
