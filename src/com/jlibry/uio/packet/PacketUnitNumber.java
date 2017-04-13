/** \file UIOPacketUnitNumber.java
 * 	UIO packet unit number class.
 *
 *   \section    sect1   Descriptions
 *   \section    sect99   Version log
 *   - 01.01: 2016/12/21	Initial version. Author: sielskyi (SLL)
 */

package com.jlibry.uio;

import java.util.Arrays;

/** \class UIOPacketStatus
    A UIO packet unit number class.
 */
public class UIOPacketUnitNumber
{
    /* Public constants */
    public static final int FIELD_SIZE_MIN    = 1;    // Size of code in bytes
    public static final int NUMBER_INVALID = 0xFFFF;		// Invalid virtuel number

    public static final int FROM_MAIN_DEVICE   = 0x00;		// From main device
    public static final int TO_MAIN_DEVICE     = 0x20;		// To main device
    public static final int TO_UNKNOWN         = 0xFF;		// Unknown unit
    public static final int FROM_UNKNOWN       = 0xDF;		// Unknown unit
    public static final int TO_ALL             = 0x3F;		// To All units
    public static final int FROM_ALL           = 0x1F;		// From All units
    public static final int TO_ALL_SYSTEM      = 0x3E;		// To All units
    public static final int FROM_ALL_SYSTEM    = 0x1E;		// From All units
    public static final int TO_ALL_INPUT       = 0x7E;		// To All units
    public static final int FROM_ALL_INPUT     = 0x5E;		// From All units
    public static final int TO_ALL_OUTPUT      = 0xBE;		// To All units
    public static final int FROM_ALL_OUTPUT    = 0x9E;		// From All units
    public static final int TO_ALL_IFACE       = 0xFE;		// To All units
    public static final int FROM_ALL_IFACE     = 0xDE;		// From All units
    public static final int TO_SYSTEM_BY_ID    = 0x3D;		// To system units by ID
    public static final int FROM_SYSTEM_BY_ID  = 0x1D;		// From system units by ID

    public static final int UNIT_CLASS_SYSTEM    = 0;
    public static final int UNIT_CLASS_INPUT     = 1;
    public static final int UNIT_CLASS_OUTPUT    = 2;
    public static final int UNIT_CLASS_IFACE     = 3;

    public static final int TYPE_DIR_TO_UNIT     = 1;
    public static final int TYPE_DIR_FROM_UNIT   = 0;

    public static final int TYPE_NUM_0           = 0x00;     // Add 0B
    public static final int TYPE_NUM_256         = 0x01;     // Add 1B
    public static final int TYPE_NUM_65536       = 0x02;     // Add 2B
    public static final int TYPE_MASK_1_16       = 0x04;     // Add 2B
    public static final int TYPE_MASK_17_32      = 0x05;     // Add 2B
    public static final int TYPE_MASK_1_32       = 0x06;     // Add 4B
    public static final int TYPE_MASK_33_64      = 0x07;     // Add 4B
    public static final int TYPE_MASK_1_64       = 0x08;     // Add 8B
    public static final int TYPE_MASK_65_128     = 0x09;     // Add 8B
    public static final int TYPE_MASK_1_128      = 0x0A;     // Add 16B
    public static final int TYPE_MASK_129_256    = 0x0B;     // Add 16B
    public static final int TYPE_MASK_1_256      = 0x0C;     // Add 32B
    public static final int TYPE_MASK_257_512    = 0x0D;     // Add 32B
    public static final int TYPE_MASK_1_512      = 0x0E;     // Add 64B
    public static final int TYPE_MASK_513_1024   = 0x0F;     // Add 64B
    public static final int TYPE_BY_ID			= 0x1D;     // By ID (add 9 B)
    public static final int TYPE_CLASS_ALL		= 0x1E;     // All units of class
    public static final int TYPE_ALL				= 0x1F;     // All units

/* Private constants */
    private static final int TYPE_MASK			= 0xFF;
    private static final int UNIT_CLASS_ITEM_MASK	= 0xC0;
    private static final int UNIT_CLASS_ITEM_POS	= 6;
    private static final int UNIT_CLASS_ITEM_LEN	= 2;
    private static final int TYPE_DIR_ITEM_MASK   = 0x20;
    private static final int TYPE_DIR_ITEM_POS    = 5;
    private static final int TYPE_DIR_ITEM_LEN    = 1;
    private static final int TYPE_ITEM_MASK		= 0x1F;
    private static final int TYPE_ITEM_POS		= 0;
    private static final int TYPE_ITEM_LEN		= 5;

