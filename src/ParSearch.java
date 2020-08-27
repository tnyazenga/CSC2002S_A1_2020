import java.util.*;
import java.io.*;

public class ParSearch {
    public static void main (String[] args) throws Exception {
        //Scanner systemScan = new Scanner(System.in);
        System.out.println("Input file "+ args[0]);
        System.out.println("Output file "+ args[1]);
        File inputFile;
        File outputFile;
        inputFile = new File(""+args[0]);
        outputFile = new File(""+args[1]);
        //Scanner fileScan = new Scanner(inputFile);
        float[][] basinData = readData(inputFile);

        FileWriter fileWrite = new FileWriter(""+args[1]);

        ArrayList<String> basins= new ArrayList<String>();
        int bsn=0;


        int size = testArray.length;
        FindBasin t = new FindBasin(testArray,0, size, outArray);
        ForkJoinPool pool = new ForkJoinPool();
        double t1 = System.currentTimeMillis();
        pool.execute(t);
        double t2 =System.currentTimeMillis() - t1;

        basins =t.get()
        bsn = basins.size()
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
}
