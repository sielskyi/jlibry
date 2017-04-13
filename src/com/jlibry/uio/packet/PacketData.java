/** \file UIOPacketData.java
 * 	UIO packet data class.
 *
 *   \section    sect1   Descriptions
 *   \section    sect99   Version log
 *   - 01.01: 2016/12/21	Initial version. Author: sielskyi (SLL)
 */

package com.jlibry.uio;

import java.util.Arrays;

/** \class UIOCode
 A UIO paket data class.
 */
public class UIOPacketData
{
    /* Public constants */
    public static final int FIELD_SIZE_MIN    = 1;   // Size of data lentype
                                                    // is minimum data length

    public static final int DATA_ARRAY_SIZE	= 256;
    public static final int DATA_LENTYPE_SIZE_MIN = 1;

    public static final int DATA_TYPE_NONE		= 0x00;    // Bits: 0
    public static final int DATA_TYPE_BOOLEAN      = 0x01;    // Bits: 1

    public static final int DATA_TYPE_UINT8        = 0x04;    // Bits: 8
    public static final int DATA_TYPE_SINT8        = 0x05;    // Bits: 8

    public static final int DATA_TYPE_UINT16       = 0x08;    // Bits: 16
    public static final int DATA_TYPE_SINT16       = 0x09;    // Bits: 16
    public static final int DATA_TYPE_PTR16        = 0x0A;    // Bits: 16

    public static final int DATA_TYPE_UINT32       = 0x0C;    // Bits: 32
    public static final int DATA_TYPE_SINT32       = 0x0D;    // Bits: 32
    public static final int DATA_TYPE_PTR32        = 0x0E;    // Bits: 32
    public static final int DATA_TYPE_FLOAT        = 0x0F;    // Bits: 32
    public static final int DATA_TYPE_UINT64       = 0x10;    // Bits: 64
    public static final int DATA_TYPE_SINT64       = 0x11;    // Bits: 64
    public static final int DATA_TYPE_PTR64        = 0x12;    // Bits: 64
    public static final int DATA_TYPE_DOUBLE       = 0x13;    // Bits: 64
    public static final int DATA_TYPE_UINT128      = 0x14;    // Bits: 128
    public static final int DATA_TYPE_SINT128      = 0x15;    // Bits: 128
    public static final int DATA_TYPE_PTR128       = 0x16;    // Bits: 128
    public static final int DATA_TYPE_LDOUBLE      = 0x17;    // Bits: 128
    public static final int DATA_TYPE_UINT256      = 0x18;    // Bits: 256
    public static final int DATA_TYPE_SINT256      = 0x19;    // Bits: 256

    public static final int DATA_TYPE_ARRAY        = 0x20;     // Bytes: N
    public static final int DATA_TYPE_ARRAY_1BL    = 0x21;     // Bytes: 0-255; AddField 1B of Length
    public static final int DATA_TYPE_ARRAY_2BL    = 0x22;     // Bytes: 0-65535; AddField 2B of Length
    public static final int DATA_TYPE_ARRAY_4BL    = 0x23;     // Bytes: 0-0xFFFFFFFF; AddField 4B of Length
    public static final int DATA_TYPE_STRING       = 0x24;     // Bites: N
    public static final int DATA_TYPE_STRING_1BL   = 0x25;     // Bytes: 0-255; AddField 1B of Length
    public static final int DATA_TYPE_STRING_2BL   = 0x26;     // Bytes: 0-65535; AddField 2B of Length
    public static final int DATA_TYPE_STRING_4BL   = 0x27;     // Bytes: 0-0xFFFFFFFF; AddField 4B of Length

    /* Private constants */
    private static final int DATA_LENTYPE_MASK	= 0xFF;
    private static final int DATA_LENTYPE_LENGTH_MAX	= 0x7F;

    private static final int DATA_TYPE_FLAG_ITEM_MASK	= 0x80;
    private static final int DATA_TYPE_FLAG_ITEM_POS		= 7;
    private static final int DATA_TYPE_FLAG_ITEM_LEN		= 1;
    private static final int DATA_LEN_ITEM_MASK		= 0x7F;
    private static final int DATA_LEN_ITEM_POS		= 0;
    private static final int DATA_LEN_ITEM_LEN		= 7;
    private static final int DATA_TYPE_ITEM_MASK		= 0x7F;
    private static final int DATA_TYPE_ITEM_POS		= 0;
    private static final int DATA_TYPE_ITEM_LEN		= 7;

