package cn.tryhi.util;

import cn.hutool.core.util.StrUtil;

public class EscapeUtil {
    //mysql的模糊查询时特殊字符转义
    public static String escapeChar(String before){
        if(StrUtil.isNotBlank(before)){
            before = before.replaceAll("\\\\", "\\\\\\\\");
            before = before.replaceAll("_", "\\\\_");
            before = before.replaceAll("%", "\\\\%");
        }
        return before ;
    }
}