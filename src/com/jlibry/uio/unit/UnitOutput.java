/** \file OutputUnit.java
 * 	OutputUnit class.
 *
 *   \section    sect1   Descriptions
 *   \section    sect99   Version log
 *   - 01.01: 2016/12/21	Initial version. Author: sielskyi (SLL)
 */

package com.sielskyi.integralconfig;

/** \class Output
    A OutputUnit main class.
 */
public class OutputUnit extends Unit{

    /* Public constants */
    public static final int DSC_OUTPUT_UNKNOWN = 0;

    /* Private constants */
    //private static final int PRIVATE = 0;

    /* Variables */
    private int mode;

    public OutputUnit() {
        this(TYPE_OUTPUT_UNKNOWN, DSC_UNKNOWN);
    }

    public OutputUnit(int type, int dsc) {
        super.setType(type);
        super.setDsc(dsc);
    }

    /** Clear code to none value. */
    @Override
    public void clear() {
        super.clear();
        mode = 0;
    }

    @Override
    public void setType(int type) {
        if (type >= TYPE_OUTPUT_UNKNOWN
                && type <= (TYPE_OUTPUT_UNKNOWN + 63))
        {
            super.setType(type);
        } else {
            super.setType(TYPE_OUTPUT_UNKNOWN);
        }
    }

    /** Get string of unit type. */
    public String getTypeString() {
        String str;

        str = "output ";
        switch (this.getType())
        {
            case TYPE_OUTPUT_STATE2:
                str += "state 2";
                break;
            case TYPE_OUTPUT_STATE3:
                str += "state 3";
                break;
            case TYPE_OUTPUT_STATE5:
                str += "state 5";
                break;
            case TYPE_OUTPUT_PERCENT:
                str += "percent";
                break;
            case TYPE_OUTPUT_VALUE:
                str += "value";
                break;
            case TYPE_OUTPUT_UNIQUE:
                str += "unique";
                break;
            default:
                str += "unknown";
        }
        return str;
    }

    /** Get string of unit descriptor. */
    public String getDscString() {
        String str;
        switch (this.getDsc()) {
            default:
                str = "unknown";
                break;
        }
        return str;
    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }
}