    /* Fields */
    private int code;

    private int	type;
    private int	length;
    byte[]	data;

    /** \memberof UIOPacketData
        Constructor.
     */
    public UIOPacketData() {
        data = new byte[DATA_ARRAY_SIZE];
        clear();
    }

    public UIOPacketData(int type,
                         byte[] arr,
                         int len)
    {
        data = new byte[DATA_ARRAY_SIZE];
        clear();
        set(type, arr, len);
    }

    /** \memberof readyToUse
        Check for ready to use class.
     */
    public boolean readyToUse() {
        return (data != null);
    }

    /** \memberof clear
        Clear code to none value.
     */
    public void clear() {
        type = DATA_TYPE_NONE;
        length = 0;
    }

    /** \memberof isLengthValid
        Get status of length validity. Static.
     */
    public static boolean isLengthValid(int len) {
        return (len < DATA_ARRAY_SIZE);
    }

    /** \memberof isTypeValid
         Get length of data by type. Static.

         \param	type	- type for check.
         \return	Lenght of data or -1 if invalid type.
     */
    public static boolean isTypeValid(int type)
    {
        if ((type & DATA_TYPE_FLAG_ITEM_MASK) == 0) {
            // Data length in lentype field
            return true;
        }
        else
        {
            // Data LenType is Type
            type = type & (~DATA_TYPE_FLAG_ITEM_MASK);
            if (type == DATA_TYPE_NONE
                || type == DATA_TYPE_BOOLEAN
                || (type >= DATA_TYPE_UINT8
                && type <= DATA_TYPE_SINT256))
            {
                return true;
            }
            else if(type >= DATA_TYPE_ARRAY
                    && type <= DATA_TYPE_STRING_4BL)
            {
                type = type - DATA_TYPE_ARRAY;
                type &= 0x03;
                if (type != 0)  // Check length of data type
                {
                    type--;
                    type = 1 << type;   // Length of field
                    if (type <= 2)
                    {
                        // Length field is not more than length type
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /** \memberof set
        Set all fields of data.
     */
    public boolean set(int type,
                       byte[]  arr,
                       int len)
    {
        if (!readyToUse()
            || arr == null
            || len == 0
            || len > data.length
            || !isTypeValid(type))
        {
            clear();
        }
        else
        {
            this.type = type;
            length = len;
            System.arraycopy(arr, 0, data, 0, len);
            if ((type & DATA_TYPE_FLAG_ITEM_MASK) == 0)
            {
                if (len > DATA_LENTYPE_LENGTH_MAX) {
                    type = DATA_TYPE_ARRAY_2BL | DATA_TYPE_FLAG_ITEM_MASK;
                }
            }
            return true;
        }
        return false;
    }

    /** \memberof getType
        Get number Type.
     */
    public int getType() {
        return type;
    }

    /** \memberof getLength
        Get data length.
     */
    public int getLength() {
        return length;
    }

    /** \memberof getArray
        Get data Array.
     */
    public int getArray(byte[]  arr,
                        int len)
    {
        if(!readyToUse()
            || arr == null
            || len < length)
        {
            return 0;
        }
        System.arraycopy(data, 0, arr, 0, length);
        return length;
    }

    /** \memberof parsePacket
         Parse packet for data fields.

         \param	packarr	- pointer to data lentype field in packet.
         \param	len		- length of packet from data lentype field.
         \return	Length of data fields in packet.
     */
    public int parsePacket(byte[]  packarr,
                           int len)
    {
        int reslen;
        int typetemp;

        if (!readyToUse()
            || packarr == null
            || len < DATA_LENTYPE_SIZE_MIN)
        {
            return 0;
        }
        clear();
        type = packarr[0] & 0xFF;
        len -= DATA_LENTYPE_SIZE_MIN;
        reslen = DATA_LENTYPE_SIZE_MIN;

        if ((type & DATA_TYPE_FLAG_ITEM_MASK) == 0)
        {
            // Data length in lentype field
            length = type;
            type = DATA_TYPE_NONE;
        }
        else
        {
            // Data LenType is Type
            typetemp = type & (~DATA_TYPE_FLAG_ITEM_MASK);
            if (typetemp == DATA_TYPE_NONE) {

            }
            else if (typetemp == DATA_TYPE_BOOLEAN) {
                length = 1;
            }
            else if (typetemp >= DATA_TYPE_UINT8
                        && typetemp <= DATA_TYPE_SINT256)
            {
                length = 1 << (typetemp / 4);
            }
            else if (typetemp >= DATA_TYPE_ARRAY
                    && typetemp <= DATA_TYPE_STRING_4BL)
            {
                typetemp = typetemp - DATA_TYPE_ARRAY;
                typetemp &= 0x03;
                if (typetemp != 0)  // Check length of data type
                {
                    typetemp--;
                    typetemp = 1 << typetemp;   // Length of field
                    if (typetemp <= len  // Length field is in packet
                        && typetemp <= 4)
                    {
                        // Length field is not more than length type
                        length = packarr[2] & 0xFF;
                        if (typetemp >= 2){
                            length += (packarr[3] & 0xFF) << 8;
                        }
                        if (typetemp >= 3){
                            length += (packarr[4] & 0xFF) << 16;
                        }
                        if (typetemp >= 4){
                            length += (packarr[5] & 0xFF) << 24;
                        }
                        len -= typetemp;
                        reslen += typetemp;
                    }
                    else {
                        reslen = 0;
                    }
                }
                else {
                    reslen = 0;	// Unknown length of data type
                }
            }
            else {
                reslen = 0;  // Unknown type
            }
        }
        if (reslen == 0 || len < length)
        {
            clear();
            reslen = 0;
        }
        else
        {
            if (set(type,
                    Arrays.copyOfRange(packarr, reslen, (reslen + length)),
                    length))
            {
                reslen += length;
            } else {
                reslen = 0;
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
    public int makePacket(byte[] packarr,
                          int len)
    {
        int reslen;
        int typetemp;

        if (!readyToUse()
            || packarr == null
            || len < DATA_LENTYPE_SIZE_MIN)
        {
            return 0;
        }

        if ((type & DATA_TYPE_FLAG_ITEM_MASK) == 0)
        {
            // Data LenType is Length
            packarr[0] = (byte) length;
            len -= DATA_LENTYPE_SIZE_MIN;
            reslen = DATA_LENTYPE_SIZE_MIN;
        }
        else
        {
            // Data LenType is Type
            packarr[0] = (byte) type;
            len -= DATA_LENTYPE_SIZE_MIN;
            reslen = DATA_LENTYPE_SIZE_MIN;

            typetemp = type & (~DATA_TYPE_FLAG_ITEM_MASK);
            if (typetemp == DATA_TYPE_NONE) {

            }else if(typetemp == DATA_TYPE_BOOLEAN) {
                length = 1;
            }
            else if(typetemp >= DATA_TYPE_UINT8
                    && typetemp <= DATA_TYPE_SINT256)
            {
                length = 1 << (typetemp / 4);
            }
            else if(typetemp >= DATA_TYPE_ARRAY
                    && typetemp <= DATA_TYPE_STRING_4BL)
            {
                typetemp -= DATA_TYPE_ARRAY;
                typetemp &= 0x03;
                if (typetemp != 0)  // Check for type length
                {
                    typetemp--;
                    typetemp = 1 << typetemp;   // Length field size
                    if (typetemp <= len  // Length field size is allowed of packet length
                        && typetemp <= 4)
                    {
                        // Length field size is not more than length type
                        packarr[2] = (byte) length;
                        if (typetemp >= 2) {
                            packarr[3] = (byte) (length >> 8);
                        }
                        if (typetemp >= 3) {
                            packarr[4] = (byte) (length >> 16);
                        }
                        if (typetemp >= 4) {
                            packarr[5] = (byte) (length >> 24);
                        }
                        len -= typetemp;
                        reslen += typetemp;
                    }
                    else {
                        reslen = 0;
                    }
                }
                else {
                    reslen = 0;  // Unknown length of type
                }
            }
            else {
                reslen = 0;  // unknown type
            }
        }
        if (reslen == 0 || len < length) {
            reslen = 0;
        }
        else
        {
            System.arraycopy(data, 0, packarr, reslen, length);
            reslen += length;
        }
        return reslen;
    }
}
