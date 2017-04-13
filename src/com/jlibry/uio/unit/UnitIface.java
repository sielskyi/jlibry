/** \file IfaceUnit.java
 * 	IfaceUnit class.
 *
 *   \section    sect1   Descriptions
 *   \section    sect99   Version log
 *   - 01.01: 2016/12/21	Initial version. Author: sielskyi (SLL)
 */

package com.sielskyi.integralconfig;

/** \class Iface
    A IfaceUnit class.
 */
public class IfaceUnit extends Unit{

    /* Public constants */
    public static final int DSC_IFACE_UNKNOWN = 0;

    /* Private constants */
    //private static final int PRIVATE = 0;

    /* Variables */
    private int mode;

    /** \memberof IfaceUnit
        Constructor.
     */
    public IfaceUnit() {
        this(TYPE_IFACE_UNKNOWN, DSC_UNKNOWN);
    }

    public IfaceUnit(int type, int dsc) {
        super.setType(type);
        super.setDsc(dsc);
    }
    /** \memberof clear
     Clear code to none value.
     */
    @Override
    public void clear() {
        super.clear();
        mode = 0;
    }

    @Override
    public void setType(int type) {
        if (type >= TYPE_IFACE_UNKNOWN
                && type <= (TYPE_IFACE_UNKNOWN + 63))
        {
            super.setType(type);
        } else {
            super.setType(TYPE_IFACE_UNKNOWN);
        }
    }

    public String getTypeString() {
        String str;

        str = "interface ";
        switch (this.getType())
        {
            case TYPE_IFACE_KEYBOARD:
                str += "keyboard";
                break;
            default:
                str += "unknown";
        }
        return str;
    }

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
