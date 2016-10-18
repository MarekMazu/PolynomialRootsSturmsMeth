
package polynomial_roots_sturms_method;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Marek
 */

public class PolynomialRootsSturmsMethod {

    protected BigDecimal precision = new BigDecimal("0.1");
    protected BigDecimal precision2 = new BigDecimal("0.00001");
    protected BigDecimal[] roots;
    protected ShowResultsController showResultController;
    
    protected void main(BigDecimal[] polynomial) {
    
        int degree = degree(polynomial);
        if (degree <= 0) zeroDegreePolynomial(degree, polynomial);
        else  {    // if degree of polynomial is higher than zero then beginning Sturm procedure
        
            precision2(polynomial);
            showText("Create Sturm sequence...");
            BigDecimal[][] sturm = CreateSturmSequence(polynomial);
            showText("Sturm sequence ready. \n");
            roots = lookingForRoots(sturm, polynomial);
            showRoots(roots);
        }
    }

    
    
    protected void zeroDegreePolynomial(int degree, BigDecimal[] polynomial) {
        
        if(degree < 0) showText("Sorry. Empty polynomial");
        else {
            if (polynomial[0].compareTo(BigDecimal.ZERO) == 0) showText("Every real number is the root.");
            else showText("This polynomial don't have any roots.");
        }
    }

    
    
    protected BigDecimal[] lookingForRoots(BigDecimal[][] sturm, BigDecimal[] polynomial) {
        
        BigDecimal limit = intervalLimit(sturm[0]); //sturm zero has this same roots as polynomial
        String limitToShow =  limit.setScale(0, RoundingMode.CEILING).toString();
        showText("All roots (if any) are inside interval: (-" + limitToShow + ", " + limitToShow + ") \n");
        BigDecimal a = limit.negate(); //down interval end
        BigDecimal b = limit;  //up interval end
        showText("Looking for roots...");
        int sturmLength = sturm[sturm.length-1][0].intValue();
        int numberOfSignChanges_a = howManySignChanges(sturm, sturmLength, a);
        int numberOfSignChanges_b = howManySignChanges(sturm, sturmLength, b);
        int numberOfRoots = numberOfSignChanges_a - numberOfSignChanges_b;
        showText("Number of roots: " + numberOfRoots);
        roots = new BigDecimal[numberOfRoots];
        
        if (numberOfRoots > 0) {
            
            BigDecimal temp_limit = b;
            int rootNumber = -1;
            
            do {
               
                if (numberOfRoots > 0) {
                    
                    BigDecimal avarageAB = (a.add(b)).divide(BigDecimal.valueOf(2));
                    
                    if ((b.subtract(a)).compareTo(precision) > 0 || polynomialValue(polynomial, avarageAB).abs().compareTo(precision) > 0){
                        
                        temp_limit = b;
                        b = avarageAB;
                        
                    } else {
                        
                        rootNumber++;
                        roots[rootNumber] = avarageAB.setScale(scale(polynomial, avarageAB), RoundingMode.HALF_UP);
                        a = b;
                        b = limit;
                        temp_limit = limit;
                    }
                    
                } else {
                    
                    a = b;
                    b = temp_limit;
                }
                

                //Sturm theorem don't work if p(a) or p(b) is zero, so p(a) and p(b) can't be zero
                
                if (polynomialValue(sturm[0], a).compareTo(BigDecimal.ZERO) == 0) {
                    
                    rootNumber++;
                    roots[rootNumber] = a.setScale(scale(polynomial, a), RoundingMode.HALF_DOWN);
                    a = a.add(precision);
                    b = limit;
                    temp_limit = limit;
                }
                
                if (polynomialValue(sturm[0], b).compareTo(BigDecimal.ZERO) == 0) {
                    
                    b = (a.add(b)).divide(BigDecimal.valueOf(2));
                }
                
                if (a.compareTo(limit) < 0 ){
                    
                    numberOfSignChanges_a = howManySignChanges(sturm, sturmLength, a);
                    numberOfSignChanges_b = howManySignChanges(sturm, sturmLength, b);
                    numberOfRoots = numberOfSignChanges_a - numberOfSignChanges_b;
                }
                
            } while (a.compareTo(limit) < 0 );
        }
        
        return roots;
    }

    
    
