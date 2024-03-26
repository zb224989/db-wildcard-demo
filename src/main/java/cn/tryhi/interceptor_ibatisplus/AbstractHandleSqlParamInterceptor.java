package cn.tryhi.interceptor_ibatisplus;

import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.statement.select.Select;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;


/**
 * sql数据拦截处理基类
 */
@Slf4j
public abstract class AbstractHandleSqlParamInterceptor extends JsqlParserSupport implements HandleSqlParamInterceptor {

    /**
     * beforeQuery
     *
     * @param executor      执行器
     * @param ms            映射信息
     * @param parameter     执行参数
     * @param rowBounds     行绑定
     * @param resultHandler 结果handler
     * @param boundSql      绑定的sal
     */
    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
        if (ignoreOnce(parameter, boundSql.getSql())) {
            if (log.isDebugEnabled()) {
                log.debug(getIgnoredString(), ms.getId());
            }
        } else {
            beforeQueryHandle(boundSql);
            PluginUtils.MPBoundSql mpBs = PluginUtils.mpBoundSql(boundSql);
            mpBs.sql(parserSingle(mpBs.sql(), boundSql));
        }
    }
    protected abstract void beforeQueryHandle(BoundSql boundSql);

    /**
     * 是否忽略这次构建
     */
    protected boolean ignoreOnce(Object parameter, String sql) {
        return true;
    }

    /**
     * debug时,忽略此次控制的输出内容
     *
     * @return 输出内容
     */
    protected abstract String getIgnoredString();

    @Override
    protected void processSelect(Select select, int index, String sql, Object obj) {
    }

}
