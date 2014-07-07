/**
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-19 下午3:42:13
 */
package cjc.weixinmp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.Date;

/**
 * 公共工具类
 * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-19 下午3:42:13
 */
public class CommonUtils {

    /** ID序列 */
    private static int id = 0;

    /**
     * 获取下一个ID值，这个ID是唯一的
     * @author jianqing.cai@qq.com, https://github.com/caijianqing/weixinmp4java/, 2014-2-25 下午4:07:22
     * @return
     */
    public synchronized static String getNextId() {
        return String.valueOf(new Date().getTime()) + id++;
    }

    /**
     * 把从post读取到的xml保存到文件
     * @param name
     *            文件名
     * @param length
     *            需要读取的长度
     * @param input 一个输入流，调用完毕后这个输入流不会被关闭
     * @return [0]=xmlFile, [1]=StringBuffer
     * @throws IOException
     */
    public static Object[] readXml(File dir, String encoding, String name, int length, InputStream input) throws IOException {

        StringBuffer sb = new StringBuffer();
        File file = new File(dir, name);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int count = 0;
            while (count < length) {
                int len = input.read(buf);
                if (len == 0) {
                    Thread.sleep(10); // 如果读取到0字节，则留休息一会免得CPU走火入魔
                    continue;
                } else if (len == -1) {
                    break;
                } else {
                    count += len;
                    sb.append(new String(buf, 0, len, encoding));
                    fos.write(buf, 0, len);
                }
            }
            fos.flush();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new Object[] { file, sb };
    }

    /**
     * 修改文件名
     * @param file
     *            文件
     * @param sign
     *            在{@link #DATA_FILENAME_SUFFIC}后缀名前增加的记号
     */
    public static void renameFile(File file, String sign, String suffix) {
        String name = file.getAbsolutePath();
        int index = name.lastIndexOf(suffix);
        String newName = name.substring(0, index) + "_" + sign + name.substring(index, name.length());
        file.renameTo(new File(newName));
    }

    /** 十六进制数字序列 */
    private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    /**
     * 转换为十六进制字符串
     * @param bytes
     *            二进制数组
     * @return 十六进制的字符串
     */
    public static String toHexString(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    /**
     * sha1加密
     * @param str
     *            数据原文
     * @return sha1密文，如果失败将返回null
     */
    public static String sha1(String str) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("sha1");
            md.update(str.getBytes());
            byte[] hash = md.digest();
            return toHexString(hash);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

}
