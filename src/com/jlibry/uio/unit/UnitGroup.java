/** \file GroupUnit.java
 * 	GroupUnit class.
 *
 *   \section    sect1   Descriptions
 *   \section    sect99   Version log
 *   - 01.01: 2016/12/21	Initial version. Author: sielskyi (SLL)
 */

package com.sielskyi.integralconfig;

/** \class GroupUnit
 A GroupUnit main class.
 */
public class GroupUnit<E extends Unit> extends Unit{

    /* Public constants */
    public static final int DSC_GROUP_UNKNOWN  = 0;

    /* Private constants */
    private static final int PRIVATE = 0;

    /* Fields */
    public UnitList<E>  units;

    /** \memberof GroupUnit
     Constructor.
     */
    public GroupUnit() {

    }

    public GroupUnit(int dsc) {
        super.setDsc(dsc);
    }

    /** \memberof clear
     Clear code to none value.
     */
    @Override
    public void clear() {
        super.clear();
        super.setType(TYPE_GROUP);
        units = new UnitList<E>();
    }

    /** Get string of unit type. */
    public String getTypeString() {
        return "group";
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
