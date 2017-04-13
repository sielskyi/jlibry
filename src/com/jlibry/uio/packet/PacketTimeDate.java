/** \file UIOPacketTimeDate.java
 * 	UIO packet timedate class.
 *
 *   \section    sect1   Descriptions
 *   \section    sect99   Version log
 *   - 01.01: 2016/12/21	Initial version. Author: sielskyi (SLL)
 */

package com.jlibry.uio;

/** \class UIOPacketTimeDate
    A UIO paket timedate field class.

    TimeDate presentation: 4B:	bits 0-5	- second
    bits 6-11	- minutes
    bits 12-16	- hour
    bits 17-21	- day
    bits 22-25	- month
    bits 26-31	- year

 */
public class UIOPacketTimeDate
{
    /* Public constants */
    public static final int FIELD_SIZE   = 4;   // Size of timedate field

    /* Private constants */
    private static final int TIMEDATE_MASK			= 0xFFFFFFFF;
    private static final int DATE_REF_YEAR			= 2016;

    private static final int TIME_SECOND_ITEM_MASK	= 0x3F;
    private static final int TIME_SECOND_ITEM_POS	= 0;
    private static final int TIME_SECOND_ITEM_LEN	= 6;
    private static final int TIME_MINUTE_ITEM_MASK	= 0x0FC0;
    private static final int TIME_MINUTE_ITEM_POS	= 6;
    private static final int TIME_MINUTE_ITEM_LEN	= 6;
    private static final int TIME_HOUR_ITEM_MASK	= 0x1F000;
    private static final int TIME_HOUR_ITEM_POS	= 12;
    private static final int TIME_HOUR_ITEM_LEN	= 5;
    private static final int DATE_DAY_ITEM_MASK	= 0x3E0000;
    private static final int DATE_DAY_ITEM_POS	= 17;
    private static final int DATE_DAY_ITEM_LEN	= 5;
    private static final int DATE_MONTH_ITEM_MASK	= 0x03C00000;
    private static final int DATE_MONTH_ITEM_POS	= 22;
    private static final int DATE_MONTH_ITEM_LEN	= 4;
    private static final int DATE_YEAR_ITEM_MASK	= 0xFC000000;
    private static final int DATE_YEAR_ITEM_POS	= 26;
    private static final int DATE_YEAR_ITEM_LEN	= 6;

    /* Fields */
    private int 	year;
    private int	    month;
    private int 	day;
    private int 	hour;
    private int 	minute;
    private int 	second;
    private int 	refyear;

    /** \memberof UIOPacketTimeDate
     Constructor.
     */
    public UIOPacketTimeDate() {
        clear();
    }

    public UIOPacketTimeDate(int refyear)
    {
        clear();
        setReferenceYear(refyear);
    }

    public UIOPacketTimeDate(int refyear,
                              int year,
                              int month,
                              int day,
                              int hour,
                              int minute,
                              int second)
    {
        clear();
        set(refyear, year, month, day, hour, minute, second);
    }

    /** \memberof readyToUse
     Check for ready to use class.
     */
    public boolean readyToUse() {
        return true;
    }

    /** \memberof clear
     Clear code to none value.
     */
    public void clear() {
        year = 0;
        month = 1;
        day = 1;
        hour = 0;
        minute = 0;
        second = 0;
        refyear = DATE_REF_YEAR;
    }

    /** \memberof set
        Set all fields of timedate.
     */
    public boolean set(int refyear,
                 int year,
                 int month,
                 int day,
                 int hour,
                 int minute,
                 int second)
    {
        if  (setReferenceYear(refyear)
                && setYear(year)
                && setMonth(month)
                && setDay(day)
                && setHour(hour)
                && setMinute(minute)
                && setSecond(second))
        {
            return true;
        }
        clear();
        return false;
    }

    /** \memberof setReferenceYear
        Set reference year.
     */
    public boolean setReferenceYear(int val)
    {
        if (val < DATE_REF_YEAR) {
            return(false);
        }
        refyear = ((val - DATE_REF_YEAR) & (~DATE_YEAR_ITEM_MASK))
                + DATE_REF_YEAR;
        return true;
    }

    /** \memberof setYear
     Set year.
     */
    public boolean setYear(int val)
    {
        if (val < DATE_REF_YEAR) {
            return false;
        }
        year = val;
        return true;
    }

