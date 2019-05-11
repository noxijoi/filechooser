package filechooser.viewmode;

import filechooser.ViewMode;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.*;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DirFileView implements ViewMode {

    Image dirImage;
    final String dirImageName = "dir.png";
    Image fileImage;
    final String fileImageName = "file.png";
    final int DEFAULT_COL_NUM = 4;

    Group group;
    Table table;
    String currentDir = "";

    public DirFileView(Composite composite, String dirPath) {
        group = new Group(composite, SWT.NONE);
        table = new Table(group, SWT.MULTI | SWT.FULL_SELECTION);
        for (int i = 0; i < DEFAULT_COL_NUM; i++) {
            TableColumn tableColumn = new TableColumn(table, SWT.NONE);
        }
        currentDir = dirPath;
        initImages();
        File dir = new File(currentDir);
        File[] files = dir.listFiles();
        List<File> fileList = Arrays.asList(files);
        Iterator<File> itr = fileList.iterator();

        while (itr.hasNext()) {
            TableItem item = new TableItem(table, SWT.NONE);
            for (int i = 0; i < DEFAULT_COL_NUM; i++) {
                if (itr.hasNext()) {
                    File file = itr.next();
                    String fileName = file.getName();
                    Image img;
                    if(file.isDirectory()){
                        img = dirImage;
                    } else {
                        img = fileImage;
                    }
                    item.setImage(i, img);
                    item.setText(i, fileName);
                }
            }

        }
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumn(i).pack();
        }
        table.setLinesVisible(true);
        table.setVisible(true);
        table.pack();
    }

    private void initImages() {
        dirImage = new Image(group.getDisplay(),
                this.getClass().getClassLoader().getResourceAsStream("img/" + dirImageName));
        fileImage = new Image(group.getDisplay(),
                this.getClass().getClassLoader().getResourceAsStream("img/" + fileImageName));
    }

    public void changeDirectory(String path) {

    }

    public Group getGroup() {
        return group;
    }
}
