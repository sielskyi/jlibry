/** \file InputUnit.java
 * 	InputUnit class.
 *
 *   \section    sect1   Descriptions
 *   \section    sect99   Version log
 *   - 01.01: 2016/12/21	Initial version. Author: sielskyi (SLL)
 */

package com.sielskyi.integralconfig;

/** \class Input
    A InputUNit main class.
 */
public class InputUnit extends Unit{

    /* Public constants */
    public static final int DSC_INPUT_GUARD = 0;

    /* Private constants */
    //private static final int PRIVATE = 0;

    /* Variables */
    private int mode;


    /** \memberof InputUnit
        Constructor.
     */
    public InputUnit() {
        this(TYPE_INPUT_UNKNOWN, DSC_UNKNOWN);
    }

    public InputUnit(int type, int dsc) {
        super.setType(type);
        super.setDsc(dsc);
    }

    /** Clear code to none value. */
    @Override
    public void clear() {
        super.clear();
        mode = 0;
    }

    /** Clear code to none value. */
    @Override
    public void setType(int type) {
        if (type >= TYPE_INPUT_UNKNOWN
                && type <= (TYPE_INPUT_UNKNOWN + 63))
        {
            super.setType(type);
        } else {
            super.setType(TYPE_INPUT_UNKNOWN);
        }
    }

    /** Get string of unit type. */
    public String getTypeString() {
        String str;

        str = "input ";
        switch (this.getType())
        {
            case TYPE_INPUT_UNKNOWN:
                str += "unknown";
                break;
            case TYPE_INPUT_STATE2:
                str += "state 2";
                break;
            case TYPE_INPUT_STATE3:
                str += "state 3";
                break;
            case TYPE_INPUT_STATE5:
                str += "state 5";
                break;
            case TYPE_INPUT_PERCENT:
                str += "percent";
                break;
            case TYPE_INPUT_VALUE:
                str += "value";
                break;
            case TYPE_INPUT_UNIQUE:
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
            case DSC_INPUT_GUARD:
                str = "guard";
                break;
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
