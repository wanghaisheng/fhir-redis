package nv.fhir.utils;

import org.apache.commons.lang.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
/**
 * 版权：融贯资讯 <br/>
 * 作者：kai.gao@rogrand.com <br/>
 * 生成日期：2013-10-24 <br/>
 * 描述：日期时间帮助类，提供日期时间常用方法封装
 */
public class DateUtil {

    public final static SimpleDateFormat sdFormat1 = new SimpleDateFormat(
            "yyyy-MM-dd");
    public final static SimpleDateFormat sdFormat2 = new SimpleDateFormat(
            "yyyyMMdd");
    public final static SimpleDateFormat sdFormat3 = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss");

    public static String format(Date date, SimpleDateFormat sdf) {
        return sdf.format(date);
    }

    public static String getNow() {
        return sdFormat3.format(new Date());
    }

    public  static String getNowWithZone(){

        TimeZone time = TimeZone.getTimeZone("GMT+8"); //设置为东八区
        time = TimeZone.getDefault();// 这个是国际化所用的
        System.out.println(time);
        TimeZone.setDefault(time);// 设置时区
        Calendar calendar = Calendar.getInstance();// 获取实例
        Date date = calendar.getTime(); //获取Date对象
        return sdFormat3.format(date);//对象进行格式化，获取字符串格式的输出
    }

    public static String getDate(Date date) {
        return sdFormat1.format(date);
    }

    public static String getDateForInt(long i) {
        if (i > 0) {
            return sdFormat3.format(new Date(i * 1000L));
        } else {
            return "";
        }
    }

    public static Date parse(String date) {
        try {
            return sdFormat2.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date parse(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取当前日期是星期几<br>
     *
     * @param dt
     * @return 当前日期是星期几
     */
    public static int getWeekOfDate(Date dt) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;

        return w == 0 ? 7 : w;
    }

    /**
     * 获得 当前 或者参数时间是今年的第几周
     *
     * @param dt
     * @return
     */
    public static int getWeekIndex(Date dt) {
        if (dt == null) {
            dt = new Date();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        long time = 0;
        try {
            // 今年 1月1号 0点
            time = sdf.parse(sdf.format(dt)).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (time == 0) {
            return -1;
        }
        // 从今年1月1号到现在过去的时间
        long lapsed = System.currentTimeMillis() - time
                + getWeekOfDate(new Date(time)) * 86400000;

        // 这个星期已经过去的时间
        long remainder = lapsed % (86400000 * 7);

        return (int) ((lapsed - remainder) / (86400000 * 7) + (remainder == 0 ? 0
                : 1));
    }

    /**
     * 获得 榜单批次号
     *
     * @param time 如果time为空 则返回当期批次号 如果是今年第一周 则检查去年的最后一周
     * @return
     */
    public static int getBatchid(long time) {
        // 本周是今年的第几周
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        int index = DateUtil.getWeekIndex(time == 0 ? null : new Date(time));
        int year = Integer.valueOf(sdf.format(time == 0 ? new Date()
                : new Date(time)));
        // 如果是第一周 则检查去年的最后一周
        if (index == 1) {
            index = DateUtil.getWeekIndex(time == 0 ? null : new Date(time
                    - (8640000 * 7)));
            year -= 1;
        }
        return year * 100 + index;
    }

    /**
     * 作者: yong.chen@rogrand.com <br/>
     * <p/>
     * 生成日期 ：2013年11月5日
     * <p/>
     * Date
     *
     * @param date    开始时间
     * @param addTime 增加时间数量
     * @param unit    时间单位 时 分 秒 日 月 年 等
     * @return
     */

    public static Date addTime(Date date, int addTime, int unit) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(unit, addTime);
        return cal.getTime();
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

	/* public static void main(String[] args) {
		Date d = new Date();
		Date d1 = DateUtil.addTime(d, 30, Calendar.MINUTE);
		System.out.println(d1);
	}*/
}
