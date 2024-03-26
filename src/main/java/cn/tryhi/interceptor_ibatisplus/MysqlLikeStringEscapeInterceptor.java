package cn.tryhi.interceptor_ibatisplus;

import cn.tryhi.util.EscapeUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.mapping.BoundSql;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * 自定义拦截器方法，处理模糊查询中包含特殊字符（_、%、\）
 */
@Slf4j
@Component
public class MysqlLikeStringEscapeInterceptor extends AbstractHandleSqlParamInterceptor {

    @Override
    protected boolean ignoreOnce(Object parameter, String sql) {
        return !(parameter instanceof HashMap) || !sql.toLowerCase().contains(" like ") || !sql.toLowerCase().contains("?");
    }

    @Override
    protected void beforeQueryHandle(BoundSql boundSql) {
        String sql = boundSql.getSql();
        // 获取关键字的个数（去重）
        String[] strList = sql.split("\\?");
        Set<String> keyNames = new HashSet<>();
        for (int i = 0; i < strList.length; i++) {
            if (strList[i].toLowerCase().contains(" like ")) {
                String keyName = boundSql.getParameterMappings().get(i).getProperty();
                keyNames.add(keyName);
            }
        }

        // 对关键字进行特殊字符“清洗”，如果有特殊字符的，在特殊字符前添加转义字符（\）
        for (String keyName : keyNames) {
            HashMap<String, Object> parameter = (HashMap<String, Object>) boundSql.getParameterObject();
            if (keyName.contains("ew.paramNameValuePairs.") && sql.toLowerCase().contains(" like ?")) {
                // 第一种情况：在业务层进行条件构造产生的模糊查询关键字
                QueryWrapper wrapper = (QueryWrapper) parameter.get("ew");
                parameter = (HashMap) wrapper.getParamNameValuePairs();

                String[] keyList = keyName.split("\\.");
                // ew.paramNameValuePairs.MPGENVAL1，截取字符串之后，获取第三个，即为参数名
                Object a = parameter.get(keyList[2]);
                if (a instanceof String && (a.toString().contains("_") || a.toString().contains("\\") || a.toString()
                        .contains("%"))) {
                    parameter.put(keyList[2],
                            "%" + EscapeUtil.escapeChar(a.toString().substring(1, a.toString().length() - 1)) + "%");
                }
            } else if (!keyName.contains("ew.paramNameValuePairs.") && sql.toLowerCase().contains(" like ?")) {
                // 第二种情况：未使用条件构造器，但是在service层进行了查询关键字与模糊查询符`%`手动拼接
                Object a = parameter.get(keyName);
                if (a instanceof String && (a.toString().contains("_") || a.toString().contains("\\") || a.toString()
                        .contains("%"))) {
                    parameter.put(keyName,
                            "%" + EscapeUtil.escapeChar(a.toString().substring(1, a.toString().length() - 1)) + "%");
                }
            } else {
                // 第三种情况：在Mapper类的注解SQL中进行了模糊查询的拼接
                Object a = parameter.get(keyName);
                if (a instanceof String && (a.toString().contains("_") || a.toString().contains("\\") || a.toString()
                        .contains("%"))) {
                    parameter.put(keyName, EscapeUtil.escapeChar(a.toString()));
                }
            }
        }
    }
    @Override
    protected String getIgnoredString() {
        return "处理模糊查询中包含的特殊字符";
    }
}
