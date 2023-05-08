package cn.zhaooo.jvm.classpath.impl;

import cn.zhaooo.jvm.classpath.Entry;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaooo3
 * @description: Entry or path mixed in different form
 * @date 4/21/23 1:57 PM
 */
public class CompositeEntry implements Entry {

    private final List<Entry> entryList = new ArrayList<>();

    public CompositeEntry(String pathList) {
        // separator = "/" separatorChar = '/' (Unix)
        // separator = "\" separatorChar = '\' (Windows)
        // pathSeparator = ":" pathSeparatorChar = ':' (Unix)
        // pathSeparator = ";" pathSeparatorChar = ';' (Windows)
        String[] paths = pathList.split(File.pathSeparator);
        // generate Entries from path
        for (String path : paths) {
            entryList.add(Entry.create(path));
        }
    }

    @Override
    public byte[] readClass(String className) throws IOException {
        for (Entry entry : entryList) {
            try {
                // If found, return the class file
                return entry.readClass(className);
            } catch (Exception ignored) {
                // if not found then do nothing
                // ignored
            }
        }
        throw new IOException("class not found " + className);
    }

    // Method to generate a string representation of the CompositeEntry object
    /**
     * 获取当前文件夹下所有entry
     */
    @Override
    public String toString() {
        String[] strs = new String[entryList.size()];
        for (int i = 0; i < entryList.size(); i++) {
            strs[i] = entryList.get(i).toString();
        }
        return String.join(File.pathSeparator, strs);
    }

}
