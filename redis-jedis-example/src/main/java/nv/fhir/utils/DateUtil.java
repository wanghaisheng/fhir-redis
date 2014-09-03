package nv.fhir.utils;

import org.apache.commons.lang.time.DateFormatUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ��Ȩ���ڹ���Ѷ <br/>
 * ���ߣ�kai.gao@rogrand.com <br/>
 * �������ڣ�2013-10-24 <br/>
 * ����������ʱ������࣬�ṩ����ʱ�䳣�÷�����װ
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
     * ��ȡ��ǰ���������ڼ�<br>
     *
     * @param dt
     * @return ��ǰ���������ڼ�
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
     * ��� ��ǰ ���߲���ʱ���ǽ���ĵڼ���
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
            // ���� 1��1�� 0��
            time = sdf.parse(sdf.format(dt)).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (time == 0) {
            return -1;
        }
        // �ӽ���1��1�ŵ����ڹ�ȥ��ʱ��
        long lapsed = System.currentTimeMillis() - time
                + getWeekOfDate(new Date(time)) * 86400000;

        // ��������Ѿ���ȥ��ʱ��
        long remainder = lapsed % (86400000 * 7);

        return (int) ((lapsed - remainder) / (86400000 * 7) + (remainder == 0 ? 0
                : 1));
    }

    /**
     * ��� �����κ�
     *
     * @param time ���timeΪ�� �򷵻ص������κ� ����ǽ����һ�� ����ȥ������һ��
     * @return
     */
    public static int getBatchid(long time) {
        // �����ǽ���ĵڼ���
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        int index = DateUtil.getWeekIndex(time == 0 ? null : new Date(time));
        int year = Integer.valueOf(sdf.format(time == 0 ? new Date()
                : new Date(time)));
        // ����ǵ�һ�� ����ȥ������һ��
        if (index == 1) {
            index = DateUtil.getWeekIndex(time == 0 ? null : new Date(time
                    - (8640000 * 7)));
            year -= 1;
        }
        return year * 100 + index;
    }

    /**
     * ����: yong.chen@rogrand.com <br/>
     * <p/>
     * �������� ��2013��11��5��
     * <p/>
     * Date
     *
     * @param date    ��ʼʱ��
     * @param addTime ����ʱ������
     * @param unit    ʱ�䵥λ ʱ �� �� �� �� �� ��
     * @return
     */

    public static Date addTime(Date date, int addTime, int unit) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(unit, addTime);
        return cal.getTime();
    }

    /**
     * �õ���ǰ�����ַ��� ��ʽ��yyyy-MM-dd�� pattern����Ϊ��"yyyy-MM-dd" "HH:mm:ss" "E"
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
