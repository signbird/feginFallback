package org.bqf.common.util;

import org.bqf.common.dto.Header;

/**
 * 项目名称: Rainbow Stone for cartoon
 * 包: com.migu.rstone.util
 * 类名称: HeaderFactory.java
 * 类描述: 
 * 创建人: Administrator
 * 创建时间: 2017年11月7日
 */
public class HeaderFactory {

    public static Header getHeader() {
        Header requestHeader = new Header();
        // TODO
        requestHeader.setVersion(HeaderConstant.VERSION);
        requestHeader.setCompanyId(HeaderConstant.PORTAL_TYPE);
        requestHeader.setProductLine(HeaderConstant.PRODUCT_LINE);
        requestHeader.setPlatform(HeaderConstant.PORTAL_TYPE);
        requestHeader.setAppid(HeaderConstant.TO_BE_DETEMINED);
        requestHeader.setImei(HeaderConstant.TO_BE_DETEMINED);
        requestHeader.setImsi(HeaderConstant.TO_BE_DETEMINED);
        requestHeader.setClientVer(HeaderConstant.TO_BE_DETEMINED);
        requestHeader.setAppid(HeaderConstant.TO_BE_DETEMINED);
        requestHeader.setAppName(HeaderConstant.TO_BE_DETEMINED);
        requestHeader.setAccessInfo(HeaderConstant.TO_BE_DETEMINED);
        requestHeader.setPortalType(HeaderConstant.PORTAL_TYPE);
        
        return requestHeader;
    }
}
