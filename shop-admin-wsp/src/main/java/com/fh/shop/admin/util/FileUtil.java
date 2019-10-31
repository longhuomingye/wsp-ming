package com.fh.shop.admin.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.xssf.usermodel.*;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.*;
import java.util.List;

public class FileUtil {

    private static FTPClient ftp = null;


    /**
     * 下载ptf文件
     *
     * @param response
     * @param byffer
     */
    public static void pdfDownload(HttpServletResponse response, ByteArrayOutputStream byffer) {

        // inline在浏览器中直接显示，不提示用户下载
        // attachment弹出对话框，提示用户进行下载保存本地
        // 默认为inline方式
        response.setHeader("Content-Disposition", "attachment; filename=" + UUID.randomUUID().toString() + ".pdf");
        // 不同类型的文件对应不同的MIME类型
        response.setContentType("application/octet-stream;charset=UTF-8");
        ServletOutputStream out;
        try {
            //获取输出流
            out = response.getOutputStream();
            //调用方法下载
            byffer.writeTo(out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置ptf标题
     *
     * @param value
     * @param font
     * @return
     */
    public static PdfPCell createHeadline(String value, Font font) {
        // 创建一个单元格
        PdfPCell cell = new PdfPCell();
        // new Paragraph()是段落的处理，可以设置段落的对齐方式，缩进和间距。
        cell.setPhrase(new Paragraph(value, font));
        //设置单元格的水平对齐方式
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        // 设置单元格的垂直对齐方式
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setMinimumHeight(30);//设置表格行高
        cell.setBorderWidth(0f);//去除表格的边框
        cell.setColspan(19);//跨列
        return cell;
    }

    /**
     * 创建单元格 ptf
     *
     * @param value
     * @param font
     * @param align
     * @return
     */
    public static PdfPCell createCell(String value, Font font, int align) {
        // 创建一个单元格
        PdfPCell cell = new PdfPCell();
        // 设置单元格的垂直对齐方式
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        // 设置单元格的水平对齐方式
        cell.setHorizontalAlignment(align);
        cell.setPhrase(new Phrase(value, font));
        return cell;
    }

    /**
     * 创建表格
     *
     * @param colNumber
     * @return
     */
    public static PdfPTable createTable(int colNumber) {
        // 创建表格
        PdfPTable table = new PdfPTable(colNumber);
        // 设置表格的总宽度
        table.setTotalWidth(SystemConstant.MAXWidth);
        //锁定宽度
        table.setLockedWidth(true);
        // 设置表格的垂直对齐方式
        table.setHorizontalAlignment(Element.ALIGN_CENTER);
        // 设置边框
        table.getDefaultCell().setBorder(1);
        return table;
    }

    /**
     * 内容字体
     *
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static Font keyFont() throws IOException, DocumentException {
        // 设置为中文 STSong-Light是宋体 不嵌入
        BaseFont createFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        //设置内容的字体样式
        Font keyfont = new Font(createFont, 10, Font.BOLD);
        return keyfont;
    }

    /**
     * 标题字体
     *
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    public static Font headFont() throws IOException, DocumentException {
        // 设置为中文 STSong-Light是宋体 不嵌入
        BaseFont createFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
        // 标题字体
        Font headfont = new Font(createFont, 25, Font.BOLD);
        return headfont;
    }

    /**
     * 导出excel
     *
     * @param wirthExcelWB
     * @param response
     */
    public static void excelDownload(XSSFWorkbook wirthExcelWB, HttpServletResponse response) {
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            // 让浏览器识别是什么类型的文件
            response.reset(); // 重点突出
            response.setCharacterEncoding("UTF-8"); // 重点突出
            response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型
            // // 重点突出
            // inline在浏览器中直接显示，不提示用户下载
            // attachment弹出对话框，提示用户进行下载保存本地
            // 默认为inline方式
            response.setHeader("Content-Disposition", "attachment;filename=" + UUID.randomUUID().toString() + ".xlsx");
            wirthExcelWB.write(out);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != out) {
                try {
                    out.close();
                    out = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void wordDownload(Map map, HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateException {
        // 新建配置对象
        Configuration configuration = new Configuration();
        // 设置默认编码
        configuration.setDefaultEncoding("UTF-8");
        // 设置模板所在路径
        configuration.setClassForTemplateLoading(FileUtil.class, "/template");
        // 获取模板对象
        Template t = configuration.getTemplate("test.xml", "UTF-8");
        // 创建文件对象
        File file = new File("D:/" + UUID.randomUUID().toString() + ".docx");
        // 新建文件输出流
        FileOutputStream fos = new FileOutputStream(file);
        // 新建写入器
        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
        // 把数据写入文件里
        t.process(map, osw);
        //刷新缓冲区
        osw.flush();
        //关流
        osw.close();
        FileUtil.downloadFile(request, response, file.getPath(), file.getName());
        // 删除没用的文件
        file.delete();
    }

    /**
     * 下载
     *
     * @param request
     * @param response
     * @param downloadFile
     * @param fileName
     */
    public static void downloadFile(HttpServletRequest request, HttpServletResponse response, String downloadFile,
                                    String fileName) {
        BufferedInputStream bis = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedOutputStream bos = null;
        try {
            File file = new File(downloadFile);
            is = new FileInputStream(file); // 文件流的声明
            os = response.getOutputStream(); // 重点突出(特别注意),通过response获取的输出流，作为服务端向客户端浏览器输出内容的通道
            // 为了提高效率使用缓冲区流
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(os);
            // 处理下载文件名的乱码问题(根据浏览器的不同进行处理)
            if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0) {
                fileName = new String(fileName.getBytes("GB2312"), "ISO-8859-1");
            } else {
                // 对文件名进行编码处理中文问题
                fileName = java.net.URLEncoder.encode(fileName, "UTF-8");// 处理中文文件名的问题
                fileName = new String(fileName.getBytes("UTF-8"), "GBK");// 处理中文文件名的问题
            }
            response.reset(); // 重点突出
            response.setCharacterEncoding("UTF-8"); // 重点突出
            response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型
            // // 重点突出
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);// 重点突出
            int bytesRead = 0;
            byte[] buffer = new byte[4096];
            while ((bytesRead = bis.read(buffer)) != -1) { // 重点
                bos.write(buffer, 0, bytesRead);// 将文件发送到客户端
                bos.flush();
            }

        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        } finally {
            // 特别重要
            // 1. 进行关闭是为了释放资源
            // 2. 进行关闭会自动执行flush方法清空缓冲区内容
            try {
                if (null != bis) {
                    bis.close();
                    bis = null;
                }
                if (null != bos) {
                    bos.close();
                    bos = null;
                }
                if (null != is) {
                    is.close();
                    is = null;
                }
                if (null != os) {
                    os.close();
                    os = null;
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex.getMessage());
            }
        }
    }

    /**
     * 把数据转换为html
     * @param data
     * @param pdfTemplateFile
     * @return
     */
    public static String buildPdfHtml(Map data, String pdfTemplateFile){
        // 将其转换为html
        StringWriter sw = null;
        try {
            Configuration configuration = new Configuration();
            // 解决freemarker的乱码问题
            configuration.setDefaultEncoding("utf-8");
            //指定模板文件所属文件夹
            configuration.setClassForTemplateLoading(FileUtil.class, SystemConstant.TEMPLATE_PATH);
            //指定模板文件
            Template template = configuration.getTemplate(pdfTemplateFile);
            sw = new StringWriter();
            template.process(data, sw);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (TemplateException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return sw.toString();
    }

    /**
     * 下载pdf文件
     * @param response
     * @param htmlContent
     */
    public static void pdfDownloadFile(HttpServletResponse response, String htmlContent) {
        OutputStream os = null;
        com.itextpdf.text.Document document = null;
        PdfWriter writer = null;
        try {
            os = response.getOutputStream(); //重点突出(特别注意),通过response获取的输出流，作为服务端向客户端浏览器输出内容的通道
            // 处理下载文件名的乱码问题(根据浏览器的不同进行处理)
            response.reset(); // 重点突出
            response.setCharacterEncoding("UTF-8"); // 重点突出
            response.setContentType("application/x-msdownload");// 不同类型的文件对应不同的MIME类型 // 重点突出
            response.setHeader("Content-Disposition", "attachment;filename="+ UUID.randomUUID().toString()+".pdf");// 重点突出
            // 1
            document = new com.itextpdf.text.Document();
            // 2
            writer = PdfWriter.getInstance(document, os);
            // 3
            document.open();
            // 4
            XMLWorkerFontProvider fontImp = new XMLWorkerFontProvider(XMLWorkerFontProvider.DONTLOOKFORFONTS);
            fontImp.register("simhei.ttf");
            XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                    new ByteArrayInputStream(htmlContent.getBytes("utf-8")), null, Charset.forName("UTF-8"), fontImp);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        } finally {
            // 特别重要
            // 1. 进行关闭是为了释放资源
            // 2. 进行关闭会自动执行flush方法清空缓冲区内容
            if (null != document) {
                document.close();
                document = null;
            }
            if (null != writer) {
                writer.close();
                writer = null;
            }
            if (null != os) {
                try {
                    os.close();
                    os = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 构造word文件
     * @param data
     * @param templateFile
     * @return
     */
    public static File buildWord(Map data, String templateFile){
        FileOutputStream out = null;
        OutputStreamWriter osw = null;
        File file = null;
        try {
            Configuration configuration = new Configuration();
            //制定模板的路径
            configuration.setClassForTemplateLoading(FileUtil.class, SystemConstant.TEMPLATE_PATH);
            //设置编码
            configuration.setDefaultEncoding("utf-8");
            //指定模板文件夹下的具体模板文件
            Template template = configuration.getTemplate(templateFile);
            file = new File(SystemConstant.WORD_SAVE_PATH+UUID.randomUUID().toString()+SystemConstant.WORD_SUFFIX);
            out = new FileOutputStream(file);
            osw = new OutputStreamWriter(out, "utf-8");
            template.process(data, osw);
            osw.flush();
        } catch (TemplateException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (null != osw) {
                try {
                    osw.close();
                    osw = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                try {
                    out.close();
                    out = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * 创建邮件
     * @param session
     * @param sendMail
     * @param receiveMail
     * @return
     * @throws Exception
     */
    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail,String context,String recipientName) throws Exception {
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);

        // 2. From: 发件人（昵称有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改昵称）
        message.setFrom(new InternetAddress(sendMail, "1902", "UTF-8"));

        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, recipientName, "UTF-8"));

        // 4. Subject: 邮件主题（标题有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改标题）
        message.setSubject("账号异常", "UTF-8");

        // 5. Content: 邮件正文（可以使用html标签）（内容有广告嫌疑，避免被邮件服务器误认为是滥发广告以至返回失败，请修改发送内容）
        message.setContent(context, "text/html;charset=UTF-8");
        //"<h1>您好，您的密码多次输入错误，当前用户已被锁定，请在明天再试</h1>"
        // 6. 设置发件时间
        message.setSentDate(new Date());

        // 7. 保存设置
        message.saveChanges();

        return message;
    }

    // 发件人的 邮箱 和 密码（替换为自己的邮箱和密码）
    // PS: 某些邮箱服务器为了增加邮箱本身密码的安全性，给 SMTP 客户端设置了独立密码（有的邮箱称为“授权码”）,
    // 对于开启了独立密码的邮箱, 这里的邮箱密码必需使用这个独立密码（授权码）。
    public static String myEmailAccount = "1664046428@qq.com";
    public static String myEmailPassword = "vuaepszfzlphbdig";

    // 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般(只是一般, 绝非绝对)格式为: smtp.xxx.com
    public static String myEmailSMTPHost = "smtp.qq.com";


    public static void FaYouJian(String receiveMailAccount,String context,String recipientName) throws Exception {
        // 1. 创建参数配置, 用于连接邮件服务器的参数配置
        Properties props = new Properties();   // 参数配置
        props.setProperty("mail.transport.protocol", "smtp"); // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPHost); // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");  // 需要请求认证

        // PS: 某些邮箱服务器要求 SMTP 连接需要使用 SSL 安全认证 (为了提高安全性, 邮箱支持SSL连接, 也可以自己开启),
        // 如果无法连接邮件服务器, 仔细查看控制台打印的 log, 如果有有类似 “连接失败, 要求 SSL 安全连接” 等错误,
        // 打开下面 /* ... */ 之间的注释代码, 开启 SSL 安全连接。
			 /*
			 // SMTP 服务器的端口 (非 SSL 连接的端口一般默认为 25, 可以不添加, 如果开启了 SSL 连接,
			 //   需要改为对应邮箱的 SMTP 服务器的端口, 具体可查看对应邮箱服务的帮助,
			 //   QQ邮箱的SMTP(SLL)端口为465或587, 其他邮箱自行去查看)
			 final String smtpPort = "465";
			 props.setProperty("mail.smtp.port", smtpPort);
			 props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			 props.setProperty("mail.smtp.socketFactory.fallback", "false");
			 props.setProperty("mail.smtp.socketFactory.port", smtpPort);
			 */

        // 2. 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getDefaultInstance(props);
        session.setDebug(true);     // 设置为debug模式, 可以查看详细的发送 log

        // 3. 创建一封邮件
        MimeMessage message = FileUtil.createMimeMessage(session, myEmailAccount, receiveMailAccount,context,recipientName);

        // 4. 根据 Session 获取邮件传输对象
        Transport transport = session.getTransport();

        // 5. 使用 邮箱账号 和 密码 连接邮件服务器, 这里认证的邮箱必须与 message 中的发件人邮箱一致, 否则报错
        //
        // PS_01: 成败的判断关键在此一句, 如果连接服务器失败, 都会在控制台输出相应失败原因的 log,
        //  仔细查看失败原因, 有些邮箱服务器会返回错误码或查看错误类型的链接, 根据给出的错误
        //  类型到对应邮件服务器的帮助网站上查看具体失败原因。
        //
        // PS_02: 连接失败的原因通常为以下几点, 仔细检查代码:
        //  (1) 邮箱没有开启 SMTP 服务;
        //  (2) 邮箱密码错误, 例如某些邮箱开启了独立密码;
        //  (3) 邮箱服务器要求必须要使用 SSL 安全连接;
        //  (4) 请求过于频繁或其他原因, 被邮件服务器拒绝服务;
        //  (5) 如果以上几点都确定无误, 到邮件服务器网站查找帮助。
        //
        // PS_03: 仔细看log, 认真看log, 看懂log, 错误原因都在log已说明。
        transport.connect(myEmailAccount, myEmailPassword);

        // 6. 发送邮件, 发到所有的收件地址, message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
        transport.sendMessage(message, message.getAllRecipients());

        // 7. 关闭连接
        transport.close();
    }

    public static void bulidExcel(List list, Class clazz,String sheetName ,String[] head,String[] propertys, HttpServletResponse response){
        XSSFWorkbook workbook = new XSSFWorkbook();
        //创建一个sheet 同时设置它的名字
        XSSFSheet sheet = workbook.createSheet(sheetName);
        //设置列宽
        sheet.setDefaultColumnWidth(15*1);
        //设置表头
        buildHead(head, sheet);
        //找出需要导出的属性
        for (int i = 0; i <list.size() ; i++) {
            Object o = list.get(i);
            try {
                findProperty(propertys,clazz,o,sheet,workbook,i+1);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        FileUtil.excelDownload(workbook, response);
    }

    static void buildHead(String[] head, XSSFSheet sheet) {
        //设置表头数据
        XSSFRow row = sheet.createRow(0);
        for (int i = 0; i < head.length; i++) {
            row.createCell(i).setCellValue(head[i]);
        }
    }

    private static void findProperty(String[] propertys,Class clazz,Object obj,XSSFSheet sheet,XSSFWorkbook workbook,Integer o) throws NoSuchFieldException, IllegalAccessException {
        //创建行
        XSSFRow row = sheet.createRow(o);
        for (int i = 0; i < propertys.length; i++) {
            //获取对应的属性
            Field field = clazz.getDeclaredField(propertys[i]);
            //设置权限
            field.setAccessible(true);
            //获取当前属性的类型
            Class<?> type = field.getType();
            //创建列
            XSSFCell cell = row.createCell(i);
            //设置数据类型   日期
            XSSFCellStyle cellStyleDate = workbook.createCellStyle();
            cellStyleDate.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
            //设置数据类型   数值
            XSSFCellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
            //进行类型判断 设置为指定的类型
            if(type==Date.class){
                Date date =(Date) field.get(obj);
                cell.setCellValue(date);
                cell.setCellStyle(cellStyleDate);
            }else if(type==BigDecimal.class){
                String value = field.get(obj).toString();
                cell.setCellValue(Double.valueOf(value));
                cell.setCellStyle(cellStyle);
            }else if( type==Integer.class){
                cell.setCellValue(Integer.valueOf(field.get(obj).toString()));
            }else if( type==Long.class){
                cell.setCellValue(Long.valueOf(field.get(obj).toString()));
            }else{
                String value = field.get(obj).toString();
                cell.setCellValue(value);
            }
        }
    }
}
