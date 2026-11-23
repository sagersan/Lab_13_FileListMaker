import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class FileListMaker1
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        ArrayList<String> list = new ArrayList<>();

        boolean needsToBeSaved = false;
        String currentFileName = "";

        String cmd;
        boolean done = false;

        do
        {
            displayList(list);

            cmd = SafeInput.getRegExString(
                    in,
                    "A-Add D-Delete I-Insert M-Move O-Open S-Save C-Clear V-View Q-Quit",
                    "[AaDdIiMmOoSsCcVvQq]");

            switch(cmd.toUpperCase())
            {
                case "A":
                    addItem(in, list);
                    needsToBeSaved = true;
                    break;

                case "D":
                    deleteItem(in, list);
                    needsToBeSaved = true;
                    break;

                case "I":
                    insertItem(in, list);
                    needsToBeSaved = true;
                    break;

                case "M":
                    moveItem(in, list);
                    needsToBeSaved = true;
                    break;

                case "C":
                    list.clear();
                    needsToBeSaved = true;
                    break;

                case "V":
                    displayList(list);
                    break;

                case "S":
                    currentFileName = SafeInput.getNonZeroLenString(
                            in,
                            "Enter file name");

                    saveFile(list, currentFileName + ".txt");

                    needsToBeSaved = false;

                    break;

                case "O":
                    currentFileName = SafeInput.getNonZeroLenString(
                            in,
                            "Enter file name to open");

                    list = loadFile(currentFileName + ".txt");

                    needsToBeSaved = false;

                    break;

                case "Q":
                    done = quitProgram(in);
                    break;
            }

        } while(!done);
    }
    private static void moveItem(
            Scanner in,
            ArrayList<String> list)
    {
        if(list.size() < 2)
        {
            System.out.println("Need at least two items.");
            return;
        }

        int from =
                SafeInput.getRangedInt(
                        in,
                        "Move which item",
                        1,
                        list.size());

        int to =
                SafeInput.getRangedInt(
                        in,
                        "Move to position",
                        1,
                        list.size());

        String item = list.remove(from - 1);

        list.add(to - 1, item);
    }
    private static void saveFile(
            ArrayList<String> list,
            String fileName)
    {
        try
        {
            PrintWriter out =
                    new PrintWriter(
                            new FileWriter(fileName));

            for(String item : list)
            {
                out.println(item);
            }

            out.close();

            System.out.println("File saved.");
        }
        catch(IOException e)
        {
            System.out.println("Error saving file.");
        }
    }
    private static ArrayList<String> loadFile(String fileName)
    {
        ArrayList<String> list =
                new ArrayList<>();

        try
        {
            Scanner file =
                    new Scanner(
                            new File(fileName));

            while(file.hasNextLine())
            {
                list.add(file.nextLine());
            }

            file.close();

            System.out.println("File loaded.");
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File not found.");
        }

        return list;
    }

    private static void addItem(Scanner in, ArrayList<String> list)
    {
        String item = SafeInput.getNonZeroLenString(
                in,
                "Enter item to add");

        list.add(item);
    }

    private static void deleteItem(Scanner in, ArrayList<String> list)
    {
        if(list.size() == 0)
        {
            System.out.println("List is empty.");
            return;
        }

        int itemNum = SafeInput.getRangedInt(
                in,
                "Select item number to delete",
                1,
                list.size());

        list.remove(itemNum - 1);
    }

    private static void insertItem(Scanner in, ArrayList<String> list)
    {
        String item = SafeInput.getNonZeroLenString(
                in,
                "Enter item to insert");

        int position = SafeInput.getRangedInt(
                in,
                "Enter position",
                1,
                list.size() + 1);

        list.add(position - 1, item);
    }

    private static void displayList(ArrayList<String> list)
    {
        System.out.println("\nCurrent List:");

        if(list.size() == 0)
        {
            System.out.println("(empty)");
        }
        else
        {
            for(int i = 0; i < list.size(); i++)
            {
                System.out.println((i + 1) + ". " + list.get(i));
            }
        }

        System.out.println();
    }

    private static boolean quitProgram(Scanner in)
    {
        return SafeInput.getYNConfirm(
                in,
                "Are you sure you want to quit?");
    }
}
