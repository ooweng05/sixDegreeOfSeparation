public class SixDegreeOfSeparationTest {
    public static void main(String[] args) {
        SixDegreeOfSeparation sds = new SixDegreeOfSeparation();
        try {
            sds.loadGraph("D:\\mod8\\exercise2\\sixDegreesOfSeparation\\src\\main\\java\\facebook_combinedSmall.txt");

            int[][] cases = {
                    {200, 500},
                    {12, 13},
                    {17, 19},
                    {55, 78},
                    {1020, 332}
            };

            for (int[] pair : cases) {
                int src = pair[0], dest = pair[1];
                int degree = sds.degreeOfSeparation(src, dest);
                System.out.printf("Degree of separation between %d and %d: %d%n", src, dest, degree);
                if (degree != -1) {
                    System.out.print("Path: ");
                    System.out.println(sds.getPath(src, dest));
                } else {
                    System.out.println("No path found.");
                }
                System.out.println("-----------");
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}