    protected BigDecimal[][] CreateSturmSequence(BigDecimal[] polynomial) {
        
        BigDecimal[][] sturm = new BigDecimal [polynomial.length+2][polynomial.length]; //array for Sturm's sequence
        BigDecimal[] derivative = derivative(polynomial); 
        BigDecimal[] gcd = greaterCommonDivisor(polynomial, derivative); 
        
        //sturm[0] - divide polynomial by greaterCommonDivisor of him and his derivative. This way get rid of additional root's multiplicities
        sturm[0] = divisionPolynomials(polynomial, gcd)[0];
        sturm[1] = derivative(sturm[0]);
        int i = 0;
        boolean flag;
        
        do { //create others elements of Sturm's sequence
            
            BigDecimal[] temp = divisionPolynomials(sturm[i], sturm[i+1])[1];
            for(int k = 0; k < temp.length; k++) temp[k] = temp[k].negate();
            sturm[i+2] = temp;
            i++;
            flag = false;
            
            //is last sturm non zero polynomial?
            for(BigDecimal coeff : temp) {if((coeff.abs()).compareTo(precision2) > 0 ) {flag = true; break;}}
            
        } while (flag == true); //continue while last sturm is not zero
        
        //after loop we have ready Sturm sequence
        sturm[sturm.length-1][0] = new BigDecimal(i); //on last position write sturm length
        return sturm;
    }
    
    
    
    protected BigDecimal[] greaterCommonDivisor(BigDecimal[] arr1, BigDecimal[] arr2){
        
        if (degree(arr2) > degree(arr1)) { //change place of polynomials if second is lower than first
            
            BigDecimal[] temp = arr1;
            arr1 = arr2;
            arr2 = temp;
        }
                
        BigDecimal[] gcd;
        BigDecimal[] h, f, g; //temp polynomials works in loop
        f = arr1;
        g = arr2;
        boolean flag;
        
        do {
        
           h = divisionPolynomials(f, g)[1]; //[1] - remainder of division f by g
           f = g;
           g = h;
           
           //is reminder zero polynomial? Approximation due to inaccuracies.
           flag = false;
           for(BigDecimal coeff : g) { if ((coeff.abs()).compareTo(precision2) > 0) {flag = true; break;}}
                
        } while(flag == true);
        
        gcd = f; //if reminder is zero, last f is greaterCommonDivisor
        
        //dividing all coeff. by higher pow coeff
        int gcdDegree = degree(gcd);
        for(int i = 0; i <= gcdDegree; i++) gcd[i] = gcd[i].divide(gcd[gcdDegree], 100, RoundingMode.HALF_UP);
        return gcd;
    }
    
    

    //Dividing polyomials. Return 2-dim array: [0] result, [1] reminder.
    
    protected BigDecimal[][] divisionPolynomials(BigDecimal[] arr1, BigDecimal[] arr2){
        
        int degree1 = degree(arr1);
        int degree2 = degree(arr2);
        if (degree2 < 0 || (degree2 == 0 && arr2[0].abs().compareTo(precision2) < 0))  {showText("Sorry, occurred division by zero polynomial"); return new BigDecimal[0][0];}
        if (degree1 < degree2) {showText("Sorry, an attempt to divide a polynomial of degree lower by the higher."); return new BigDecimal[0][0];}
          else { //dividing only if degrees are OK
        
            BigDecimal[][] result0_reminder1 = new BigDecimal[2][degree1 + 1]; //array: [0] result, [1] reminder
            initializeArr(result0_reminder1[0]);
            initializeArr(result0_reminder1[1]);
            
            //temp arrays
            BigDecimal[] temp1 = arr1;
            BigDecimal[] temp2 = arr2;
            BigDecimal[] temp3 = new BigDecimal[arr1.length]; initializeArr(temp3);
            
            //temp polynomials degrees
            int i, j, r, s;
            
            do {
                
                i = degree(temp1);  
                j = degree(temp2);  
                r =  i-j; //degree of result
                result0_reminder1[0][r] = temp1[i].divide(temp2[j], 100, RoundingMode.HALF_UP); //first coeff of result 
                for (int k = j; k >= 0; k--) { //temp3 = temp1 - result*temp2 
                    
                    temp3[r+k] = temp1[r+k].subtract(result0_reminder1[0][r].multiply(temp2[k]));
                    temp3[i] = BigDecimal.ZERO; //first of temp3 allways must be zero
                    if ((temp3[r+k].abs()).compareTo(precision2) < 0 ) temp3[r+k] = BigDecimal.ZERO; //due innacuracy, very small number change to zero
                }
                
                if (r>0) { //others coefficients (continue subtracting, but zeros)  
                    
                    for (int k = r-1; k>=0; k--) temp3[k] = temp1[k];
                }
                
                temp1 = temp3; //now temp3 will be dividing by temp2
                s = degree(temp3); 
                    
            } while((s >= j) && ((s != 0) || (temp3[0].compareTo(BigDecimal.ZERO) != 0))); //stay in loop if temp3 degree is equal or higher of temp2, and temp3 is not zero.

            //reminder is last temp3
            result0_reminder1[1] = temp3;
            return result0_reminder1;

        }
    }
    
    
    
