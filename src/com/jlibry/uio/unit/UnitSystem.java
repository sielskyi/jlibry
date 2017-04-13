/**
 * 	SystemUnit class.
 *
 *   @author    sielskyi (SLL)
 *   @version   01.01 2016/12/21 Initial version.
 */

package com.sielskyi.integralconfig;

public class SystemUnit extends Unit
{

/* Public constants */

    public static final int PUBLIC = 0;

/* Private constants */

    private static final int PRIVATE = 0;

/* Fields */

/* Methods */

    public SystemUnit() {
        this(TYPE_UNKNOWN, DSC_UNKNOWN);
    }

    public SystemUnit(int type, int dsc) {
        this.setType(type);
        this.setDsc(dsc);
    }

    /** Clear code to none value. */
    @Override
    public void clear() {
        super.clear();

    }

    /** Get string of unit type. */
    public String getTypeString() {
        return "system";
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

}
