package filechooser.viewmode;

import filechooser.ViewMode;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;

public class ListViewMode implements ViewMode {
    Group listViewGroup;
    Table table;

    public ListViewMode(Composite composite){
        listViewGroup = new Group(composite, SWT.NONE);
        table = new Table(listViewGroup, SWT.NONE);
    }

    public void changeDirectory(String path) {

    }

    public Group getGroup() {
        return listViewGroup;
    }
}
