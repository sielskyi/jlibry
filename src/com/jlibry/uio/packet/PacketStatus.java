/** \file UIOPacketStatus.java
 * 	UIO packet status class.
 *
 *   \section    sect1   Descriptions
 *   \section    sect99   Version log
 *   - 01.01: 2016/12/21	Initial version. Author: sielskyi (SLL)
 */

package com.jlibry.uio;

/** \class UIOPacketStatus
 A UIO packet status class.
 */
public class UIOPacketStatus
{
/* Public constants */
    public static final int FIELD_SIZE    = 1;    // Size of code in bytes

    // Codes classes
    public static final int STATUS_NONE = 0;
    public static final int STATUS_TIMEDATE		= 1;     // Add 4B of date and time
    public static final int STATUS_TIMEDATE_NO	= 0;

    public static final int STATUS_UNITDSC		= 1;     // Add 2B of unit descriptor
    public static final int STATUS_UNITDSC_NO	= 0;

    public static final int STATUS_ACKWAIT		= 1;
    public static final int STATUS_ACKWAIT_NO	= 0;

    public static final int STATUS_PRIORITY_HIGH	= 1;
    public static final int STATUS_PRIORITY_LOW	= 0;
    public static final int STATUS_PRIORITY_NO	= 0;

/* Private constants */
    private static final int STATUS_MASK = 0xFF;
    private static final int STATUS_TIMEDATE_ITEM_MASK = 0x01;
    private static final int STATUS_TIMEDATE_ITEM_POS = 0;
    private static final int STATUS_TIMEDATE_ITEM_LEN = 1;
    private static final int STATUS_UNITDSC_ITEM_MASK = 0x02;
    private static final int STATUS_UNITDSC_ITEM_POS = 1;
    private static final int STATUS_UNITDSC_ITEM_LEN = 1;
    private static final int STATUS_ACKWAIT_ITEM_MASK = 0x40;
    private static final int STATUS_ACKWAIT_ITEM_POS = 6;
    private static final int STATUS_ACKWAIT_ITEM_LEN = 1;
    private static final int STATUS_PRIORITY_ITEM_MASK = 0x80;
    private static final int STATUS_PRIORITY_ITEM_POS = 7;
    private static final int STATUS_PRIORITY_ITEM_LEN = 1;

    /* Fields */
    private int status;

    /** \memberof UIOPacketStatus
        Constructor.
    */
    public UIOPacketStatus() {
        clear();
    }

    public UIOPacketStatus(int val) {
        clear();
        set(val);
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
        status = STATUS_NONE;
    }

    /** \memberof set
        Set status value.
        \param 	val    - status to set.
        \return	void.
    */
    public void set(int val) {
        status = val;
    }

    /** \memberof setPriority
        Set status priority.
    */
    public void setPriority(int val) {
        status &= ~STATUS_PRIORITY_ITEM_MASK;
        status += (val << STATUS_PRIORITY_ITEM_POS) & STATUS_PRIORITY_ITEM_MASK;
    }

    /** \memberof setACKWait
        Set status ACKWait.
    */
    public void setACKWait(int val) {
        status &= ~STATUS_ACKWAIT_ITEM_MASK;
        status += (val << STATUS_ACKWAIT_ITEM_POS) & STATUS_ACKWAIT_ITEM_MASK;
    }

    /** \memberof setTimeDate
        Set status TimeDate.
    */
    public void setTimeDate(int val) {
        status &= ~STATUS_TIMEDATE_ITEM_MASK;
        status += (val << STATUS_TIMEDATE_ITEM_POS) & STATUS_TIMEDATE_ITEM_MASK;
    }

    /** \memberof setUnitDsc
        Set status UnitDsc.
    */
    public void setUnitDsc(int val) {
        status &= ~STATUS_UNITDSC_ITEM_MASK;
        status += (val << STATUS_UNITDSC_ITEM_POS) & STATUS_UNITDSC_ITEM_MASK;
    }

    /** \memberof get
        Get status.
    */
    public int get() {
        return status;
    }

    /** \memberof getPriority
        Get status Priority.
    */
    public int getPriority() {
        return getPriority(status);
    }

    /** \memberof getACKWait
        Get status ACKWait.
    */
    public int getACKWait() {
        return getACKWait(status);
    }

    /** \memberof getTimeDate
        Get status TimeDate.
    */
    public int getTimeDate() {
        return getTimeDate(status);
    }

    /** \memberof getUnitDsc
        Get status UnitDsc.
    */
    public int getUnitDsc() {
        return getUnitDsc(status);
    }

    /** \memberof parsePacket
     Parse packet for status field.

     \param	packarr	- reference to status field in packet.
     \param	len		- length of packet from status field.
     \return	Length of status field in packet.
     */
    public int parsePacket(byte[]  packarr,
                           int len)
    {
        if (packarr == null || len < FIELD_SIZE) {
            return 0;
        }
        clear();
        status = packarr[0] & 0xFF;

        return FIELD_SIZE;
    }

    /** \memberof makePacket
     Make status field for packet.

     \param	packarr	- reference to status field in packet.
     \param	len		- available length of packet from status field.
     \return	Length of status field in packet.
     */
    public int makePacket(byte[]  packarr,
                          int len)
    {
        if (packarr == null || len < FIELD_SIZE) {
            return(0);
        }
        packarr[0] = (byte) status;

        return(FIELD_SIZE);
    }

    /** \memberof getPriority
        Get external status Priority. Static.
     */
    public static int getPriority(int val) {
        return ((val & STATUS_PRIORITY_ITEM_MASK) >> STATUS_PRIORITY_ITEM_POS);
    }

    /** \memberof getACKWait
        Get external status ACKWait. Static.
     */
    public static int getACKWait(int val) {
        return ((val & STATUS_ACKWAIT_ITEM_MASK) >> STATUS_ACKWAIT_ITEM_POS);
    }

    /** \memberof getTimeDate
        Get external status TimeDate. Static.
     */
    public static int getTimeDate(int val) {
        return ((val & STATUS_TIMEDATE_ITEM_MASK) >> STATUS_TIMEDATE_ITEM_POS);
    }

    /** \memberof getUnitDsc
        Get external status UnitDsc. Static.
     */
    public static int getUnitDsc(int val) {
        return ((val & STATUS_UNITDSC_ITEM_MASK) >> STATUS_UNITDSC_ITEM_POS);
    }
}
