/**
 * 	Unit class.
 *
 *   @author    sielskyi (SLL)
 *   @version   01.01 2016/12/21 Initial version.
 */

package com.sielskyi.integralconfig;

public abstract class Unit extends Object
{
/* Public constants */
    // Types
    public static final int TYPE_NONE       = 0;
    public static final int TYPE_UNKNOWN    = 0;
    public static final int TYPE_DEVICE     = 1;    // physical device
    public static final int TYPE_UNION      = 2;	// logical group of units with the different type
    public static final int TYPE_GROUP      = 3;	// logical group of units with the same type
    public static final int TYPE_USER       = 4;

    public static final int TYPE_INPUT_UNKNOWN  = 64;
    public static final int TYPE_INPUT_STATE2   = 65;
    public static final int TYPE_INPUT_STATE3   = 66;
    public static final int TYPE_INPUT_STATE5   = 67;
    public static final int TYPE_INPUT_PERCENT  = 68;
    public static final int TYPE_INPUT_VALUE    = 69;
    public static final int TYPE_INPUT_UNIQUE   = 70;   // Add unit field

    public static final int TYPE_OUTPUT_UNKNOWN  = 128;
    public static final int TYPE_OUTPUT_STATE2   = 129;
    public static final int TYPE_OUTPUT_STATE3   = 130;
    public static final int TYPE_OUTPUT_STATE5   = 131;
    public static final int TYPE_OUTPUT_PERCENT  = 132;
    public static final int TYPE_OUTPUT_VALUE    = 133;
    public static final int TYPE_OUTPUT_UNIQUE   = 134;

    public static final int TYPE_IFACE_UNKNOWN   = 192;
    public static final int TYPE_IFACE_KEYBOARD  = 193;

    // Descriptors
    public static final int DSC_UNKNOWN  = 0;

/* Private constants */
    //private static final int PRIVATE = 0;

/* Fields */
    private int type;
    private int dsc;
    private int number;

/* Methods */
    public Unit() {
        clear();
    }

    public Unit(int type, int dsc, int number) {
        setType(type);
        setDsc(dsc);
        setNumber(number);
    }

    /** Clear code to none value. */
    public void clear() {
        type = TYPE_UNKNOWN;
        dsc = DSC_UNKNOWN;
        number = 0;
    }

    /** Get value of unit type. */
    public int getType() {
        return type;
    }

    /** Set value of unit type. */
    public void setType(int type) {
        this.type = type;
    }

    /** Get value of unit descriptor. */
    public int getDsc() {
        return dsc;
    }

    /** Set value of unit descriptor. */
    public void setDsc(int dsc) {
        this.dsc = dsc;
    }

    /** Get value of unit number. */
    public int getNumber() {
        return number;
    }

    /** GSet value of unit number. */
    public void setNumber(int number) {
        this.number = number;
    }

    /** Get string of unit type. */
    abstract public String getTypeString();

    /** Get string of unit descriptor. */
    abstract public String getDscString();

}
