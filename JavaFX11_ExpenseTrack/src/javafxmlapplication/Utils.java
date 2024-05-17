/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

/**
 *
 * @author sovacu
 */
public class Utils {
    /**
     *  A password is considered valid if follows an accepted email syntax:
     *  name@domain.com
     * @param email String which contains the email to check  
     * @return  True if the email is valid. False otherwise.  
     */
    
    public static  Boolean checkEmail (String email)
    {   if(email == null){
          return false; 
        }
       // Regex to check valid email. 
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        // Compile the ReGex 
        Pattern pattern = Pattern.compile(regex);
        // Match ReGex with value to check
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
     /**
     * 
     * A password is considered valid if it is combination of 8 to 15 numbers or 
     * letters, without blank spaces.
     *
     * @param password String which contains the password to check  
     * @return  True if the password is valid. False otherwise.   
     */ 
    public static Boolean checkPassword(String password){     
  
        // If the password is empty , return false 
        if (password == null) { 
            return false; 
        } 
        // Regex to check valid password. 
        String regex =  "^[A-Za-z0-9]{8,15}$"; 
  
        // Compile the ReGex 
        Pattern pattern = Pattern.compile(regex); 
        // Match ReGex with value to check
        Matcher matcher = pattern.matcher(password); 
        return matcher.matches();
    
    }
    
    public static void circularCutout(ImageView imageView) {
        double radius = Math.min(imageView.getFitWidth(), imageView.getFitHeight()) / 2;
        Circle clip = new Circle(radius, radius, radius);
        imageView.setClip(clip);
    }
    
}
