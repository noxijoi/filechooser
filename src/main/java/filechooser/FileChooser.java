package filechooser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class FileChooser {
    private Shell shell;
    private final String SAVE_TITLE = "Save file";
    private final String OPEN_TITLE = "Open file";

    private String[] filterNames = new String[0];
    private String[] filterExtensions = new String[0];
    private String[] filesMNames = new String[0];


    private String currentDirectoryPath;
    private String homeDirectoryPath = "C:\\";

    private Combo diskCombo;
    private Combo extensionsCombo;
    private Tree directoryTree;
    private Button toHomeButton;
    private Text fileName;
    private Button submitButton;
    private Group mainArea;


    public FileChooser(Shell parent, int mode) {
        shell = new Shell(parent);
        switch (mode) {
            case SWT.SAVE:
                shell.setText(OPEN_TITLE);
                break;
            case SWT.OPEN:
                shell.setText(OPEN_TITLE);
        }
        shell.setRedraw(true);
        shell.setSize(1500, 500);

        GridLayout gridLayout = new GridLayout();
        gridLayout.numColumns = 4;
        gridLayout.makeColumnsEqualWidth = false;

        shell.setLayout(gridLayout);

        diskCombo = new Combo(shell, SWT.DROP_DOWN);
        initDiskCombo();

        GridData emptySpaceGridData = new GridData();
        emptySpaceGridData.horizontalSpan = 3;
        Text empty = new Text(shell, SWT.READ_ONLY);
        empty.setLayoutData(emptySpaceGridData);

        GridData treeGridData = new GridData();
        treeGridData.horizontalAlignment = GridData.FILL;
        treeGridData.heightHint = 500;
        treeGridData.grabExcessVerticalSpace = true;
        treeGridData.grabExcessHorizontalSpace = true;
        directoryTree = new Tree(shell, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
        directoryTree.setLayoutData(treeGridData);
        initDirectoryTree();

        GridData mainGroupGridData = new GridData();
        mainGroupGridData.horizontalSpan = 3;
        mainGroupGridData.widthHint = 1000;
        mainGroupGridData.heightHint = 500;
        mainGroupGridData.grabExcessHorizontalSpace = true;
        mainGroupGridData.grabExcessVerticalSpace = true;

        mainArea = new Group(shell, SWT.SHADOW_NONE);
        mainArea.setLayoutData(mainGroupGridData);

        toHomeButton = new Button(shell, SWT.PUSH);
        toHomeButton.setText("to home directory");

        fileName = new Text(shell, SWT.SINGLE | SWT.BORDER);
        GridData fileNameGridData = new GridData();
        fileNameGridData.horizontalSpan = 2;
        fileNameGridData.widthHint = 600;
        fileName.setLayoutData(fileNameGridData);

        extensionsCombo = new Combo(shell, SWT.DROP_DOWN);

        new Label(shell, SWT.NONE).setLayoutData(emptySpaceGridData);

        submitButton = new Button(shell, SWT.PUSH);
        submitButton.setText("Выбрать");
    }

    private void initDirectoryTree() {
        directoryTree.setRedraw(true);
        File[] logicDisks = File.listRoots();
        for (File logicDisk : logicDisks) {
            final TreeItem logicDiskItem = new TreeItem(directoryTree, SWT.NONE);
            logicDiskItem.setText(logicDisk.toString());
            logicDiskItem.setData(logicDisk.getAbsolutePath());
            TreeItem emptyItem = new TreeItem(logicDiskItem, SWT.NONE);
            logicDiskItem.addListener(SWT.Selection, new Listener() {
                public void handleEvent(Event event) {
                    changeDirectory((String) logicDiskItem.getData());
                }
            });

        }
        directoryTree.addListener(SWT.Expand, new Listener() {
            public void handleEvent(Event event) {
                TreeItem item = (TreeItem) event.item;
                if (item == null) {
                    return;
                }
                if (item.getItems().length == 1){
                    item.removeAll();
                }
                String path = (String) item.getData();
                File file = new File(path);
                File[] files = file.listFiles();
                if (files != null) {
                    for (File f : files) {
                        if (f.isDirectory()) {
                            TreeItem subDir = new TreeItem(item, SWT.NONE);
                            TreeItem subsub = new TreeItem(subDir, SWT.NONE);
                            subDir.setText(f.getName());
                            subDir.setData(f.getAbsolutePath());
                        }
                    }
                }
            }

        });
        directoryTree.addListener(SWT.Collapse, new Listener() {
            public void handleEvent(Event event) {
                TreeItem item = (TreeItem) event.item;
                if (item == null) {
                    return;
                }
                item.removeAll();
                TreeItem emptyItem = new TreeItem(item, SWT.NONE);
            }
        });
        directoryTree.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event event) {
                TreeItem item  = (TreeItem) event.item;
                String path  = (String) item.getData();
                changeDirectory(path);
            }
        });
    }

    private void checkSubDirectories(TreeItem directoryItem) {
        String parentDirectoryPath = (String) directoryItem.getData();
        File parentDir = new File(parentDirectoryPath);
        File[] files = parentDir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    final TreeItem subDir = new TreeItem(directoryItem, SWT.NONE);
                    subDir.setData(file.getAbsolutePath());
                    subDir.setText(file.getName());
                }
            }
        }
    }

    private void changeDirectory(String newPath) {
    }

    private void initDiskCombo() {
        File[] logicDisks = File.listRoots();
        for (int i = 0; i < logicDisks.length; i++) {
            diskCombo.add(logicDisks[i].getPath());
        }
    }

    public String open() {
        shell.open();
        return "";
    }
}
