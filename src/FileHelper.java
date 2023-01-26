import java.io.*;
import java.util.ArrayList;

public class FileHelper {

    public String path;
    public String fileName;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public FileHelper(String path, String fileName) {
        this.path = path;
        this.fileName = fileName;
    }


    /**

     This method checks if a file exists or not
     @return true if the file exists, false otherwise
     */
    //Statt FileNotFoundE. wird hier diese Methode genutzt
    public boolean checkIfFileExists() {
        return new File(this.path + this.fileName).exists();
    }

    /**

     This method reads a file and returns the number of lines of the file
     @return number of lines in the file.
     @throws IOException if there is an error reading the file
     */
    public int getAmountOfLines() {
        int lines = 0;
        try (BufferedReader reader = getFile()) {
            while (reader.readLine() != null) lines++;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    /**

     This method reads a file and returns the content in an ArrayList of Strings
     @return ArrayList of strings containing the content of the file
     @throws IOException if there is an error reading the file
     */
    public ArrayList<String> processFile() {
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader reader = getFile()) {
            String line;

            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    /**

     This method returns a BufferedReader for a file
     @return BufferedReader for the file
     @throws FileNotFoundException if the file is not found
     */
    private BufferedReader getFile() throws FileNotFoundException {
        return new BufferedReader(new FileReader(this.path + this.fileName));
    }





}
