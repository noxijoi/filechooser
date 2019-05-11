package filechooser.viewmode;

import filechooser.ViewMode;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;

public class DirViewMode implements ViewMode {

    Group dirViewGroup;
    Table table;

    public DirViewMode(Composite composite){
        dirViewGroup = new Group(composite, SWT.NONE);
        table = new Table(dirViewGroup, SWT.NONE);
    }

    public void changeDirectory(String path) {

    }

    public Group getGroup() {
        return dirViewGroup;
    }
}
