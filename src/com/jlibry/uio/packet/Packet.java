/** \file UIOPacket.java
 * 	UIO packet class.
 *
 *   \section    sect1   Descriptions
 *   \section    sect99   Version log
 *   - 01.01: 2016/12/21	Initial version. Author: sielskyi (SLL)
 */

package com.jlibry.uio;

import com.jlibry.uio.UIOCode;
import com.jlibry.uio.UIOPacketStatus;
import com.jlibry.uio.UIOPacketUnitNumber;
import com.jlibry.uio.UIOPacketTimeDate;
import com.jlibry.uio.UIOUnitDsc;
import com.jlibry.uio.UIOPacketData;

import java.util.Arrays;

/** \class UIOPacket
    A UIO packet class.

 UIO packet:
 CODE,STAT,NUM_TYPE,[NUM[NUM_N]],[DATE_TIME_STAMP],    ->
 ->  [UNIT_DSC],DATA_LEN_TYPE,[DATA_LEN],[DATA]

 */
public class UIOPacket
{
    /* Public constants */
    public static final int SIZE_MIN    = 5;    // Size of code in bytes
    public static final int SIZE_MAX    = 256;    // Size of code in bytes


    /* Private constants */

    /* Fields */
    public UIOCode             code;
    public UIOPacketStatus     status;
    public UIOPacketUnitNumber number;
    public UIOPacketTimeDate   timeDate;
    public UIOUnitDsc          unitDsc;
    public UIOPacketData       data;

    private int     length;

    /** \memberof UIOCode
     Constructor.
     */
    public UIOPacket()
    {
        code = new UIOCode();
        status = new UIOPacketStatus();
        number = new UIOPacketUnitNumber();
        timeDate = new UIOPacketTimeDate();
        unitDsc = new UIOUnitDsc();
        data = new UIOPacketData();

        clear();
    }

    public UIOPacket(byte[] arr, int len)
    {
        code = new UIOCode();
        status = new UIOPacketStatus();
        number = new UIOPacketUnitNumber();
        timeDate = new UIOPacketTimeDate();
        unitDsc = new UIOUnitDsc();
        data = new UIOPacketData();

        parsePacket(arr, len);
    }

    /** \memberof readyToUse
     Check for ready to use class.
     */
    public boolean readyToUse() {
        if (code == null
            || status == null
            || number == null
            || timeDate == null
            || unitDsc == null
            || data == null)
        {
            return false;
        }
        return true;
    }

    /** \memberof clear
     Clear code to none value.
     */
    public void clear()
    {
        if (readyToUse())
        {
            length = 0;
            code.clear();
            status.clear();
            number.clear();
            timeDate.clear();
            unitDsc.clear();
            data.clear();
        }
    }

    /** \memberof getLength
        Get length.
     */
    public int getLength()
    {
        int len;
        byte[] arr;

        arr = new byte[SIZE_MAX];
        len = SIZE_MAX;
        length = makePacket(arr, len);
        return length;
    }

    /** \memberof set
        Set packet.
     */
    public int set(byte[]  packarr, int len)
    {
        return parsePacket(packarr, len);
    }

    /** \memberof get
     Get packet.
     */
    public int get(byte[]  packarr, int len)
    {
        return makePacket(packarr, len);
    }

    /** \memberof parsePacket
         Parse packet for fields.

         \param	packarr	- referece to the packet.
         \param	len		- length of the packet.
         \return	Length of parsed packet.
     */
    public int parsePacket(byte[]  packarr, int len)
    {
        int reslen;
        int tmplen;

        if (!readyToUse()
            || packarr == null
            || len < SIZE_MIN)
        {
            return 0;
        }
        reslen = 0;
        tmplen = code.parsePacket(packarr, len);
        if(tmplen == 0){
            return(0);
        }
        reslen += tmplen;

        tmplen = status.parsePacket(
                    Arrays.copyOfRange(packarr, reslen, len),
                    (len - reslen));

        if (tmplen == 0) {
            return 0;
        }
        reslen += tmplen;

        tmplen = number.parsePacket(
                Arrays.copyOfRange(packarr, reslen, len),
                (len - reslen));
        if (tmplen == 0) {
            return 0;
        }
        reslen += tmplen;

        if (status.getTimeDate() == status.STATUS_TIMEDATE)
        {
            tmplen = timeDate.parsePacket(
                        Arrays.copyOfRange(packarr, reslen, len),
                        (len - reslen));
            if (tmplen == 0) {
                return 0;
            }
            reslen += tmplen;
        }

        if (status.getUnitDsc() == status.STATUS_UNITDSC)
        {
            tmplen = unitDsc.parsePacket(
                        Arrays.copyOfRange(packarr, reslen, len),
                        (len - reslen));
            if (tmplen == 0) {
                return 0;
            }
            reslen += tmplen;
        }

        tmplen = data.parsePacket(
                    Arrays.copyOfRange(packarr, reslen, len),
                    (len - reslen));
        if (tmplen == 0) {
            return 0;
        }
        reslen += tmplen;

        length = reslen;
        return reslen;
    }

    /** \memberof makePacket
         Make packet.

         \param	packarr	- reference to the packet.
         \param	len		- available length of the packet.
         \return	Length of the packet.
     */
    public int makePacket(byte[]  packarr, int len)
    {
        int reslen;
        int tmplen;

        if (!readyToUse()
            || (length < SIZE_MIN && code.get() == code.CODE_NONE)
            || packarr == null
            || len < SIZE_MIN)
        {
            return 0;
        }
        reslen = 0;
        tmplen = code.makePacket(packarr, len);
        if(tmplen == 0){
            return(0);
        }
        reslen += tmplen;

        tmplen = status.makePacket(
                    Arrays.copyOfRange(packarr, reslen, len),
                    (len - reslen));
        if (tmplen == 0) {
            return 0;
        }
        reslen += tmplen;

        tmplen = number.makePacket(
                    Arrays.copyOfRange(packarr, reslen, len),
                    (len - reslen));
        if (tmplen == 0) {
            return 0;
        }
        reslen += tmplen;

        if (status.getTimeDate() == status.STATUS_TIMEDATE)
        {
            tmplen = timeDate.makePacket(
                        Arrays.copyOfRange(packarr, reslen, len),
                        (len - reslen));
            if (tmplen == 0) {
                return 0;
            }
            reslen += tmplen;
        }

        if (status.getUnitDsc() == status.STATUS_UNITDSC)
        {
            tmplen = unitDsc.makePacket(
                        Arrays.copyOfRange(packarr, reslen, len),
                        (len - reslen));
            if (tmplen == 0) {
                return 0;
            }
            reslen += tmplen;
        }

        tmplen = data.makePacket(
                    Arrays.copyOfRange(packarr, reslen, len),
                    (len - reslen));
        if (tmplen == 0) {
            return 0;
        }
        reslen += tmplen;

        return reslen;
    }
}
