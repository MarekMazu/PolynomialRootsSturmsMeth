
package polynomial_roots_sturms_method;

/**
 *
 * @author Marek
 */
public class XsupscriptsPow {
    
    public String XsupPow(int pow){
    
        String xSupPow;
        
        switch(pow){
            
            case 0 : xSupPow = ""; break;
            case 1 : xSupPow = " x"; break;
            case 2 : xSupPow = " x" + Character.toString((char)(0x00B2)); break;
            case 3 : xSupPow = " x" + Character.toString((char)(0x00B3)); break;
            case 10 : xSupPow = " x" + Character.toString((char)(0x00B9)) + Character.toString((char)(0x2070)); break;
            default : xSupPow = " x" + Character.toString((char)(0x2070 + pow)); break;
        }
    
        return xSupPow;
    }
}
