package com.example.carinabernscherer.bernscherer_ba_recordingcall;

/**
 * Created by carinabernscherer on 09.01.16.
 *
 */
public class RecordingFile {

    public String name;
    public String size;
    public  String type;
    public String path;


    /**
     * Constructor
     * @param name
     * @param size
     * @param type
     * @param path
     */
    public RecordingFile(String name, String size, String type,String path) {
        this.name = name;
        this.size = size;
        this.type = type;
        this.path = path;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
