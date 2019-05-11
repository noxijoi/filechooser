package filechooser.viewmode;

import filechooser.ViewMode;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;

public class TableFileView implements ViewMode {

    Composite parent;
    Group group;
    Table table;


    public TableFileView(Composite composite){
        this.parent = composite;
        group = new Group(composite, SWT.NONE);
        table = new Table(group, SWT.NONE);
    }

    public void changeDirectory(String path) {

    }

    public Group getGroup() {
        return group;
    }

    public Control getControl() {
        return group;

    }
}