    private static final int NUMBER_ARRAY_SIZE	= 64;

    /* Fields */
    private int	type;
    private int number;
    private int	numarrlen;
    private byte[] numarr;

    /** \memberof UIOPacketStatus
        Constructor.
     */
    public UIOPacketUnitNumber()
    {
        numarr = new byte[NUMBER_ARRAY_SIZE];
        clear();
    }

    /** \memberof UIOPacketStatus
        Constructor for single unit number initialize.
     */
    public UIOPacketUnitNumber(int unitclass,
                               int dir,
                               int type,
                               int number)
    {
        numarr = new byte[NUMBER_ARRAY_SIZE];
        set(unitclass, dir, type, number, null, 0);
    }

    /** \memberof UIOPacketStatus
        Constructor for any unit number initialize.
     */
    public UIOPacketUnitNumber(int unitclass,
                        int dir,
                        int type,
                        int number,
                        byte[]  numarr,
                        int numarrlen)
    {
        numarr = new byte[NUMBER_ARRAY_SIZE];
        set(unitclass, dir, type, number, numarr, numarrlen);
    }

    /** \memberof readyToUse
     Check for ready to use class.
     */
    public boolean readyToUse() {
        return (numarr != null);
    }

    /** \memberof clear
        Clear code to none value.
     */
    public void clear()
    {
        type = FROM_MAIN_DEVICE;
        number = NUMBER_INVALID;
        numarrlen = 0;
        if (numarr != null) {
            Arrays.fill(numarr, (byte) 0);
        }
    }

    /** \memberof isNumberValid
        Get status of number validity. Static.
     */
    static public boolean isNumberValid(int num)
    {
        return (num < NUMBER_INVALID);
    }

    /** \memberof isTypeValid
        Get status of Type validity. Static.
     */
    static public boolean isTypeValid(int type)
    {
        switch (type)
        {
            case TYPE_NUM_256:
            case TYPE_NUM_65536:
            case TYPE_NUM_0:
            case TYPE_CLASS_ALL:
            case TYPE_ALL:
            case TYPE_BY_ID: {
                return true;
            }
            default: {
                if (type >= TYPE_MASK_1_16
                    && type <= TYPE_MASK_513_1024)
                {
                    return true;
                }
            }
        }
        return false;
    }

    /** \memberof set
     Set all fields of number.
     */
    public boolean set(int unitclass,
                        int dir,
                        int type,
                        int number,
                        byte[]  numarr,
                        int numarrlen)
    {
        if (readyToUse()
            && setUnitClass(unitclass)
            && setDirection(dir)
            && setType(type)
            && setNumber(number)
            && setNumberArray(numarr, numarrlen))
        {
            return true;
        }
        clear();
        return false;
    }

    /** \memberof setUnitClass
     Set number UnitClass.
     */
    public boolean setUnitClass(int val)
    {
        type &= ~UNIT_CLASS_ITEM_MASK;
        type += (val << UNIT_CLASS_ITEM_POS) & UNIT_CLASS_ITEM_MASK;
        return true;
    }

    /** \memberof setDirection
     Set number Direction.
     */
    public boolean setDirection(int val)
    {
        type &= ~TYPE_DIR_ITEM_MASK;
        type += (val << TYPE_DIR_ITEM_POS) & TYPE_DIR_ITEM_MASK;
        return true;
    }

