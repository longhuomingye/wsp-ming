package com.fh.shop.admin.util;

public final class SystemConstant {
    public static String USER = "user";
    static int MAXWidth = 520;// 总宽度
    public static String ERRORJSP = "/error.jsp";//错误界面
    public static final String COMPANY_NAME = "1902";//导出pdf文件的单位
    public static final String TEMPLATE_PATH = "/template";//模板所属文件夹
    public static final String PRODUCT_PDF_TEMPLATE_FILE = "info.html";//制定pdf模板html
    public static final String PRODUCT_WORD_FILE="product-template.xml";//导出word模板文件
    public static final String WORD_SAVE_PATH = "d:/upload/";//存放文件的路径
    public static final String WORD_SUFFIX = ".docx";//word文件后缀
    public static final Integer LOG_STATUS_SUCCESS = 1;//成功状态
    public static final Integer LOG_STATUS_ERROR = 0;//失败状态
    public static final Integer LOGINF_ERROR_COUNT = 3;//连续错误次数
    public static final String USER_PASSWORD_RESET = "123456";//连续错误次数
    //sessionId
    public static String SESSIONId_NAME = "ming_id";
    //域名
    public static String DOMAIN = "ming.shop.com";
    //验证码存活时间
    public static Integer CODE_EXPIRE = 5*60;
    //用户存活时间
    public static Integer USER_EXPIRE = 30*60;
}
