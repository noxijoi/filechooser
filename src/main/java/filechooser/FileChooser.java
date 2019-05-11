package filechooser;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.io.File;


public class FileChooser {
    private Shell shell;
    private final String SAVE_TITLE = "Save file";
    private final String OPEN_TITLE = "Open file";

    private String[] filterNames = new String[0];
    private String[] filterExtensions = new String[0];
    private String[] filesNames = new String[0];


    private String currentDirectoryPath;
    private String homeDirectoryPath = "C:\\";

    private Combo diskCombo;
    private Combo extensionsCombo;
    private FileTree fileTree;
    private Button toHomeButton;
    private Text fileName;
    private Button submitButton;
    private FileArea mainArea;


    public FileChooser(Shell parent, int mode) {
        currentDirectoryPath = homeDirectoryPath;
        shell = new Shell(parent);
        switch (mode) {
            case SWT.SAVE:
                shell.setText(OPEN_TITLE);
                break;
            case SWT.OPEN:
                shell.setText(OPEN_TITLE);
        }
        shell.setRedraw(true);
        shell.setSize(1500, 700);

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
        fileTree = new FileTree(shell);
        fileTree.setLayoutData(treeGridData);


        GridData mainGroupGridData = new GridData();
        mainGroupGridData.horizontalSpan = 3;
        mainGroupGridData.widthHint = 1000;
        mainGroupGridData.heightHint = 500;
        mainGroupGridData.grabExcessHorizontalSpace = true;
        mainGroupGridData.grabExcessVerticalSpace = true;

        mainArea = new FileArea(shell, currentDirectoryPath);
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

    public String[] getFilterExtensions() {
        return filterExtensions;
    }

    public void setFilterExtensions(String[] filterExtensions) {
        this.filterExtensions = filterExtensions;
    }

    public String[] getFilesNames() {
        return filesNames;
    }

    public void setFilesNames(String[] filesNames) {
        this.filesNames = filesNames;
    }

    public String getCurrentDirectoryPath() {
        return currentDirectoryPath;
    }

    public void setCurrentDirectoryPath(String currentDirectoryPath) {
        this.currentDirectoryPath = currentDirectoryPath;
    }

    public String getHomeDirectoryPath() {
        return homeDirectoryPath;
    }

    public void setHomeDirectoryPath(String homeDirectoryPath) {
        this.homeDirectoryPath = homeDirectoryPath;
    }
}