    /** \memberof setType
     Set number Type.
     */
    public  boolean setType(int val)
    {
        if (!isTypeValid(val)) {
            return false;
        }
        type &= ~TYPE_ITEM_MASK;
        type += (val << TYPE_ITEM_POS) & TYPE_ITEM_MASK;
        return true;
    }

    /** \memberof setFullType
     Set number FullType.
     */
    public boolean setFullType(int val)
    {
        if (!isTypeValid(getType(val))) {
            return false;
        }
        type = val;
        return true;
    }

    /** \memberof setNumber
     Set number Number.
     */
    public boolean setNumber(int val)
    {
        number = val;
        return true;
    }

    /** \memberof setNumberArray
     Set number Array.
     */
    public boolean setNumberArray(byte[]  numarr, int len)
    {
        if (!readyToUse()
            || numarr == null
            || len == 0
            || len > NUMBER_ARRAY_SIZE)
        {
            numarrlen = 0;
            return false;
        }
        numarrlen = len;
        System.arraycopy(numarr, 0, this.numarr, 0, len);
        return true;
    }

    /** \memberof getFullType
     Get FullType of number.
     */
    public int getFullType()
    {
        return type;
    }

    /** \memberof getUnitClass
     Get number UnitClass.
     */
    public int getUnitClass()
    {
        return getUnitClass(type);
    }

    /** \memberof getDirection
     Get number Direction.
     */
    public int getDirection()
    {
        return getDirection(type);
    }

    /** \memberof getType
     Get number Type.
     */
    public int getType()
    {
        return getType(type);
    }

    /** \memberof getNumber
     Get Number.
     */
    public int getNumber()
    {
        return number;
    }

    /** \memberof getNumberArray
     Get number Array.
     */
    public int getNumberArray(byte[]  arr, int len)
    {
        if (!readyToUse()
            || arr == null
            || len < numarrlen)
        {
            return 0;
        }
        System.arraycopy(numarr, 0, arr, 0, numarrlen);
        return numarrlen;
    }

    /** \memberof toogleNumber
     Toogle number to next.
     */
    public void toogleNumber()
    {
        int tnum;

        if (!readyToUse()) {
            return;
        }
        switch (getType(type))
        {
            case TYPE_CLASS_ALL:
            case TYPE_ALL: {
                if (isNumberValid(number)) {
                    number++;
                }
                break;
            }
            default:
            {
                if (getType(type) >= TYPE_MASK_1_16
                    && getType(type) <= TYPE_MASK_513_1024
                    && numarrlen != 0
                    && number >= getMinNumberByType(type)
                    && number <= getMaxNumberByType(type))
                {
                    tnum = getMinNumberByType(type);
                    number -= tnum;
                    tnum = (getMaxNumberByType(type) - tnum) + 1;
                    clearNumberToMask(number, numarr, tnum);
                    number = getNumberFromArray();
                }
                else{
                    number = NUMBER_INVALID;
                }
            }
        }
    }

