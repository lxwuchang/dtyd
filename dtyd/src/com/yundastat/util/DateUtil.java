/**
* $Id: DateUtil.java,v 1.1 2012/05/17 22:50:07 wuchang Exp $
*
*/
package com.yundastat.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtil {
	

	/** ����+ʱ��ĸ�ʽ */
	final static public String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	/** ���ڵĸ�ʽ */
	final static public String DATE_FORMAT = "yyyy-MM-dd";
	/** Сʱ:�ֵĸ�ʽ */
	final static public String HHMM_FORMAT = "HH:mm";
	
	final static public String HHMMSS_FORMAT = "HH:mm:ss";
	
	/**
	 * �������ַ������ָ����ʽ��Date����
	 * 
	 * @param dateTime �����ַ�
	 * @param format ָ����ʽ
	 * @return ����ȷ��ʽ�����ڶ���
	 * @throws ParseException
	 */
	public static Date parse(String dateTime, String format) throws ParseException {
		if(dateTime == null || dateTime.length() <= 0) return null;
		String sDateTime = ((dateTime.indexOf('.') > 0))?dateTime.substring(0,dateTime.indexOf('.')):dateTime;
		
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.parse(sDateTime);
	}
	
	/**
	 * �������ַ������"yyyy-MM-dd HH:mm:ss"��ʽ��Date����
	 * 
	 * @param dateTime �����ַ�
	 * @param format ָ����ʽ
	 * @return ����ȷ��ʽ�����ڶ���
	 * @throws ParseException
	 */
	public static Date parseDateTime(String dateTime) throws ParseException {
		return parse(dateTime, DATE_TIME_FORMAT);
	}
	/**
	 * �������������ָ����ʽ�������ַ�
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatDateTime(Date date, String format) {
		if(date == null) return null;
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
	
	/**
	 * �������ַ������"yyyy-MM-dd"��ʽ��Date����
	 * 
	 * @param dateTime �����ַ�
	 * @param format ָ����ʽ
	 * @return ����ȷ��ʽ�����ڶ���
	 * @throws ParseException
	 */
	public static Date parseDate(String dateTime) throws ParseException {
		return parse(dateTime, DATE_FORMAT);
	}
	/**
	 * �������������"HH:mm"��ʽ�������ַ�
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatHHmm(Date date) {
		return formatDateTime(date, HHMM_FORMAT);
	}
	
	/**
	 * �������������"HH:mm:ss"��ʽ�������ַ�
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatHHmmss(Date date) {
		return formatDateTime(date, HHMMSS_FORMAT);
	}
	/**
	 * �������������"yyyy-MM-dd"��ʽ�������ַ�
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String formatYYYYMMDD(Date date) {
		return formatDateTime(date, DATE_FORMAT);
	}
	/**
	 * �������������"yyyy-MM-dd HH:mm:ss"��ʽ�������ַ�
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static String format(Date date) {
		return formatDateTime(date, DATE_TIME_FORMAT);
	}
	
	/**
	 * ��������
	 * 
	 * @param year 1900-...
	 * @param month 0-11
	 * @param monthDay 1-31
	 * @return
	 */
	public static Date toDate(int year, int month, int day) {
		if (!checkDay(year, month, day)) dealErrorDay(year, month, day);
		
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day);
		
		return cal.getTime();
	}
	
	/**
	 * ������λ���
	 * 
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		if(date == null) return -1;
		return date.getYear() + 1900;
	}	
	/**
	 * ��������(0-11)
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonth(Date date) {
		if(date == null) return -1;
		return date.getMonth();
	}
	/**
	 * �����¼�(1-31) 
	 * 
	 * @param date
	 * @return
	 */
	public static int getMonthDay(Date date) {
		if(date == null) return -1;
		return date.getDate();
	}
	/**
	 * �����ܼ�
	 * returned value (<tt>7</tt> = Sunday, <tt>1</tt> = Monday, 
     * <tt>2</tt> = Tuesday, <tt>3</tt> = Wednesday, <tt>4</tt> = 
     * Thursday, <tt>5</tt> = Friday, <tt>6</tt> = Saturday) 
	 * 
	 * @param date
	 * @return
	 */
	public static int getChinaWeekDay(Date date) {
		if(date == null) return -1;
		int d = date.getDay();
		if (d == 0) d = 7;
		
		return d;
	}
	/**
	 * �����ܼ�
	 * returned value (<tt>0</tt> = Sunday, <tt>1</tt> = Monday, 
     * <tt>2</tt> = Tuesday, <tt>3</tt> = Wednesday, <tt>4</tt> = 
     * Thursday, <tt>5</tt> = Friday, <tt>6</tt> = Saturday) 
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeekDay(Date date) {
		if(date == null) return -1;
		return date.getDay();
	}
	/**
	 * ����Сʱ��(0-23)
	 * 
	 * @param date
	 * @return
	 */
	public static int getHours(Date date) {
		if(date == null) return -1;
		return date.getHours();
	}
	/**
	 * ���ط�����0-59��
	 * 
	 * @param date
	 * @return
	 */
	public static int getMinutes(Date date) {
		if(date == null) return -1;
		return date.getMinutes();
	}
	/**
	 * �Ƿ��ǽ���
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isToday(Date date) {
		if(date == null) return false;
		Date today = new Date();		
		return (date.getYear() == today.getYear()
				&& date.getMonth() == today.getMonth()
				&& date.getDate() == today.getDate())? true : false;			
	}
	/**
	 * �Ƿ��ǽ���
	 * 
	 * @param year
	 * @param month
	 * @param monthDay
	 * @return
	 */
	public static boolean isToday(int year, int month, int day) {
		if (!checkDay(year, month, day)) dealErrorDay(year, month, day);
		
		return isToday(toDate(year, month, day));		
	}
	/**
	 * �Ƿ���ĳ��Сʱ�����[0,59)�ڡ�
	 * 
	 * @param date
	 * @param hour
	 * @return
	 */
	public static boolean isInHour(Date date, int hour) {
		return (getHours(date) == hour)? true : false;	
	}	
		
	/**
	 * ĳ��ĳ��ĳ�����һ��
	 * 
	 * @param year
	 * @param month
	 * @param monthDay
	 * @return
	 */
	public static Date getNextDay(int year, int month, int day) {
		if (!checkDay(year, month, day)) dealErrorDay(year, month, day);
		
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day);
		cal.add(Calendar.DAY_OF_YEAR, 1);
		
		return cal.getTime();
	}
	/**
	 * ĳ��ĳ��ĳ�����һ��
	 * �������1900��1��1�գ��򷵻�1900��1��1�ա�
	 * 
	 * @param year
	 * @param month
	 * @param monthDay
	 * @return
	 */
	public static Date getPrevDay(int year, int month, int day) {
		if (!checkDay(year, month, day)) dealErrorDay(year, month, day);
		
//		���ǰ����Ϊ1900-1-1 ǰһ����Ϊ����
		if(year == 1900 && month == 0 && day == 1){
			return toDate(1900, 0, 1);
		}
		
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day);
		cal.add(Calendar.DAY_OF_YEAR, -1);
		
		return cal.getTime();
	}
	
	/**
	 * ĳ��ĳ��ĳ�����һ��
	 * 
	 * @param year
	 * @param month
	 * @param monthDay
	 * @return
	 */
	public static Date getNextDay(Date date) {
		if(date == null) return null;
		int year = getYear(date);
		int month = getMonth(date);
		int day = getMonthDay(date);
		
		return getNextDay(year, month, day);
	}
	/**
	 * ĳ��ĳ��ĳ�����һ��
	 * �������1900��1��1�գ��򷵻�1900��1��1�ա�
	 * 
	 * @param year
	 * @param month
	 * @param monthDay
	 * @return
	 */
	public static Date getPrevDay(Date date) {
		if(date == null) return null;
		int year = getYear(date);
		int month = getMonth(date);
		int day = getMonthDay(date);
		if (year < 1900 || month < 0 || month > 11 || day < 1 || day > 31) return null;
		
		return getPrevDay(year, month, day);
	}

	/**
	 * ĳ��ĳ��ĳ�յ������ܵĵ�һ��(��һ)��
	 * 
	 * @param year
	 * @param month
	 * @param monthDay
	 * @return
	 */
	public static Date getFirstDayOfWeek(int year, int month, int day) {
		if (!checkDay(year, month, day)) dealErrorDay(year, month, day);
		
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day);
		cal.add(Calendar.DAY_OF_WEEK, 1 - getChinaWeekDay(toDate(year, month, day)));
		
		return cal.getTime();
	}
	/**
	 * ĳ��ĳ��ĳ�յ������ܵ���ĩһ��(����)��
	 * 
	 * @param year
	 * @param month
	 * @param monthDay
	 * @return
	 */
	public static Date getEndDayOfWeek(int year, int month, int day) {
		if (!checkDay(year, month, day)) dealErrorDay(year, month, day);
		
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day);
		cal.add(Calendar.DAY_OF_WEEK, 7 - getChinaWeekDay(toDate(year, month, day)));
		
		return cal.getTime();
	}
	/**
	 * ĳ��ĳ��ĳ�յ������ܵ���һ�ܵ�һ��(��һ)��
	 * 
	 * @param year
	 * @param month
	 * @param monthDay
	 * @return
	 */
	public static Date getFirstDayOfNextWeek(int year, int month, int day) {
		if (!checkDay(year, month, day)) dealErrorDay(year, month, day);
		
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day);
		cal.add(Calendar.DAY_OF_YEAR, 7);
		Date newDate = cal.getTime();
		
		return getFirstDayOfWeek(getYear(newDate), getMonth(newDate), getMonthDay(newDate));
	}
	/**
	 * ĳ��ĳ��ĳ�յ������ܵ�ǰһ�ܵ�һ��(��һ)��
	 * �������1900��1��1�գ��򷵻�1900��1��1�ա�
	 * 
	 * @param year
	 * @param month
	 * @param monthDay
	 * @return
	 */
	public static Date getFirstDayOfPrevWeek(int year, int month, int day) {
		if (!checkDay(year, month, day)) dealErrorDay(year, month, day);
		
		//���ǰ����Ϊ1900-1-1��1900-1-7������һ�� ��һ�ܵĵ�һ��
		if(year == 1900 && month == 0 && day <= 7){
			return toDate(year, month, 1);
		}
		
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day);
		cal.add(Calendar.DAY_OF_YEAR, -7);
		Date newDate = cal.getTime();
		
		return getFirstDayOfWeek(getYear(newDate), getMonth(newDate), getMonthDay(newDate));
	}
	/**
	 * ĳ��ĳ��ĳ�յ������ܵ���һ����ĩһ��(����)��
	 * 
	 * @param year
	 * @param month
	 * @param monthDay
	 * @return
	 */
	public static Date getEndDayOfNextWeek(int year, int month, int day) {
		if (!checkDay(year, month, day)) dealErrorDay(year, month, day);
		
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day);
		cal.add(Calendar.DAY_OF_YEAR, 7);
		Date newDate = cal.getTime();
		
		return getEndDayOfWeek(getYear(newDate), getMonth(newDate), getMonthDay(newDate));
	}
	/**
	 * ĳ��ĳ��ĳ�յ������ܵ�ǰһ����ĩһ��(����)��
	 * �������1900��1��1�գ��򷵻�1900��1��1�ա�
	 * 
	 * @param year
	 * @param month
	 * @param monthDay
	 * @return
	 */
	public static Date getEndDayOfPrevWeek(int year, int month, int day) {
		if (!checkDay(year, month, day)) dealErrorDay(year, month, day);
		
//		���ǰ����Ϊ1900-1-1��1900-1-7������һ�� ��һ�ܵ����һ��
		if(year == 1900 && month == 0 && day <= 7){
			return toDate(year, month, 7);
		}
		
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day);
		cal.add(Calendar.DAY_OF_YEAR, -7);
		Date newDate = cal.getTime();
		return getEndDayOfWeek(getYear(newDate), getMonth(newDate), getMonthDay(newDate));
	}
	
	/**
	 * ĳ��ĳ�µĵ�һ��
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getFirstDayOfMonth(int year, int month) {
		return toDate(year, month, 1);
	}
	/**
	 * ĳ��ĳ�µ����һ��
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getEndDayOfMonth(int year, int month) {
		return toDate(year, month, getMaxDays(year, month));
	}
	/**
	 * ĳ��ĳ�µ���һ���µĵ�һ�졣
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getFirstDayOfNextMonth(int year, int month) {
		if (year < 1900 || month < 0 || month > 11) return null;
		
		if(month == 11){
			return getFirstDayOfMonth(year+1, 0);
		}
		
		return getFirstDayOfMonth(year, month+1);
	}
	/**
	 * ĳ��ĳ�µ���һ���µ����һ�졣
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getEndDayOfNextMonth(int year, int month) {
		if (year < 1900 || month < 0 || month > 11) return null;
		
		if(month == 11){
			return getEndDayOfMonth(year+1, 0);
		}
		
		return getEndDayOfMonth(year, month+1);
	}
	/**
	 * ĳ��ĳ�µ���һ���µĵ�һ�졣
	 * ע�⣺��С����Ӧ��Ϊ1900��2�¡��磺����1900��1�£��򷵻�1900��1��1��
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getFirstDayOfPrevMonth(int year, int month) {
		if (year < 1900 || month < 0 || month > 11) return null;
		
		if(year == 1900 && month == 0){
			return getFirstDayOfMonth(year, month);
		}
		
		if(month == 0){
			return getFirstDayOfMonth(year-1, 11);
		}
		
		return getFirstDayOfMonth(year, month-1);
	}
	/**
	 * ĳ��ĳ�µ���һ���µ����һ�졣
	 * ע�⣺��С����Ӧ��Ϊ1900��2�¡��磺����1900��1�£��򷵻�1900��1��31��
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getEndDayOfPrevMonth(int year, int month) {
		if (year < 1900 || month < 0 || month > 11) return null;
		
		if(year == 1900 && month == 0){
			return getEndDayOfMonth(year, month);
		}
		
		if(month == 0){
			return getEndDayOfMonth(year-1, 11);
		}
		
		return getEndDayOfMonth(year, month-1);
	}
	
	/**
	 * ĳ��ĳ�µ�����
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getMaxDays(int year, int month) {
		if (year < 1900 || month < 0 || month > 11) return 0;
		
		Calendar cal = Calendar.getInstance();
		cal.set(year, month,1);
		
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	/**
	 * ����ĳһ������������ַ�
	 * 
	 * @param date
	 * @return
	 */
	public static String toZeroDateTime(Date date) {
		return formatDateTime(date, DATE_FORMAT) + " 00:00:00";
	}
	/**
	 * ����ĳһ�����ʱ�̵������ַ�
	 * 
	 * @param date
	 * @return
	 */
	public static String toLastDateTime(Date date) {
		return formatDateTime(date, DATE_FORMAT) + " 23:59:59";
	}
	/**
	 * ����ĳһ��������տ�ʼ��������ʱ�̽�β������
	 * 
	 * @param date
	 * @return
	 */
	public static Date toNowDate(Date date) {
		return toDate(DateUtil.getYear(date), DateUtil.getMonth(date), DateUtil.getMonthDay(date));
	}
	
	/**
	 * �ڸ��ʱ���ϼ���n���º��ʱ��,
	 * @param currentDate
	 * @param month  ����Ϊ����
	 * @return YYYY-MM-DD HH:mm:ss
	 * @deprecated ������BUG, ��fengchun����
	 * @see #getThisDayByMonth
	 */
	@Deprecated
	public static String getNextDateAfterMonth(Date currentDate, int month){
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
        Date now = new Date();
		return fmt.format(getThisDayByMonth(now,6));
	}
	/**
	 * �Ƚ����ڣ������ա� 
	 * 
	 * @param d0
	 * @param d1
	 * @return  the value <code>0</code> if the argument d1 is equal to
     *          d0; a value less than <code>0</code> if d0
     *          is before d1 argument; and a value greater than
     *      	<code>0</code> if d0 is after d1 argument.
     * @exception NullPointerException if d0 or d1 is null.
     */
	public static int compareDay(Date d0, Date d1){
		return d0.compareTo(d1);
	}
	/**
	 * d0�Ƿ���[d1,d2]�����������
	 * 
	 * @param d0
	 * @param d1
	 * @param d2
	 * @return
	 * @exception NullPointerException if d0 or d1 or d2 is null.
	 */
	public static boolean isInDayZone(Date d0, Date d1, Date d2){
		return (compareDay(d0,d1)>=0 && compareDay(d1,d2)<=0)?true:false;
	}
    
    /**
     * ����N����֮��֮ǰ����ĳ�졣
     * �������������ҵ���������ڴ��µ����һ�죬�򷵻ش��µ����һ�졣
     * ���磺����(2007-1-31,1)���򷵻�2007-2-28
     * 
     * @param date
     * @param month
     * @return
     */
    public static Date getThisDayByMonth(Date date, int monthNumber){
        if(monthNumber == 0) return date;
        if(date == null) return null;
        
        int year = getYear(date);
        int month = getMonth(date);
        int day = getMonthDay(date);
        if (!checkDay(year, month, day)) dealErrorDay(year, month, day);
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day);
        cal.add(Calendar.MONTH, monthNumber);
        
        return cal.getTime();
    }
	
	/**
	 * ����N��֮��֮ǰ����ĳ�졣
	 * ������������ҵ�����������1900-1-1���򷵻�1900-1-1��
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static Date getThisDayByDay(Date date, int days){
		if(days == 0) return date;
		if(date == null) return null;
		
		int year = getYear(date);
		int month = getMonth(date);
		int day = getMonthDay(date);
		if (!checkDay(year, month, day)) dealErrorDay(year, month, day);
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, day);
		cal.add(Calendar.DAY_OF_YEAR, days);
		
		Date findDate = cal.getTime();
		Date date_1900_1_1 = toDate(1900,0,1);
		if(compareDay(findDate, date_1900_1_1) < 0) return date_1900_1_1;
		
		return findDate;
	}
	

	/**
	 *  ��ʽ���������ͣ�����������ʾ������ʱ��
	 *  ����String ��ʽ���£� 2007��9��4�� 16:32:35
	 * @param date
	 * @return
	 */
	public static String formatDateTimeChinaLocale(Date date){
		if(date == null) return "";
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.DEFAULT, Locale.CHINA);
		return df.format(date);
	}
	
	
	/**
	 *  ��ʽ���������ͣ�����������ʾ������ʱ��
	 *  ����String ��ʽ���£� 2007��9��4�� 
	 * @param date
	 * @return
	 */
	public static String formatDateChinaLocale(Date date){
		if(date == null) return "";
		DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.CHINA);
		return df.format(date);
	}
	
	
	/**
	 * 
	 * �Ƚ�2���������ͣ�����2�������������������ʾǰһ�����ڱȺ�һ��������n�죬�����ʾǰһ���ڱȺ�һ������n��
	 * 
	 * @param compareDate   
	 * @param compareToDate
	 * @return
	 */
	public static int compareDays(Date d1, Date d2){
		if(d1 == null || d2 == null) throw new RuntimeException("Not compare between d1<"+d1+"> and d2<"+d2+">!");
		try {
			d1 = parseDate(formatYYYYMMDD(d1));
			d2 = parseDate(formatYYYYMMDD(d2));
		} catch (ParseException e) {	
			throw new RuntimeException("data format error d1="+d1+",d2="+d2);
		}
		
		int i = (int) ((d1.getTime()-d2.getTime())/(24*3600*1000)) ;
		return i ;
	}
	
	
	public static boolean checkDay(int year, int month, int monthDay) {
		if (year < 1900 || month < 0  || month > 11 || monthDay < 1 || monthDay > 31) return false;
		int maxDay = getMaxDays(year, month);
		if(monthDay > maxDay) return false;
		return true;
	}
	
	public static void dealErrorDay(int year, int month, int monthDay){
		throw new RuntimeException("The day<"+year+","+(month+1)+","+monthDay+"> is ERROR-DAY!");
	}
	
	/**
	 * 
	 * ���µĵ�һ��
	 * @return
	 */
	public static String getMonthFirstDay(){
		Date today = new Date();
		int year = getYear(today);
		int month = getMonth(today)+1;
		int day = 1;
		return year+"-"+month+"-"+"0"+day;
	}
	/**
	 * 
	 * ��ǰʱ��
	 * @return
	 */
	public static String getCurrentDate(){
		return  formatYYYYMMDD(new Date());
	}
	public static Date getCurrentDateTime(){
		return new Date();
	}
	
	public static void main(String []args){
		String dd="2011-5-27 20:34:52";
		try {
			Date ds=DateUtil.parse(dd, DateUtil.DATE_TIME_FORMAT);
			System.out.println(DateUtil.formatYYYYMMDD(ds));
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(DateUtil.format(new Date()));
	}
}
