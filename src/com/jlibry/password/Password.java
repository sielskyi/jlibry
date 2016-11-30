/** \file Password.java
* 	A password class.
*
*   \section    sect1   Descriptions
*   \section    sect99   Version log
*   - 01.01: 2016/11/24	Initial version. Author: sielskyi (SLL)
*/

package com.jlibry.password;

/** \class Password
	A password main class.
*/
public class Password{
    
	/** A None type */
    public final int    TYPE_NONE                   	= 0;
	/** Password with latin letters: A-Z, a-z */
    public final int    TYPE_BIT_LATIN_LETTERS       	= 1;
	/** Password with arabic digits: 0 - 9 */
    public final int    TYPE_BIT_ARABIC_DIGITS       	= 2;
	/** Password with basic printed symbols: !,./[]()... */
    public final int    TYPE_BIT_BASIC_PRINTED_SYMBOLS  = 4;
	/** Password is a HEX code */
    public final int    TYPE_BIT_HEX_CODE           	= 8;
	/** Default password type */
    public final int    TYPE_DEFAULT
			= TYPE_BIT_BASIC_PRINTED_SYMBOLS | TYPE_BIT_LATIN_LETTERS | TYPE_BIT_ARABIC_DIGITS;

	private String		value;	// A password string
    private int         type;	// A type of password

	/** \memberof Password
		Constructor.
	*/
    public Password(){
        initialize();
    }

	/** \memberof setValue
		Set a default type password string.
		\param 	str	- password string to set.
		\return	void.
	*/
    public void setValue(String str){
        setValue(str, TYPE_DEFAULT);
    }
	
	/** \memberof setValue
		Set a typed password string.
		\param 	str		- password string to set.
		\param	type 	- type of password.
		\return	void.
	*/
    public void setValue(String str, int type){
        char ch;
        int i;
        String str1;

        initialize();
        if((type & TYPE_BIT_HEX_CODE) != 0){
            str = str.toUpperCase();
            type = TYPE_BIT_HEX_CODE;
        }
        i = 0;
        while(i < str.length()){
            ch = str.charAt(i);
            if(((type & TYPE_BIT_HEX_CODE) != 0 && ((ch >= 'A' && ch <= 'F') || (ch >= '0' && ch <= '9')))
            || ((type & TYPE_BIT_LATIN_LETTERS) != 0 && ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')))
            || ((type & TYPE_BIT_ARABIC_DIGITS) != 0 && (ch >= '0' && ch <= '9'))
            || ((type & TYPE_BIT_BASIC_PRINTED_SYMBOLS) != 0
                && (ch >= ' ' && ch <= '~'
                    && !(ch >= 'A' && ch <= 'Z'
                    || ch >= 'a' && ch <= 'z'
                    || ch >= '0' && ch <= '9')))
            ){
                i++;
            }else{
                str1 = str.substring(i, (i + 1));
                str = str.replace(str1, "");
                continue;
            }
        }
        value = str;
        this.type = type;
    }
	
	/** \memberof getType
		Get a password type value.
		\param 	void.
		\return	A password type value.
	*/
    public int getType(){
        return type;
    }
	
	/** \memberof getValue
		Get a password string.
		\param 	void.
		\return	A string of password.
	*/
    public String getValue(){
        return value;
    }

	/* Initialize password value and type */
    private void initialize(){
        value = "";
        type = TYPE_NONE;
    }
}