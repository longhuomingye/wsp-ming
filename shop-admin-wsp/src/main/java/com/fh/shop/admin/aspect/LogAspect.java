package com.fh.shop.admin.aspect;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.admin.biz.log.ILogService;
import com.fh.shop.admin.common.Log;
import com.fh.shop.admin.common.WebContext;
import com.fh.shop.admin.po.log.LogInfo;
import com.fh.shop.admin.po.user.User;
import com.fh.shop.admin.util.CookieUtil;
import com.fh.shop.admin.util.KeyUtil;
import com.fh.shop.admin.util.RedisUtil;
import com.fh.shop.admin.util.SystemConstant;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

public class LogAspect {
    @Autowired
    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);
    @Resource(name = "logService")
    private ILogService logService;
    public Object logInfo(ProceedingJoinPoint point){
        Object proceed = null;
        //获取request  通过request 获取 用户信息
        HttpServletRequest request = WebContext.getRequset();
        //获取sesssionId
        String sessionId = CookieUtil.read(SystemConstant.SESSIONId_NAME, request);
        //从session中拿到当前登录的用户
        String userJson = RedisUtil.get(KeyUtil.buildUserKey(sessionId));
        User user = JSONObject.parseObject(userJson, User.class);
        if(user!=null){
            //获取用户名
            String userName = user.getUserName();
            //获取当前用户的真实姓名
            String realName = user.getRealName();
            //获取类名
            String canonicalName = point.getTarget().getClass().getCanonicalName();
            //获取方法名
            String name = point.getSignature().getName();
            //获取详细参数信息
            Map<String, String[]> parameterMap = request.getParameterMap();
            StringBuffer str = getStringBuffer(parameterMap);
            String Info = null;
            //获取自定义注解
            MethodSignature msg = (MethodSignature) point.getSignature();
            Method method = msg.getMethod();
            String value = "";
            //判断当前方法是否使用自定义注解
            if(method.isAnnotationPresent(Log.class)){
                Log log = method.getAnnotation(Log.class);
                value = log.value();
            }
            try {
                proceed = point.proceed();
                Info = "用户:"+userName+"执行了:"+canonicalName+"层的"+name+"方法成功";
                LOGGER.info(Info);
                //操作成功保存数据到数据库
                addLogInfo(userName, realName, Info, "",SystemConstant.LOG_STATUS_SUCCESS,str.toString(),value);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                //记录执行失败的日志信息
                Info = "用户:"+userName+"执行了:"+canonicalName+"层的"+name+"方法失败:"+throwable.getMessage();
                LOGGER.error(Info);
                //执行失败 保存数据到数据库
                addLogInfo(userName,realName,Info,throwable.getMessage(),SystemConstant.LOG_STATUS_ERROR,str.toString(),value);
                //把异常抛给上级
                throw new RuntimeException(throwable);
            }
            return proceed;
        }
        return null;
    }

    private StringBuffer getStringBuffer(Map<String, String[]> parameterMap) {
        Iterator<Map.Entry<String, String[]>> iterator = parameterMap.entrySet().iterator();
        StringBuffer str = new StringBuffer();
        //判断是否还有下一个值
        while (iterator.hasNext()){
            //获取一个entry对象
            Map.Entry<String, String[]> next = iterator.next();
            str.append("|"+next.getKey()+":"+StringUtils.join(next.getValue(),",")+";");
        }
        return str;
    }

    private void addLogInfo(String userName, String realName, String info, String errorMsg,Integer status,String detail,String value) {
        LogInfo logInfo = new LogInfo();
        logInfo.setUserName(userName);
        logInfo.setRealName(realName);
        logInfo.setInfo(info);
        logInfo.setCreateDate(new Date());
        logInfo.setErrorMsg(errorMsg);
        logInfo.setStatus(status);
        logInfo.setDetail(detail);
        logInfo.setContent(value);
        logService.addLog(logInfo);
    }
}
