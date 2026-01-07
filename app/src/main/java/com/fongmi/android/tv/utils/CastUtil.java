package com.fongmi.android.tv.utils;

import android.text.Html;
import android.text.TextUtils;

import com.fongmi.android.tv.bean.CastMember;

import java.util.ArrayList;
import java.util.List;

/**
 * 演职人员工具类
 * 用于解析和处理视频作品中的演员、导演信息
 */
public class CastUtil {
    
    /**
     * 解析演职人员字符串为 CastMember 列表
     * 支持逗号(,)和斜杠(/)作为分隔符
     * 自动清理 HTML 标签和前后空格
     * 过滤空字符串
     * 
     * @param text 演职人员字符串（可能包含多个人名，用逗号或斜杠分隔）
     * @param type 演职人员类型（演员或导演）
     * @return 解析后的 CastMember 列表
     */
    public static List<CastMember> parseCastMembers(String text, CastMember.CastType type) {
        List<CastMember> members = new ArrayList<>();
        
        // 如果输入为空，直接返回空列表
        if (TextUtils.isEmpty(text)) {
            return members;
        }
        
        // 清理 HTML 标签
        String cleanText = sanitizeHtml(text);
        
        // 如果清理后为空，返回空列表
        if (TextUtils.isEmpty(cleanText)) {
            return members;
        }
        
        // 使用正则表达式分割字符串，支持逗号和斜杠作为分隔符
        String[] names = cleanText.split("[,/]");
        
        // 遍历所有人名
        for (String name : names) {
            // 去除前后空格
            name = name.trim();
            
            // 过滤空字符串
            if (!name.isEmpty()) {
                members.add(new CastMember(name, type));
            }
        }
        
        return members;
    }
    
    /**
     * 清理 HTML 标签
     * 将 HTML 格式的文本转换为纯文本
     * 
     * @param text 可能包含 HTML 标签的文本
     * @return 清理后的纯文本
     */
    private static String sanitizeHtml(String text) {
        if (TextUtils.isEmpty(text)) {
            return "";
        }
        
        // 使用 Android 的 Html.fromHtml() 方法清理 HTML 标签
        String cleaned = Html.fromHtml(text).toString();
        
        // 去除前后空格
        return cleaned.trim();
    }
    
    /**
     * 验证演职人员名字是否有效
     * 有效的名字不能为 null、空字符串或仅包含空格
     * 
     * @param name 演职人员名字
     * @return true 如果名字有效，否则返回 false
     */
    public static boolean isValidCastName(String name) {
        return name != null && !name.trim().isEmpty();
    }
}