    /** \memberof parsePacket
     Parse packet for number fields.

     \param	packarr	- pointer to numbertype field in packet.
     \param	len		- length of packet from number field.
     \return	Length of number fields in packet.
     */
    public int parsePacket(byte[]  packarr, int len)
    {
        int reslen;

        if (!readyToUse()
            || packarr == null
            || len < FIELD_SIZE_MIN)
        {
            return 0;
        }
        clear();
        type = packarr[0] & 0xFF;
        len -= FIELD_SIZE_MIN;
        reslen = FIELD_SIZE_MIN;

        switch (getType()) {
            case TYPE_NUM_256: {
                if (len > 1) {
                    number = packarr[1] & 0xFF;
                    reslen += 1;
                } else {
                    reslen = 0;
                }
                break;
            }
            case TYPE_NUM_65536:
            {
                if (len > 2)
                {
                    number = packarr[1] & 0xFF;
                    number += ((packarr[2] & 0xFF) << 8);
                    reslen += 2;
                }
                else {
                    reslen = 0;
                }
                break;
            }
            case TYPE_NUM_0:
            case TYPE_CLASS_ALL:
            case TYPE_ALL: {
                break;
            }
            case TYPE_BY_ID:
            {
                numarrlen = getArrayLengthByType(TYPE_BY_ID);
                if (len > numarrlen) {
                    System.arraycopy(packarr, 1, numarr, 0, numarrlen);
                    number = packarr[2] & 0xFF;
                    number += ((packarr[3] & 0xFF) << 8);
                    reslen += numarrlen;
                } else {
                    numarrlen = 0;
                    reslen = 0;
                }
                break;
            }
            default:
            {
                if (getType() >= TYPE_MASK_1_16
                    && getType() <= TYPE_MASK_513_1024)
                {
                    numarrlen = getArrayLengthByType(getType());
                    if (len > numarrlen) {
                        System.arraycopy(packarr, 1, numarr, 0, numarrlen);
                        number = getNumberFromArray();
                        reslen += numarrlen;
                    }
                    else
                    {
                        numarrlen = 0;
                        reslen = 0;
                    }
                }
                else {
                    reslen = 0;
                }
            }
        }
        return reslen;
    }

    /** \memberof makePacket
     Make number fields for packet.

     \param	packarr	- pointer to numbertype field in packet.
     \param	len		- available length of packet from number field.
     \return	Length of number fields in packet.
     */
    public int makePacket(byte[]  packarr,
                                  int len)
    {
        int reslen;

        if (!readyToUse()
            || packarr == null
            || len < FIELD_SIZE_MIN)
        {
            return 0;
        }

        packarr[0] = (byte) type;
        len -= FIELD_SIZE_MIN;
        reslen = FIELD_SIZE_MIN;

        switch (getType(type))
        {
            case TYPE_NUM_256:
            {
                if (len > 1)
                {
                    packarr[1] = (byte) number;
                    reslen += 1;
                }
                else {
                    reslen = 0;
                }
                break;
            }
            case TYPE_NUM_65536:
            {
                if (len > 2)
                {
                    packarr[1] = (byte) number;
                    packarr[2] = (byte) (number >> 8);
                    reslen += 2;
                }
                else {
                    reslen = 0;
                }
                break;
            }
            case TYPE_NUM_0:
            case TYPE_CLASS_ALL:
            case TYPE_ALL: {
                break;
            }
            case TYPE_BY_ID:
            {
                if (numarrlen >= 9 && len > numarrlen)
                {
                    System.arraycopy(numarr, 0, packarr, 1, numarrlen);
                    packarr[2] = (byte) number;
                    packarr[3] = (byte) (number >> 8);
                    reslen += numarrlen;
                }
                else {
                    reslen = 0;
                }
                break;
            }
            default:
            {
                if (type >= TYPE_MASK_1_16
                    && type <= TYPE_MASK_513_1024)
                {
                    if (len > numarrlen)
                    {
                        System.arraycopy(numarr, 0, packarr, 1, numarrlen);
                        reslen += numarrlen;
                    }
                    else {
                        reslen = 0;
                    }
                }
                else {
                    reslen = 0;  // Unknown type
                }
            }
        }
        return reslen;
    }

    /** \memberof getArrayLengthByType
     Get length of number array by type. Static.
     */
    public static int getArrayLengthByType(int type)
    {
        int len;

        len = 0;
        type = getType(type);
        switch (type)
        {
            case TYPE_BY_ID: {
                len = 9;
                break;
            }
            default:
            {
                if (type >= TYPE_MASK_1_16
                    && type <= TYPE_MASK_513_1024)
                {
                    type -= TYPE_MASK_1_16;
                    len = 2 << (type / 2);
                }
            }
        }
        return len;
    }

