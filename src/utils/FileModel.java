package utils;

import javax.swing.*;

/**
 * Created by ht on 2016/2/24.
 */
public class FileModel extends AbstractListModel {

    private String[] files;

    public FileModel(String[] files) {
        this.files = files;
    }

    @Override
    public Object getElementAt(int index) {
        //排除不是密码文件的
        return (index + 1) + "." + files[index++];
    }

    @Override
    public int getSize() {
        return files.length;
    }
}
