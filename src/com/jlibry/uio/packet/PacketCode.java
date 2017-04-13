/**
 * 	UIO Code class.
 *
 *   @author    sielskyi (SLL)
 *   @version   01.01 2016/12/21 Initial version.
 */

package com.jlibry.uio;

public class Code
{
    /* Public constants */
    public static final int FIELD_SIZE    = 2;    // Size of code in bytes

    public static final int CLASS_MSG_ALARM_EVENT    = 0;    // Alarm event message
    public static final int CLASS_MSG_ALARM_RESTORE  = 1;    // Alarm restore message  
    public static final int CLASS_MSG_WARN_EVENT     = 2;    // Warning event message
    public static final int CLASS_MSG_WARN_RESTORE   = 3;    // Warning restore message
    public static final int CLASS_MSG_SIMPLE_EVENT   = 4;    // Simple event message
    public static final int CLASS_MSG_SIMPLE_RESTORE = 5;    // Simple restore message
    public static final int CLASS_CMD_GET            = 6;    // Command to get
    public static final int CLASS_CMD_SET            = 7;    // Command to set
    public static final int CLASS_CMD_RES_GET        = 8;    // Command result of get
    public static final int CLASS_CMD_RES_SET        = 9;    // Command result of set
    public static final int CLASS_MSG_RES_EVENT      = 8;    // Message result for event
    public static final int CLASS_MSG_RES_RESTORE    = 9;    // Command result for restore 

    public static final int CLASS_STATUS_MSG_EVENT	    = 0;
    public static final int CLASS_STATUS_MSG_RESTORE    = 1;
    public static final int CLASS_STATUS_CMD_GET		= 0;
    public static final int CLASS_STATUS_CMD_SET		= 1;
    public static final int CLASS_STATUS_CMD_GET_RESULT		= 0;
    public static final int CLASS_STATUS_CMD_SET_RESULT		= 1;
    public static final int CLASS_STATUS_MSG_EVENT_RESULT	= 0;
    public static final int CLASS_STATUS_MSG_RESTORE_RESULT	= 1;

    // Codes values
    public static final int CODE_REQUEST                  = 0x0000;   // Request for messages
    public static final int CODE_NOMSG                    = 0x0000;   // No message 
    public static final int CODE_VALUE                    = 0x0001;    // value
    public static final int CODE_VALUE_OVER               = 0x0002;    // value is over
    public static final int CODE_VALUE_UNDER              = 0x0003;    // value is under
    public static final int CODE_VALUE_HIGH               = 0x0004;    // value is high
    public static final int CODE_VALUE_LOW                = 0x0005;    // value is low    
    public static final int CODE_VALUE_MAX                = 0x0006;    // value is max
    public static final int CODE_VALUE_MIN                = 0x0007;    // value is min
    public static final int CODE_TURN_ON                  = 0x0008;    // turned on
    public static final int CODE_ENABLE                   = 0x0009;    // enabled
    public static final int CODE_INSTALL                  = 0x000A;
    public static final int CODE_INSTALL_COMMIT           = 0x000B;
    public static final int CODE_MODE                     = 0x000C;
    public static final int CODE_STATE                    = 0x000D;
    public static final int CODE_STATUS                   = 0x000E;
    public static final int CODE_INFO                     = 0x000F;
    public static final int CODE_UIO                      = 0x0010;   // incapsulated UIO data
    public static final int CODE_PROTOCOL_DATA            = 0x0011;   // incapsulated protocol

    public static final int CODE_RESTART                  = 0x0020;
    public static final int CODE_RESET                    = 0x0021;
    public static final int CODE_BOOT_ID                  = 0x0022;
    public static final int CODE_BOOT_MODE                = 0x0023;
    public static final int CODE_MEMORY                   = 0x0024;

    public static final int CODE_SEEK_UNIT                = 0x00FF;
    public static final int CODE_NONE					  = 0xFFFF;

    /* Private constants */
    private static final int CLASS_ITEM_MASK	= 0xF000;
    private static final int CLASS_ITEM_POS	    = 12;
    private static final int CLASS_ITEM_LEN	    = 4;
    private static final int CLASS_STATUS_ITEM_MASK	    = 0x1000;
    private static final int CLASS_STATUS_ITEM_POS	    = 12;
    private static final int CLASS_STATUS_ITEM_LEN	    = 1;
    private static final int CODE_ITEM_MASK	    = 0x0FFF;
    private static final int CODE_ITEM_POS	    = 0;
    private static final int CODE_ITEM_LEN	    = 12;

    /* Fields */
    private int code;

    public Code() {
        clear();
    }

    public Code(int code) {
        clear();
        set(code);
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
        code = CODE_NONE;
    }

    /** \memberof set
     Set code.
     \param 	code    - code to set.
     \return	void.
     */
    public void set(int code) {
        this.code = code;
    }

    /** \memberof set
     Set code by class and code.
     \param 	codeval - code value.
     \param     codecls - code class.
     \return	true if set is OK.
     */
    public boolean set(int codeval, int codecls)
    {
        if (setCodeValue(codeval) && setCodeClass(codecls)) {
            return true;
        }
        clear();
        return false;
    }

    /** \memberof setCodeValue
     Set code Value.
     */
    public boolean setCodeValue(int codeval) {
        code &= ~CODE_ITEM_MASK;
        code += (codeval << CODE_ITEM_POS) & CODE_ITEM_MASK;
        return true;
    }

    /** \memberof setCodeClass
     Set code Class.
     */
    public boolean setCodeClass(int codecls) {
        code &= ~CLASS_ITEM_MASK;
        code += (codecls << CLASS_ITEM_POS) & CLASS_ITEM_MASK;
        return true;
    }

    /** \memberof get
     Get code.
     */
    public int get() {
        return code;
    }

    /** \memberof getCodeValue
     Get code Value.
     */
    public int getCodeValue() {
        return getCodeValue(code);
    }

    /** \memberof getCodeClass
     Get code Class.
     */
    public int getCodeClass() {
        return getCodeClass(code);
    }

    /** \memberof parsePacket
     Parse packet for code field.

     \param	packarr	- reference to code field in packet.
     \param	len		- length of packet from code field.
     \return	Length of code field in packet.
     */
    public int parsePacket(byte[] packarr, int len)
    {
        if (packarr == null || len < FIELD_SIZE) {
            return 0;
        }
        clear();
        code = packarr[0] & 0xFF;
        code += (packarr[1] & 0xFF) << 8;

        return FIELD_SIZE;
    }

    /** \memberof makePacket
     Make code field for packet.

     \param	packarr	- reference to code field in packet.
     \param	len		- available length of packet from code field.
     \return	Length of code field in packet.
     */
    public int makePacket(byte[]  packarr,
                          int len)
    {
        if (packarr == null || len < FIELD_SIZE) {
            return 0;
        }
        packarr[0] = (byte) code;
        packarr[1] = (byte) (code >> 8);

        return FIELD_SIZE;
    }

    /** \memberof getValue
     Get external code value. Static.
     */
    public static int getCodeValue(int code) {
        return ((code & CODE_ITEM_MASK) >> CODE_ITEM_POS);
    }

    /** \memberof getClass
     Get external code class. Static.
     */
    public static int getCodeClass(int code) {
        return ((code & CLASS_ITEM_MASK) >> CLASS_ITEM_POS);
    }

}
