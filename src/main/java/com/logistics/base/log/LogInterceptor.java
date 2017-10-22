package com.logistics.base.log;

import com.alibaba.fastjson.JSON;
import com.logistics.base.constant.BaseConstant;
import com.logistics.base.utils.CommonUtil;
import com.logistics.base.utils.ReqUtils;
import com.logistics.service.sys.log.ISysOperatorLogService;
import com.logistics.service.vo.sys.SysOperatorLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.security.acl.LastOwnerException;
import java.util.Date;

/**
 * Created by Administrator on 2017/9/25/025.
 */
@Aspect
@Component
public class LogInterceptor {
    @Pointcut("execution(* com.logistics.service.*.*.*(..)) && @annotation(com.logistics.base.log.Log)")
    private void anyMethod(){}//定义一个切入点

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private ISysOperatorLogService iSysOperatorLogService;

    @AfterReturning(value = "@annotation(log)", argNames = "log")
    public void doAccessCheck(JoinPoint joinPoint,Log log){
        // 判断参数
        if (joinPoint.getArgs() == null) {// 没有参数
            return;
        }
        // 获取方法名
        String methodName = joinPoint.getSignature().getName();
        // 获取操作内容
        String opContent = optionContent(joinPoint.getArgs(), methodName);
        Integer operatorId = (Integer) request.getSession().getAttribute(BaseConstant.SYS_UID);
        String operatorName = (String) CommonUtil.getContentFromJSON(request.getSession().getAttribute(BaseConstant.SYS_USER),"name");
        String ip = ReqUtils.getUserIp(request);
        SysOperatorLog sysOperatorLog = new SysOperatorLog();
        sysOperatorLog.setName(log.operateName());
        sysOperatorLog.setIp(ip);
        sysOperatorLog.setContent(opContent);
        sysOperatorLog.setSysName(operatorName);
        sysOperatorLog.setSysUid(operatorId);
        sysOperatorLog.setCreateTime(new Date());
        iSysOperatorLogService.addOperatorLog(sysOperatorLog);
    }
    /**
     * 使用Java反射来获取被拦截方法(insert、update)的参数值， 将参数值拼接为操作内容
     *
     * @param args
     * @param mName
     * @return
     */
    public String optionContent(Object[] args, String mName) {
        if (args == null) {
            return null;
        }
        StringBuffer rs = new StringBuffer();
        rs.append(mName);
        String className = null;
        int index = 1;
        // 遍历参数对象
        for (Object info : args) {
            // 获取对象类型
            if (info == null) {
                continue;
            }
            className = info.getClass().getName();
            className = className.substring(className.lastIndexOf(".") + 1);
            rs.append("[参数" + index + "，类型:" + className + "，值:");
            if (className.equalsIgnoreCase("String") || className.equalsIgnoreCase("Integer")) {
                rs.append(info);
            }
            index++;
            rs.append("]");
        }
        return rs.toString();
    }
}
