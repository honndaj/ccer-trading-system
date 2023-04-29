package com.example.common;

public interface SystemString {

    String SUCCESS_STATUS = "200"; // 成功
    String PARAM_ERROR = "400";  // 参数错误
    String AUTH_NOT_SATISFY = "401";  // 权限不足
    String SYSTEM_ERROR = "500"; // 服务器错误
    String SERVICE_ERROR = "600"; // 业务异常

    String[] STRING_AREA = { "北京", "天津", "上海", "深圳", "广州", "湖北", "重庆" };
    String[] STRING_KIND = { "树林", "风能", "太阳能", "水电", "生物质发电" };
}
