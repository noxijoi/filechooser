package filechooser;

import filechooser.FileChooser;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;

public class OpenFileChooser {
    public static void main(String[] args) {
        Display display = new Display();
        Shell shell = new Shell(display);

        FileChooser fc  = new FileChooser(shell, SWT.OPEN);
        fc.open();
        while (!shell.isDisposed()){
            if(!display.readAndDispatch())
                display.sleep();
        }
        display.dispose();
    }

}

