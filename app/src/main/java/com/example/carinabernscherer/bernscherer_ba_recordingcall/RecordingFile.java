package com.example.carinabernscherer.bernscherer_ba_recordingcall;

/**
 * Created by carinabernscherer on 09.01.16.
 *
 */
public class RecordingFile {

    public String name;
    public String duration;
    public  String type;
    public String path;


    /**
     * Constructor
     * @param name
     * @param duration
     * @param type
     * @param path
     */
    public RecordingFile(String name, String duration, String type,String path) {
        this.name = name;
        this.duration = duration;
        this.type = type;
        this.path = path;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
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
