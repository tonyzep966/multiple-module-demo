package com.tony966.javaspringdbfdemo.constant;

import java.util.HashMap;
import java.util.HashSet;

public class CityConstant {

    // 计划表省份简称与全称映射关系
    public static HashMap<String, String> originPlanMap = new HashMap<>();

    // 策略模式注解集合
    public static HashSet<String> originStrategySet = new HashSet<>();

    static {
        originPlanMap.put("安徽", "安徽省");
        originPlanMap.put("安徽省", "安徽省");
        originPlanMap.put("北京", "北京市");
        originPlanMap.put("北京市", "北京市");
        originPlanMap.put("福建", "福建省");
        originPlanMap.put("福建省", "福建省");
        originPlanMap.put("甘肃", "甘肃省");
        originPlanMap.put("甘肃省", "甘肃省");
        originPlanMap.put("广东", "广东省");
        originPlanMap.put("广东省", "广东省");
        originPlanMap.put("广西", "广西壮族自治区");
        originPlanMap.put("广西壮族自治区", "广西壮族自治区");
        originPlanMap.put("贵州", "贵州省");
        originPlanMap.put("贵州省", "贵州省");
        originPlanMap.put("海南", "海南省");
        originPlanMap.put("海南省", "海南省");
        originPlanMap.put("河北", "河北省");
        originPlanMap.put("河北省", "河北省");
        originPlanMap.put("河南", "河南省");
        originPlanMap.put("河南省", "河南省");
        originPlanMap.put("黑龙江", "黑龙江省");
        originPlanMap.put("黑龙江省", "黑龙江省");
        originPlanMap.put("湖北", "湖北省");
        originPlanMap.put("湖北省", "湖北省");
        originPlanMap.put("湖南", "湖南省");
        originPlanMap.put("湖南省", "湖南省");
        originPlanMap.put("吉林", "吉林省");
        originPlanMap.put("吉林省", "吉林省");
        originPlanMap.put("江苏", "江苏省");
        originPlanMap.put("江苏省", "江苏省");
        originPlanMap.put("江西", "江西省");
        originPlanMap.put("江西省", "江西省");
        originPlanMap.put("辽宁", "辽宁省");
        originPlanMap.put("辽宁省", "辽宁省");
        originPlanMap.put("内蒙古", "内蒙古自治区");
        originPlanMap.put("内蒙古自治区", "内蒙古自治区");
        originPlanMap.put("宁夏", "宁夏回族自治区");
        originPlanMap.put("宁夏回族自治区", "宁夏回族自治区");
        originPlanMap.put("青海", "青海省");
        originPlanMap.put("青海省", "青海省");
        originPlanMap.put("山东", "山东省");
        originPlanMap.put("山东省", "山东省");
        originPlanMap.put("山西", "山西省");
        originPlanMap.put("山西省", "山西省");
        originPlanMap.put("陕西", "陕西省");
        originPlanMap.put("陕西省", "陕西省");
        originPlanMap.put("四川", "四川省");
        originPlanMap.put("四川省", "四川省");
        originPlanMap.put("天津", "天津市");
        originPlanMap.put("天津市", "天津市");
        originPlanMap.put("新疆", "新疆维吾尔自治区");
        originPlanMap.put("新疆维吾尔自治区", "新疆维吾尔自治区");
        originPlanMap.put("云南", "云南省");
        originPlanMap.put("云南省", "云南省");
        originPlanMap.put("浙江", "浙江省");
        originPlanMap.put("浙江省", "浙江省");
        originPlanMap.put("重庆", "重庆市");
        originPlanMap.put("重庆市", "重庆市");
        originPlanMap.put("西藏", "西藏自治区");
        originPlanMap.put("西藏自治区", "西藏自治区");
    }

    static {
        originStrategySet.add(StrategyConstants.ANHUI.trim());
        originStrategySet.add(StrategyConstants.BEIJING.trim());
        originStrategySet.add(StrategyConstants.CHONGQING.trim());
        originStrategySet.add(StrategyConstants.FUJIAN.trim());
        originStrategySet.add(StrategyConstants.GANSU.trim());
        originStrategySet.add(StrategyConstants.GUANGDONG.trim());
        originStrategySet.add(StrategyConstants.GUANGXI.trim());
        originStrategySet.add(StrategyConstants.GUIZHOU.trim());
        originStrategySet.add(StrategyConstants.HAINAN.trim());
        originStrategySet.add(StrategyConstants.HEBEI.trim());
        originStrategySet.add(StrategyConstants.HEILONGJIANG.trim());
        originStrategySet.add(StrategyConstants.HENAN.trim());
        originStrategySet.add(StrategyConstants.HUBEI.trim());
        originStrategySet.add(StrategyConstants.HUNAN.trim());
        originStrategySet.add(StrategyConstants.JIANGSU.trim());
        originStrategySet.add(StrategyConstants.JIANGXI.trim());
        originStrategySet.add(StrategyConstants.JILIN.trim());
        originStrategySet.add(StrategyConstants.LIAONING.trim());
        originStrategySet.add(StrategyConstants.NEIMENGGU.trim());
        originStrategySet.add(StrategyConstants.NINGXIA.trim());
        originStrategySet.add(StrategyConstants.QINGHAI.trim());
        originStrategySet.add(StrategyConstants.SHANDONG.trim());
        originStrategySet.add(StrategyConstants.SHANXI14.trim());
        originStrategySet.add(StrategyConstants.SHANXI61.trim());
        originStrategySet.add(StrategyConstants.SICHUAN.trim());
        originStrategySet.add(StrategyConstants.TIANJIN.trim());
        originStrategySet.add(StrategyConstants.XINJIANG.trim());
        originStrategySet.add(StrategyConstants.XIZANG.trim());
        originStrategySet.add(StrategyConstants.YUNNAN.trim());
        originStrategySet.add(StrategyConstants.ZHEJIANG.trim());
    }
}
