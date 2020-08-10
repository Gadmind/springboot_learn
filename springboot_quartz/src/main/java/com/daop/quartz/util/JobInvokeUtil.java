package com.daop.quartz.util;

import com.daop.quartz.pojo.JobDetail;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

/**
 * @BelongsProject: springboot_learn
 * @BelongsPackage: com.daop.scheduled.util
 * @Description: 定时任务方法执行工具
 * @DATE: 2020-08-10
 * @AUTHOR: Administrator
 **/
public class JobInvokeUtil {
    /**
     * 是新定时任务中的方法
     *
     * @param jobDetail 定时任务信息类
     */
    public static void invokeMethod(JobDetail jobDetail) throws Exception {
        String beanName = jobDetail.getJobClass();
        String methodName = getMethodName(jobDetail.getJobMethod());
        List<Object[]> methodParams = getMethodParams(jobDetail.getJobMethod());
        if (!validClassName(jobDetail.getJobClass())) {
            Object bean = SpringUtils.getBean(beanName);
            invokeMethod(bean, methodName, methodParams);
        } else {
            Object bean = Class.forName(beanName).newInstance();
            invokeMethod(bean, methodName, methodParams);
        }
    }

    /**
     * 调用任务方法
     *
     * @param bean         目标对象
     * @param methodName   方法名称
     * @param methodParams 方法参数
     */
    private static void invokeMethod(Object bean, String methodName, List<Object[]> methodParams) {
        try {
            if (methodParams != null && methodParams.size() > 0) {
                Method method = bean.getClass().getDeclaredMethod(methodName, getMethodParamsType(methodParams));
                method.invoke(bean, getMethodParamsValue(methodParams));
            } else {
                Method method = bean.getClass().getDeclaredMethod(methodName);
                method.invoke(bean);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取参数类型
     *
     * @param methodParams 相关参数类型
     * @return 参数类型集合
     */
    private static Class<?>[] getMethodParamsType(List<Object[]> methodParams) {
        Class<?>[] classes = new Class<?>[methodParams.size()];
        int index = 0;
        for (Object[] os : methodParams) {
            classes[index] = (Class<?>) os[1];
            index++;
        }
        return classes;
    }

    /**
     * 获取方法参数值
     *
     * @param methodParams 方法参数
     * @return 参数值集合
     */
    private static Object[] getMethodParamsValue(List<Object[]> methodParams) {
        Object[] classes = new Object[methodParams.size()];
        int index = 0;
        for (Object[] os : methodParams) {
            classes[index] = (Object) os[0];
            index++;
        }
        return classes;
    }

    /**
     * 获取方法中的参数
     *
     * @param jobMethod 目标字符串参数
     * @return 目标字符串中的参数集合
     */
    private static List<Object[]> getMethodParams(String jobMethod) {
        String methodStr = StringUtils.substringBetween(jobMethod, "(", ")");
        if (StringUtils.isEmpty(methodStr)) {
            return null;
        }
        String[] methodParam = methodStr.split(",");
        List<Object[]> paramsList = new LinkedList<>();
        for (String s : methodParam) {
            String str = StringUtils.trimToEmpty(s);
            //String字符串类型，包含'
            if (StringUtils.contains(str, "'")) {
                paramsList.add(new Object[]{StringUtils.replace(str, "'", ""), String.class});
            }
            //Boolean类型，等于true或false
            else if (StringUtils.equalsIgnoreCase(str, "true") || StringUtils.equalsIgnoreCase(str, "false")) {
                paramsList.add(new Object[]{Boolean.valueOf(str), Boolean.class});
            }
            //long长整形，包含L
            else if (StringUtils.containsIgnoreCase(str, "L")) {
                paramsList.add(new Object[]{Long.valueOf(StringUtils.replaceIgnoreCase(str, "L", "")), Long.class});
            }
            //double类型，包含D
            else if (StringUtils.containsIgnoreCase(str, "D")) {
                paramsList.add(new Object[]{Double.valueOf(StringUtils.replaceIgnoreCase(str, "D", "")), Double.class});
            }
            //其他类型归类为整形
            else {
                paramsList.add(new Object[]{Integer.valueOf(str), Integer.class});
            }
        }
        return paramsList;
    }

    /**
     * 获取Bean方法
     *
     * @param jobMethod 目标字符串方法名
     * @return method方法
     */
    private static String getMethodName(String jobMethod) {
        return StringUtils.substringBefore(jobMethod, "(");
    }

    /**
     * 校验是否为包类名
     *
     * @param invokeClassName 目标字符串类名
     * @return 是否为类名
     */
    private static boolean validClassName(String invokeClassName) {
        return StringUtils.countMatches(invokeClassName, ".") > 1;
    }
}