    /** \memberof setMonth
     Set month.
     */
    public boolean setMonth(int val)
    {
        if(val < 1 || val > 12){
            return false;
        }
        month = val;
        return true;
    }

    /** \memberof setDay
     Set day.
     */
    public boolean setDay(int val)
    {
        if(val < 1 || val > 31){
            return false;
        }
        day = val;
        return true;
    }

    /** \memberof setHour
     Set hour.
     */
    public boolean setHour(int val)
    {
        if (val > 23) {
            return false;
        }
        hour = val;
        return true;
    }

    /** \memberof setMinute
     Set minute.
     */
    public boolean setMinute(int val)
    {
        if (val > 59) {
            return false;
        }
        minute = val;
        return true;
    }

    /** \memberof setSecond
     Set second.
     */
    public boolean setSecond(int val)
    {
        if(val > 59){
            return false;
        }
        second = val;
        return true;
    }

    /** \memberof getReferenceYear
     Get reference year.
     */
    public int getReferenceYear() {
        return refyear;
    }

    /** \memberof getYear
     Get year.
     */
    public int getYear() {
        return (year + refyear);
    }

    /** \memberof getMonth
     Get month.
     */
    public int getMonth() {
        return month;
    }

    /** \memberof getDay
     Get day.
     */
    public int getDay() {
        return day;
    }

    /** \memberof getHour
     Get hour.
     */
    public int getHour() {
        return hour;
    }

    /** \memberof getMonute
     Get minute.
     */
    public int getMinute() {
        return minute;
    }

    /** \memberof getSecond
     Get second.
     */
    public int getSecond() {
        return second;
    }


    /** \memberof parsePacket
     Parse packet for timedate fields.

     \param	packarr	- link to timedate field in packet.
     \param	len		- length of packet from timedate field.
     \return	Length of timedate field in packet.
     */
    public int parsePacket(byte[]  packarr, int len)
    {
        int timedate;

        if (packarr == null || len < FIELD_SIZE) {
            return 0;
        }
        clear();
        timedate = packarr[0] & 0xFF;
        timedate |= (packarr[1] & 0xFF) << 8;
        timedate |= (packarr[2] & 0xFF) << 16;
        timedate |= (packarr[3] & 0xFF) << 24;

        setYear((int) ((timedate & DATE_YEAR_ITEM_MASK) >> DATE_YEAR_ITEM_POS));
        setMonth((int) ((timedate & DATE_MONTH_ITEM_MASK) >> DATE_MONTH_ITEM_POS));
        setDay((int) ((timedate & DATE_DAY_ITEM_MASK) >> DATE_DAY_ITEM_POS));
        setHour((int) ((timedate & TIME_HOUR_ITEM_MASK) >> TIME_HOUR_ITEM_POS));
        setMinute((int) ((timedate & TIME_MINUTE_ITEM_MASK) >> TIME_MINUTE_ITEM_POS));
        setSecond((int) ((timedate & TIME_SECOND_ITEM_MASK) >> TIME_SECOND_ITEM_POS));

        return FIELD_SIZE;
    }

    /** \memberof makePacket
     Make timedate field for packet.

     \param	packarr	- pointer to timedate field in packet.
     \param	len		- available length of packet from timedate field.
     \return	Length of timedate field in packet.
     */
    public int makePacket(byte[]  packarr, int len)
    {
        int timedate;

        if (packarr == null || len < FIELD_SIZE) {
            return 0;
        }
        timedate = (second << TIME_SECOND_ITEM_POS) & TIME_SECOND_ITEM_MASK;
        timedate |= (minute << TIME_MINUTE_ITEM_POS) & TIME_MINUTE_ITEM_MASK;
        timedate |= (hour << TIME_HOUR_ITEM_POS) & TIME_HOUR_ITEM_MASK;
        timedate |= (day << DATE_DAY_ITEM_POS) & DATE_DAY_ITEM_MASK;
        timedate |= (month << DATE_MONTH_ITEM_POS) & DATE_MONTH_ITEM_MASK;
        timedate |= (year << DATE_YEAR_ITEM_POS) & DATE_YEAR_ITEM_MASK;

        packarr[0] = (byte) timedate;
        packarr[1] = (byte) (timedate >> 8);
        packarr[2] = (byte) (timedate >> 16);
        packarr[3] = (byte) (timedate >> 24);

        return FIELD_SIZE;
    }
}
