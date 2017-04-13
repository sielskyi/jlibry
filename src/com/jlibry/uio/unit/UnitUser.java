/**
 * 	UserUnit class.
 *
 *   @author    sielskyi (SLL)
 *   @version   01.01 2016/12/21 Initial version.
 */

package com.sielskyi.integralconfig;

public class UserUnit extends Unit{

    /* Public constants */
    public static final int PUBLIC = 0;

    /* Private constants */
    private static final int PRIVATE = 0;

    /* Variables */
    private String name;

    public UserUnit() {

    }

    public UserUnit(int dsc) {
        super.setDsc(dsc);
    }

    /** Clear code to none value. */
    @Override
    public void clear() {
        super.clear();
        super.setType(TYPE_USER);
        name = "";
    }

    /** Get string of unit type. */
    public String getTypeString() {
        return "user";
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
