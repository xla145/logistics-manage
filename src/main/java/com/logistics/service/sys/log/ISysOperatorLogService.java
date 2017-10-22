package com.logistics.service.sys.log;

import com.logistics.service.vo.sys.SysOperatorLog;

/**
 * Created by Administrator on 2017/9/21/021.
 */
public interface ISysOperatorLogService {

    /**
     * 添加系统日志信息
     * @param sysOperatorLog
     */
    public void addOperatorLog(SysOperatorLog sysOperatorLog);

}
