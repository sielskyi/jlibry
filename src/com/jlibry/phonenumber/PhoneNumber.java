/** \file PhoneNumber.java
* 	A phone number class.
*
*   \section    sect1   Descriptions
*   \section    sect99   Version log
*   - 01.01: 2016/11/24	Initial version. Author: sielskyi (SLL)
*/

package com.jlibry.phonenumber;

/** \class PhoneNumber 
	A phone number main class.
*/
public class PhoneNumber{
    
	private String		value;	// A phone number string
	
	/** \memberof PhoneNumber
		Constructor.
	*/
    public PhoneNumber(){
        value = "";
    }

	/** \memberof setValue
		Set a Phone number string.
		\param 	str	- phone number string to set.
		\return	void.
	*/
    public void setValue(String str){
        int i;
        char ch;

        value = "";
        i = 0;
        while(i < str.length()){
            ch = str.charAt(i);
            if(i == 0 && ch == '+'){
                i++;
            }else
            if(ch < '0' || ch > '9'){
                String str1;
                str1 = str.substring(i, (i + 1));
                str = str.replace(str1, "");
            }else {
                i++;
            }
        }
        value = str;
    }

	/** \memberof getValue
		Get a Phone number string.
		\param 	void.
		\return	String of phone number.
	*/
    public String getValue(){
        return value;
    }
}