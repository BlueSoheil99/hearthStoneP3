package edu.sharif.student.bluesoheil.ap98.hearthstone.util.Configuration;

import java.util.Properties;

public class Configs extends Properties {

    public boolean readBoolean(String field){
        return Boolean.parseBoolean(this.getProperty(field));
    }

    public int readInt(String field){
        return Integer.parseInt(this.getProperty(field));
    }

    public String[] readArrays(String field){
        String str = this.getProperty(field);
        return str.split(",");
    }

    public String[][] read2DArrays(String field){
        String str = this.getProperty(field);
        String[] str1 = str.split(";");
        int length = str1[1].length();
        String[][] str2 = new String[str.length()][length];
        for (int x=0; x< str1.length ; x++) {
            String[] row = str1[x].split(",");
            for(int y=0 ; y<length ; y++ ){
                str2[x][y] = row[y];
            }
        }
        return str2;
    }

}
