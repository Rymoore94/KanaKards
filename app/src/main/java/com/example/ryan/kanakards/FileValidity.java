package com.example.ryan.kanakards;

import java.util.Vector;

class FileValidity {

    private Vector<String> contents;
    private String fileName;

    FileValidity(Vector<String> contents, String fileName) {
        this.contents = contents;
        this.fileName = fileName;
    }

    private boolean isEven() {
        return (contents.size() % 2 == 0);
    }

    private boolean legalLength() {
        for (int x = 0; x < contents.size(); x++) {
            if (x % 2 == 0) {
                if (contents.get(x).length() > 3)
                    return false;
            } else {
                if (contents.get(x).length() > 5)
                    return false;
            }
        }
        return true;
    }

    private boolean legalExtension() {
        int dotLoc;
        String extension;
        dotLoc = fileName.indexOf(".");
        extension = fileName.substring(dotLoc);

        return (extension.equals(".kk"));
    }

    boolean checkValid() {
        return (isEven() && legalLength() && legalExtension());
    }
}
