package filechooser;

import filechooser.viewmode.DirFileView;
import filechooser.viewmode.ListFileView;
import filechooser.viewmode.TableFileView;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TabFolder;

public class FileArea {
    TabFolder tabFolder;
    Controller controller;
    String currentPath;
    FileArea(Shell shell, String currentPath){
        this.currentPath = currentPath;
        //
        tabFolder = new TabFolder(shell, SWT.BORDER);
        TabItem tabViewItem = new TabItem(tabFolder, SWT.NONE);
        tabViewItem.setText("Таблица");
        TableFileView tableViewMode = new TableFileView(tabFolder);
        tabViewItem.setControl(tableViewMode.getControl());

        TabItem listViewItem = new TabItem(tabFolder, SWT.NONE);
        listViewItem.setText("Список");
        ListFileView listViewMode = new ListFileView(tabFolder);
        listViewItem.setControl(listViewMode.getGroup());

        TabItem dirViewItem = new TabItem(tabFolder, SWT.NONE);
        dirViewItem.setText("Папки");
        DirFileView dirViewMode = new DirFileView(tabFolder, currentPath);
        dirViewItem.setControl(dirViewMode.getGroup());
    }

    public void setLayoutData(GridData gridData) {
        tabFolder.setLayoutData(gridData);
    }
}
