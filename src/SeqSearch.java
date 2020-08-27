import java.util.*;
import java.io.*;

public class SeqSearch {
    public static void main (String[] args) throws Exception {
        Scanner systemScan = new Scanner(System.in);
        System.out.println("Input file "+ args[0]);
        System.out.println("Output file "+ args[1]);
        File inputFile;
        File outputFile;
        inputFile = new File(""+args[0]);
        outputFile = new File(""+args[1]);
        Scanner fileScan = new Scanner(inputFile);
        float[][] basinData = readData(inputFile);

        FileWriter fileWrite = new FileWriter(""+args[1]);

        ArrayList<String> basins= new ArrayList<String>();
        int bsn=0;

        double t1 = System.currentTimeMillis();
        for (int i = 0; i < basinData.length; i ++) {
            for (int j = 0; j < basinData[0].length; j ++){
                if (isBasin(i, j, basinData) == true) {
                    System.out.print(i + "  " + j);
                    basins.add(i + "  " + j);
                    System.out.println();
                    bsn++;
                }
            }
        }
        double t2 =System.currentTimeMillis() - t1;

        fileWrite.write(""+bsn);
        for (String i : basins){
            fileWrite.write("\n");
            fileWrite.write(i);
        }

        fileWrite.close();

        System.out.println("Run time: "+t2+"ms");
    }
    public static float[][] readData(File file) throws FileNotFoundException {
        Scanner scan = new Scanner(file);
        String info = scan.nextLine();
        //System.out.println("Info: "+info);
        String[] infoSplit = info.split(" ");
        int dim1 = Integer.parseInt(infoSplit[0]);
        int dim2 = Integer.parseInt(infoSplit[1]);
        float[][] array = new float[dim1][dim2];
        while (scan.hasNextLine()){
            String[] line = scan.nextLine().trim().split(" ");
            int i = 0;
            int count = 0;
            while (i < dim1) {
                for (int j = 0; j < dim2 ; j ++){
                    array[i][j] = Float.parseFloat(line[count]);
                    count ++;
                }
                i ++;
            }
        }
        return array;
    }

    public static boolean isBasin(int rowNum, int columnNum, float[][] mountainArray){
        if (rowNum == 0 && columnNum == 0){    // Top left

            return false;
        }
        else if (rowNum == 0 && columnNum == mountainArray.length -1 ) { // Top Right
            return false;
        }
        else if(rowNum == mountainArray[0].length -1  && columnNum == 0){ // Bottom left
            return false;
        }
        else if(rowNum == mountainArray[0].length - 1 && columnNum == mountainArray.length - 1){   // Bottom right
            return false;
        }
        else if (columnNum == 0){   // Left of box

            return false;
        }
        else if (columnNum == mountainArray.length - 1) {   // Right of box

            return false;
        }
        else if (rowNum == 0){   // Top of box

            return false;
        }
        else if (rowNum == mountainArray[0].length - 1) { //Bottom of box

            return false;
        }
        else {  // General case
            if (mountainArray[rowNum + 1][columnNum] - mountainArray[rowNum][columnNum] >= 0.01 && mountainArray[rowNum+1][columnNum + 1] - mountainArray[rowNum][columnNum] >= 0.01 && mountainArray[rowNum + 1][columnNum -1] - mountainArray[rowNum][columnNum] >= 0.01 &&
                    mountainArray[rowNum][columnNum + 1] - mountainArray[rowNum][columnNum] >= 0.01 && mountainArray[rowNum][columnNum -1] - mountainArray[rowNum][columnNum] >= 0.01 &&
                    mountainArray[rowNum - 1][columnNum] - mountainArray[rowNum][columnNum] >= 0.01 && mountainArray[rowNum - 1][columnNum + 1] - mountainArray[rowNum][columnNum] >= 0.01 && mountainArray[rowNum - 1][columnNum-1] - mountainArray[rowNum][columnNum] >= 0.01)
                return true;
        }
        return false;
    }
}
