package com.linkmeng.managersystem.common;

import com.linkmeng.managersystem.common.constant.I18nConstant;
import com.linkmeng.managersystem.common.exception.CommonException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

/**
 * 国际化工具类
 *
 * @since 2024-08-15
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ResourceUtil {
    private static final String LOCAL_CONNECTOR = "_";
    private static final List<String> I18N_LANGUAGE_WHITE_LIST = Arrays.asList("zh_CN", "en_US");
    private static final String I18N_FILE_NAME = "/i18n/com_linkmeng_managersystem-ui_{0}.properties";

    /**
     * 国际化
     *
     * @param i18nKey 国际化ID
     * @param arguments 填充参数
     * @return 国际化字符串
     */
    public static String of(String i18nKey, Object... arguments) {
        if (StringUtils.isEmpty(i18nKey)) {
            log.warn("Get i18n failed, input key is empty.");
            return "";
        }
        String i18nValue = getFromProperty(i18nKey, getCurrentLocale());
        if (StringUtils.isEmpty(i18nValue)) {
            log.warn("Get i18n {} failed, value is empty.", i18nKey);
            return buildFailedResult(i18nKey);
        }
        return MessageFormat.format(i18nValue, arguments);
    }

    /**
     * 获取当前系统的语言设置
     *
     * @return 语言设置
     */
    private static String getCurrentLocale() {
        Locale currentLocale = Locale.getDefault();
        String language = currentLocale.getLanguage().toLowerCase(Locale.ROOT);
        String country = currentLocale.getCountry().toUpperCase(Locale.ROOT);
        String currentLocal = String.join(LOCAL_CONNECTOR, language, country);
        return I18N_LANGUAGE_WHITE_LIST.contains(currentLocal) ? currentLocal : I18N_LANGUAGE_WHITE_LIST.get(0);
    }

    /**
     * 从资源文件中获取指定的国际化字符串
     *
     * @param key 国际化ID
     * @param locale 语言设置
     * @return 国际化字符串
     */
    private static String getFromProperty(String key, String locale) {
        String fileName = MessageFormat.format(I18N_FILE_NAME, locale);
        try (InputStream inputStream = ResourceUtil.class.getResourceAsStream(fileName)) {
            if (inputStream == null) {
                log.warn("Get i18n file {} failed", fileName);
                return buildFailedResult(key);
            }
            Properties properties = new Properties();
            properties.load(inputStream);
            return properties.getProperty(key);
        } catch (IOException exception) {
            log.error("Get i18n file {} caused exception", fileName);
            return buildFailedResult(key);
        }
    }

    /**
     * 出错时获取默认国际化结果
     *
     * @param key 国际化ID
     * @return 默认国际化字符串
     */
    private static String buildFailedResult(String key) {
        return "!!" + key + "!!";
    }
}