    //Size of interval that are all roots inside.
    //from one of Couchy's theorem's
    
    protected BigDecimal intervalLimit(BigDecimal[] arr){
        
        int s = degree(arr);

        //max coefficient from 0 to s-1
        BigDecimal max = arr[0].abs();
        for (int i = 1; i < s; i++) max = (arr[i].abs()).max(max);
        
        BigDecimal intervalLimit = (max.divide(arr[s].abs(), 100, RoundingMode.HALF_UP)).add(BigDecimal.ONE);
        return intervalLimit;
    }
    
    
    
    protected BigDecimal polynomialValue (BigDecimal[] arr, BigDecimal x){
        
        int s = degree(arr);
        BigDecimal value = arr[s];
        for(int i = s-1; i >= 0; i--) value = (value.multiply(x)).add(arr[i]);
        return value;
    }
    
    
    protected void precision2(BigDecimal[] polynomial) {
        
        BigDecimal coeffSum = BigDecimal.ONE;
        for(BigDecimal coeff : polynomial) coeffSum = coeffSum.add(coeff.abs());
        precision2 = BigDecimal.ONE.divide(coeffSum, 50, RoundingMode.HALF_DOWN);
    }
    
    
    
    protected int howManySignChanges(BigDecimal[][] sturm, int sturmLength, BigDecimal x){
        
        int signChanges = 0;
        int sign1, sign2;
        sign1 = polynomialValue(sturm[0], x).signum();
        for(int i = 1; i <= sturmLength; i++) {
            
            sign2 = (polynomialValue(sturm[i], x)).signum();
            if (sign2 == 0) sign2 = sign1; //zero don't change sign
            if (sign1 != sign2) signChanges++;
            sign1 = sign2;
        }
        return signChanges;
    }
    
    
    
    protected void initializeArr(BigDecimal[] arr){
        
        for(int i = 0; i < arr.length; i++) arr[i] = BigDecimal.ZERO; 
    }
    
    
    
    protected static int degree(BigDecimal[] arr){
        
        int degree = 0;
        if (arr.length == 0) degree = -1; //if array is empty then degree -1 for differentiate zero array.
         
        for (int i = arr.length-1; i >= 0; i--) {
            
            if(arr[i].compareTo(BigDecimal.ZERO) != 0) {
                degree = i;
                break;
            }
        }
        return degree;    
    }
    
    
    //assume arr represent at least 1 degree polynomial, so arr.length is at least 2
    
    protected BigDecimal[] derivative(BigDecimal[] arr){
        
        BigDecimal[] derivative = new BigDecimal[arr.length-1];
        for (int i = 0; i < arr.length-1; i++){
            derivative[i] = arr[i+1].multiply(new BigDecimal(Integer.toString(i+1)));
        }
        return derivative;
    }
    
    
    // Mazur's Scale
    protected int scale(BigDecimal[] polynomial, BigDecimal x){
        
        int mazurScale = 0;
        BigDecimal absDerivativeValueX = polynomialValue(derivative(polynomial), x).abs();
        if (absDerivativeValueX.compareTo(precision) > 0){
            mazurScale = (int) (Math.log10(absDerivativeValueX.doubleValue()) - Math.log10(precision.doubleValue()));
        }
        return mazurScale;
    }
    
    
    
    protected void showRoots(BigDecimal[] roots){
        
        showResultController.setRoots(roots);
        showResultController.showRoots();
    }
    
    
    protected void showText(String text) {
        
        showResultController.setText(text);
    }
    

    protected void setShowResultController(ShowResultsController showResultController) {
        
        this.showResultController = showResultController;
    }

    
    protected void setPrecision(BigDecimal precision) {
        
        this.precision = precision;
    }
    
    
}
