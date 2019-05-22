package filechooser;

import filechooser.viewmode.TextView;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.*;

import java.io.File;
import java.io.IOException;


public class FileChooser {
    private Shell parent;
    private Shell shell;
    private final String SAVE_TITLE = "Save file";
    private final String OPEN_TITLE = "Open file";
    private int mode;

    private String[] filterNames = {"Все файлы"};
    private String[] filterExtensions = {""};


    private String currentDirectoryPath;
    private String homeDirectoryPath = "C:\\";


    private Combo diskCombo;
    private Combo extensionsCombo;
    private FileTree fileTree;
    private Button toHomeButton;
    private Text fileName;
    private Button submitButton;
    private FileArea mainArea;
    private Controller controller;


    public FileChooser(Shell parent, int mode) {
        this.parent = parent;
        currentDirectoryPath = homeDirectoryPath;
    }

    private void initSubmitButton() {
        submitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent e) {
                String name = fileName.getText();
                String dir = controller.getCurrentDir();
                if(name.isEmpty()){
                    MessageBox msg = new MessageBox(shell, SWT.ICON_ERROR);
                    msg.setMessage("Enter file name");
                    msg.open();
                    return;
                }
                String ext = (String) extensionsCombo.getData(extensionsCombo.getItem(extensionsCombo.getSelectionIndex()));
                String fullPath = dir + name + ext;
                if(mode == SWT.OPEN && !(new File(fullPath).exists())){
                    MessageBox msg = new MessageBox(shell, SWT.ICON_ERROR);
                    msg.setMessage("File doesn't exist");
                    msg.open();
                    return;
                }
                File file = new File(fullPath);
                if(!file.exists()){
                    try {
                        file.createNewFile();
                    } catch (IOException e1) {
                        MessageBox msg = new MessageBox(shell, SWT.ICON_ERROR);
                        msg.setMessage("Error until file creating");
                        msg.open();
                    }
                }
                controller.selectFile(fullPath);
            }
        });
    }

    private void initExtensionsCombo() {
        extensionsCombo.removeAll();
        for (int i = 0; i < filterExtensions.length; i++) {
            extensionsCombo.add(filterNames[i]);
            extensionsCombo.setData(filterNames[i], filterExtensions[i]);
        }
        extensionsCombo.select(0);
        extensionsCombo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent e) {
                extensionsCombo.forceFocus();
            }
        });
        extensionsCombo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
               int index = extensionsCombo.getSelectionIndex();
               String ext = filterExtensions[index];
               controller.changeExtension(ext);
            }
        });
    }

    private void initDiskCombo() {
        File[] logicDisks = File.listRoots();
        for (int i = 0; i < logicDisks.length; i++) {
            diskCombo.add(logicDisks[i].getPath());
        }
        diskCombo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent e) {
                diskCombo.forceFocus();
            }
        });
        diskCombo.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                int index = diskCombo.getSelectionIndex();
                String path = diskCombo.getItem(index);
                controller.updatePath(path);
            }
        });
        diskCombo.pack();
    }

    public String open() {
        shell = new Shell(parent);

        controller = new Controller(currentDirectoryPath, filterExtensions[0], shell);
        this.mode = mode;
        switch (mode) {
            case SWT.SAVE:
                shell.setText(SAVE_TITLE);
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

        diskCombo = new Combo(shell,SWT.DROP_DOWN | SWT.READ_ONLY);
        initDiskCombo();

        GridData openDirTextGridData = new GridData();
        openDirTextGridData.horizontalSpan = 3;
        openDirTextGridData.widthHint = 1000;
        TextView openDir = new TextView(shell, controller);
        openDir.setLayoutData(openDirTextGridData);
        controller.addView(openDir);

        GridData treeGridData = new GridData();
        treeGridData.horizontalAlignment = GridData.FILL;
        treeGridData.heightHint = 500;
        treeGridData.widthHint = 300;
        treeGridData.grabExcessVerticalSpace = true;
        treeGridData.grabExcessHorizontalSpace = true;
        fileTree = new FileTree(shell, controller);
        controller.addView(fileTree);
        fileTree.setLayoutData(treeGridData);


        GridData mainGroupGridData = new GridData();
        mainGroupGridData.horizontalSpan = 3;
        mainGroupGridData.widthHint = 1000;
        mainGroupGridData.heightHint = 500;
        mainGroupGridData.grabExcessHorizontalSpace = true;
        mainGroupGridData.grabExcessVerticalSpace = true;

        mainArea = new FileArea(shell, controller);
        mainArea.setLayoutData(mainGroupGridData);

        toHomeButton = new Button(shell, SWT.PUSH);
        toHomeButton.setText("to home directory");
        toHomeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDown(MouseEvent e) {
                controller.updatePath(homeDirectoryPath);
            }
        });

        fileName = new Text(shell, SWT.BORDER);
        fileName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseDoubleClick(MouseEvent e) {
                fileName.forceFocus();
            }
        });
        GridData fileNameGridData = new GridData();
        fileNameGridData.horizontalSpan = 2;
        fileNameGridData.widthHint = 600;
        fileName.setLayoutData(fileNameGridData);

        extensionsCombo = new Combo(shell, SWT.DROP_DOWN | SWT.READ_ONLY);
        initExtensionsCombo();

        GridData emptyLabel = new GridData();
        emptyLabel.horizontalSpan = 3;
        new Label(shell, SWT.NONE).setLayoutData(emptyLabel);

        submitButton = new Button(shell, SWT.PUSH);
        submitButton.setText("Выбрать");
        initSubmitButton();
        shell.pack();
        while (!shell.isDisposed()){
            shell.pack();
            shell.open();

            Display display = Display.getCurrent();
            if(!display.readAndDispatch())
                display.sleep();
        }
        String path = controller.getSelectedFilePath();
        return path;
    }

    public String[] getFilterExtensions() {
        return filterExtensions;
    }

    public void setFilterExtensions(String[] filterExtensions) {
        this.filterExtensions = filterExtensions;

    }
    public  void  setFilterNames(String[] filterNames){
        this.filterNames = filterNames;

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
