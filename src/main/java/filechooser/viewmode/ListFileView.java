package filechooser.viewmode;

import filechooser.ViewMode;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;

public class ListFileView implements ViewMode {
    Group group;
    Table table;

    public ListFileView(Composite composite){
        group = new Group(composite, SWT.NONE);
        table = new Table(group, SWT.NONE);
    }

    public void changeDirectory(String path) {

    }

    public Group getGroup() {
        return group;
    }
}
