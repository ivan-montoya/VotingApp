/**
 * 
 */
package gui;

import java.awt.EventQueue;
import java.sql.Connection;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * @author Ivan Montoya
 *
 */
public class Driver {
    /**
     * Overrides default public constructor.
     */
    private Driver() {
        throw new IllegalStateException();
    }

    /**
     * Main driver method of the program.
     * 
     * @param theArgs Command line arguments.
     */
    public static void main(final String[] theArgs) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
            	
            	try {
            	    for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            	        if ("Nimbus".equals(info.getName())) {
            	            UIManager.setLookAndFeel(info.getClassName());
            	            break;
            	        }
            	    }
            	} catch (UnsupportedLookAndFeelException e) {
            	    // handle exception
            	} catch (ClassNotFoundException e) {
            	    // handle exception
            	} catch (InstantiationException e) {
            	    // handle exception
            	} catch (IllegalAccessException e) {
            	    // handle exception
            	}
                new WelcomeScreen();
                
            }
        });
    }
}
