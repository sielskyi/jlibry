/** \file UIOUnitDsc.java
 * 	UIO unit descriptor class.
 *
 *   \section    sect1   Descriptions
 *   \section    sect99   Version log
 *   - 01.01: 2016/12/21	Initial version. Author: sielskyi (SLL)
 */

package com.jlibry.uio;

/** \class UIOUnitDsc
    A UIO unit descriptor class.
 */
public class UIOUnitDsc
{
    /* Public constants */
    public static final int FIELD_SIZE    = 2;    // Size of dsc field in bytes

    public static final int UNITDSC_UNKNOWN    = 0xFFFF;    // unknown

    /* Private constants */
    private static final int VALUE_MASK	= 0xFFFF;

    /* Fields */
    private int value;

    /** \memberof UIOCode
        Constructor.
     */
    public UIOUnitDsc() {
        clear();
    }

    public UIOUnitDsc(int val) {
        value = val;
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
        value = UNITDSC_UNKNOWN;
    }

    /** \memberof set
        Set descriptor.
     */
    public void set(int val) {
        value = val;
    }

    /** \memberof get
        Get descriptor.
     */
    public int get() {
        return value;
    }

    /** \memberof parsePacket
     Parse packet for uint descriptor field.

     \param	packarr	- reference to code field in packet.
     \param	len		- length of packet from code field.
     \return	Length of uint descriptor field in packet.
     */
    public int parsePacket(byte[] packarr, int len)
    {
        if (packarr == null || len < FIELD_SIZE) {
            return 0;
        }
        clear();
        value = packarr[0] & 0xFF;
        value += (packarr[1] & 0xFF) << 8;

        return FIELD_SIZE;
    }

    /** \memberof makePacket
     Make uint descriptor field to packet.

     \param	packarr	- reference to code field in packet.
     \param	len		- available length of packet from code field.
     \return	Length of uint descriptor field in packet.
     */
    public int makePacket(byte[]  packarr,
                          int len)
    {
        if (packarr == null || len < FIELD_SIZE) {
            return 0;
        }
        packarr[0] = (byte) value;
        packarr[1] = (byte) (value >> 8);

        return FIELD_SIZE;
    }

}
