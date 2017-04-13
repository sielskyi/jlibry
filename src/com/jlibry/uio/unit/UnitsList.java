/** \file UnitList.java
 * 	UnitList class.
 *
 *   \section    sect1   Descriptions
 *   \section    sect99   Version log
 *   - 01.01: 2016/12/21	Initial version. Author: sielskyi (SLL)
 */

package com.sielskyi.integralconfig;

import java.util.ArrayList;
import java.util.Comparator;

/** \class UnitList
    A UnitList main class.
 */

public class UnitList<E extends Unit> extends ArrayList<E>{

    /*public boolean create()
    {
        int num;

        num = 0;
        num = getFirstFreeNumber(num);

        return this.create(num);
    }

    public boolean create(int num)
    {
        boolean res;

        E element = new E();

        res = super.add(element);

        UnitListComparator<E> comp = new  UnitListComparator<E>();

        super.sort(comp);

        return res;
    }*/

    /*@Override
    public void add(int index, E element)
    {

    }*/

    private int getIndexByNumber(int num)
    {
        for(int i = 0; i < super.size(); i++)
        {
            if (super.get(i) != null
                 && super.get(i).getNumber() == num)
            {
                return i;
            }
        }
        return -1;
    }


    private int getFirstFreeNumber(int num)
    {
        int i;

        for(i = 0; i < super.size(); i++)
        {
            if (super.get(i) != null
                && super.get(i).getNumber() >= num)
            {
                if(super.get(i).getNumber() != num) {
                    break;
                }
                num++;
            }
        }
        return i;
    }

    private class UnitListComparator<T extends Unit> implements Comparator<T>
    {
        public int compare(T o1, T o2)
        {
            return (o2.getNumber() - o2.getNumber());
        }

        public boolean equals(Object obj)
        {
            return (this.getClass() == obj.getClass());
        }
    }
}
