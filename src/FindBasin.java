import java.util.*;

public class FindBasin extends RecursiveTask<boolean[][]> {
    static int THRESHOLD = 10000;
    int lo;
    int hi;
    public float[][] basinData;
    ArrayList<String> basins= new ArrayList<String>();

    public FindBasin(float[][] basinData;, int lo, int hi, ArrayList<String> basins) {
        this.basinData = basinData;
        this.lo = lo;
        this.hi = hi;
        this.basins = basins;
    }

    protected ArrayList<String> compute() {
        List<ForkJoinTask> children = new ArrayList();
        for (int i = 0; i < basinData[0].length; i++) {
            children.add(new helperFindBasin(0, basinData.length, i, basinData, basins));
        }
        invokeAll(children);

        return basins;
    }
}

public class helperFindBasin extends RecursiveAction {
    public float[][] basinData;
    ArrayList<String> basins= new ArrayList<String>();
    int lo;
    int hi;
    int rowNum;
    static int THRESHOLD = 100000;

    helperFindBasin(int lo, int high, int rowNum, float[][] basinData, boolean[][] basins) {
        this.lo = lo;
        this.hi = high;
        this.rowNum = rowNum;
        this.basinData = basinData;
        this.basins = basins;
    }

    protected void compute() {
        if (hi-lo < THRESHOLD) {
            computeDirectly();
        }
        else {
            helperFindBasin left = new helperFindBasin(lo, (hi+lo)/2, rowNum, basinData, basins);
            helperFindBasin right = new helperFindBasin((hi+lo)/2, hi, rowNum, basinData, basins);
            //left.fork();
            //right.compute();
            //left.join();
            invokeAll(left, right);
        }
    }

    protected void computeDirectly() {
        for (int i = 0; i < hi;i ++){
            if (isBasin(rowNum, i, basinData)) {
                basins.add(i + " " + j);
            }
        }
    }

    public ArrayList<String> getbasins(){
        return basins;
    }
    
    public static boolean isBasin(int rowNum, int columnNum, float[][] basinData){
        if (rowNum == 0 && columnNum == 0){    // Top left

            return false;
        }
        else if (rowNum == 0 && columnNum == basinData.length -1 ) { // Top Right
            return false;
        }
        else if(rowNum == basinData[0].length -1  && columnNum == 0){ // Bottom left
            return false;
        }
        else if(rowNum == basinData[0].length - 1 && columnNum == basinData.length - 1){   // Bottom right
            return false;
        }
        else if (columnNum == 0){   // Left of box

            return false;
        }
        else if (columnNum == basinData.length - 1) {   // Right of box

            return false;
        }
        else if (rowNum == 0){   // Top of box

            return false;
        }
        else if (rowNum == basinData[0].length - 1) { //Bottom of box

            return false;
        }
        else {  // General case
            if (basinData[rowNum + 1][columnNum] - basinData[rowNum][columnNum] >= 0.01 &&
                basinData[rowNum+1][columnNum + 1] - basinData[rowNum][columnNum] >= 0.01 &&
                basinData[rowNum + 1][columnNum -1] - basinData[rowNum][columnNum] >= 0.01 &&
                basinData[rowNum][columnNum + 1] - basinData[rowNum][columnNum] >= 0.01 &&
                basinData[rowNum][columnNum -1] - basinData[rowNum][columnNum] >= 0.01 &&
                basinData[rowNum - 1][columnNum] - basinData[rowNum][columnNum] >= 0.01 &&
                basinData[rowNum - 1][columnNum + 1] - basinData[rowNum][columnNum] >= 0.01 &&
                basinData[rowNum - 1][columnNum-1] - basinData[rowNum][columnNum] >= 0.01)
                return true;
        }
        return false;
    }
}