    /** \memberof getMinNumberByType
     Get minimum number by type. Static.
     */
    public static int getMinNumberByType(int type)
    {
        int num;

        num = 0;
        type = getType(type);
        if (type >= TYPE_MASK_1_16
            && type <= TYPE_MASK_513_1024)
        {
            type -= TYPE_MASK_1_16;
            type = type / 2;
            num = 16 << (type / 2);
            if ((type & 1) != 0) {
                num = 1 + num;
            } else {
                num = 1;
            }
        }
        return num;
    }

    /** \memberof getMaxNumberByType
     Get maximum number by type. Static.
     */
    public static int getMaxNumberByType(int type)
    {
        int num;

        num = 0;
        type = getType(type);
        switch (type)
        {
            case TYPE_NUM_256: {
                num = 0xFF;
                break;
            }
            case TYPE_NUM_65536:
            case TYPE_CLASS_ALL:
            case TYPE_ALL:
            case TYPE_BY_ID: {
                num = 0xFFFF;
                break;
            }
            default:
            {
                if (type >= TYPE_MASK_1_16
                    && type <= TYPE_MASK_513_1024)
                {
                    type -= TYPE_MASK_1_16;
                    num = 16 << (type / 2);
                    if ((type & 1) != 0) {
                        num = (num * 2);
                    }
                }
            }
        }
        return num;
    }

    /** \memberof getUnitClass
     Get external status UnitClass. Static.
     */
    public static int getUnitClass(int val) {
        return ((val & UNIT_CLASS_ITEM_MASK) >> UNIT_CLASS_ITEM_POS);
    }

    /** \memberof getDirection
     Get external status Direction. Static.
     */
    public static int getDirection(int val) {
        return ((val & TYPE_DIR_ITEM_MASK) >> TYPE_DIR_ITEM_POS);
    }

    /** \memberof getType
     Get external status Type. Static.
     */
    public static int getType(int val) {
        return ((val & TYPE_ITEM_MASK) >> TYPE_ITEM_POS);
    }


    private int getNumberFromArray()
    {
        int num;
        int tnum;

        num = NUMBER_INVALID;
        if (getType(type) >= TYPE_MASK_1_16
            && getType(type) <= TYPE_MASK_513_1024
            && numarrlen != 0)
        {
            tnum = (getMaxNumberByType(type) - getMinNumberByType(type)) + 1;
            num = getNumberFromMask(numarr, 0, tnum);
            if (num <= tnum) {
                num += getMinNumberByType(type);
            }
        }
        return num;
    }

    private int getNumberFromMask(byte[] pmask,
                                 int beginnum,
                                 int maxnum)
    {
        int i;
        int indx;
        byte bmask;

        if (beginnum >= maxnum) {
            return maxnum;
        }
        indx = beginnum >> 3;
        i = beginnum & 0x07;
        bmask = 1;
        for (; i < maxnum; i++)
        {
            if (pmask[indx] != 0)
            {
                if ((pmask[indx] & bmask) != 0) {
                    break;
                }
                bmask <<= 1;
                if (bmask == 0)
                {
                    bmask = 1;
                    indx++;
                }
            }
            else
            {
                indx++;
                i = indx << 3;
                bmask = 1;
            }
        }
        return i;
    }

    private void setNumberToMask(int num,
                                 byte[] pmask,
                                 int maxnum)
    {
        if (num < maxnum)
        {
            maxnum = num >> 3;
            pmask[maxnum] |= (byte) (1 << (num & 0x07));
        }
    }

    private void clearNumberToMask(int num,
                                   byte[] pmask,
                                   int maxnum)
    {
        if (num < maxnum)
        {
            maxnum = num >> 3;
            pmask[maxnum] &= (byte) (~(1 << (num & 0x07)));
        }
    }

    private boolean isNumberToMaskSet(int num,
                                       byte[] pmask,
                                       int maxnum)
    {
        if (num < maxnum)
        {
            maxnum = num >> 3;
            if ((pmask[maxnum] & (1 << (num & 0x07))) != 0) {
                return true;
            }
        }
        return false;
    }
}
