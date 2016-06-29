package com.example.orvilleclarke.testfrag.ToDo;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Orville Clarke on 6/25/2016.
 */
public class ToDoContent  {

    // PROPERTIES
    private String listItem;
    private static final int COUNT = 25;

    // GLOBAL VARIABLES
    public static final ArrayList<ToDoItem> ITEMS = new ArrayList<ToDoItem>();

    public static final Map<String, ToDoItem> ITEM_MAP = new HashMap<String, ToDoItem>();


    static {
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addItem(createToDoItem(i));
        }
    }



    //*************************************************************
    //*************************************************************
    //*************************************************************
    //******************TODOCONTENT METHODS************************

    // INDEX
    //          readItems -     READS ITEMS TO FILE
    //          writeItems -    WRITES ITEMS TO FILE
    //          addItem -       ADDS ITEMS TO GLOBAL VARIABLES
    //

    // READ TEXT FILE:

    public ArrayList<ToDoItem> readItems(Context fileContext){

        try{
            ITEMS.clear();
            ITEM_MAP.clear();

                String filename= "todo.txt";
                FileInputStream fileinput = fileContext.openFileInput(filename);
                ObjectInputStream in = new ObjectInputStream(fileinput);
                ArrayList<ToDoItem> listfound = new ArrayList<ToDoItem>();
                ToDoItem o;
                while((o = (ToDoItem)in.readObject())!=null){
                    ITEMS.add(o);

                }





        }catch(IOException e)
        {

        }catch(NullPointerException e){
           e.printStackTrace();
            return ITEMS;

        }catch(ClassNotFoundException e){
            e.printStackTrace();
            return ITEMS;
        }

        return ITEMS;
    }


    // WRITES TODOLIST TO TEXT FILE:

    public void writeItems(ArrayList<ToDoItem> todolist, Context fileContext){


        try{

            String filename = "todo.txt";
//


            // GETS FILE
            File file = new File(fileContext.getFilesDir(), filename);

            // INITIALIZES outputStream
            FileOutputStream outputStream = fileContext.openFileOutput(filename, Context.MODE_PRIVATE);

            // INITIALIZES ARRAY TO ItemsToWrite
            // HAVE TO COPY GLOBAL VARIABLE ITEMS TO ItemsToWrite OR ELSE IT WILL BE A POINTER
            // AND WHEN YOU CLEAR ITEMS YOU WILL CLEAR YOUR ItemsToWrite
            ArrayList<ToDoItem> ItemsToWrite = new ArrayList<ToDoItem>();

            // INITIALIZES ObjectOutputStream
            // THE FILE WILL USE THE OOS  TO WRITE OUT TO THE TEXT FILE
            ObjectOutputStream oos = new ObjectOutputStream(outputStream);

            // Write incoming list to local ArrayList and delete global resoivoir
            for (ToDoItem item: todolist) {
                ItemsToWrite.add(item);
            }



            // LOOPS Write to file ONE OBJECT AT A TIME
            for(ToDoItem item : ItemsToWrite)
                {

                    oos.writeObject(item);

                    oos.reset();

                }

            // CLOSE OutputStream
            outputStream.close();


// SEE IF FILE WROTe
//            FileInputStream fis = fileContext.openFileInput("todo.txt");
//            ObjectInputStream ois = new ObjectInputStream(fis);
//            ArrayList<ToDoItem> listfound = new ArrayList<ToDoItem>();
//            ToDoItem o;
////
//            while( (o =(ToDoItem)ois.readObject())!= null){
////               o  = (ToDoItem)ois.readObject();
//                listfound.add(o);
//            }


        }catch(IOException e){
            e.printStackTrace();
//        } catch(ClassNotFoundException e) {
//            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        }

    }




    //  ADDs TODOITEM TO THE GLOBAL VARIABLE LISTS

    private static void addItem(ToDoItem item) {
        ITEMS.add(item);
       ITEM_MAP.put(item.id, item);

    }


    //  ADDS ITEM TO TEXT FILE
    //   FIRST ADD ITEM TO GLOBAL VARIABLE
    //   THEN IT WRITES THEM TO FILE

    public static void addItemToList(ToDoItem item, Context fileContext){
        addItem(item);
        ToDoContent a = new ToDoContent();
        a.writeItems(ITEMS, fileContext);

    }





    private static ToDoItem createToDoItem(int position) {
        return new ToDoItem(String.valueOf(position), "Item " + position);
    }



    //*************************************************************
    //*************************************************************
    //*************************************************************
    //******************TODOITEM CLASS*****************************


    public static class ToDoItem implements Serializable {


        // PROPERTIES
        public final String id;
        public final String content;

        // PUBLIC CONSTRUCTOR WITH TWO VARIABLE INPUTS
        // STRING ID && STRING CONTENT
        public ToDoItem(String id, String content) {
            this.id = id;
            this.content = content;
        }

        // PUBLIC CONSTRUCTOR WITH ONE VARIABLE
        public ToDoItem( String content) {
            int value = ITEMS.size() + 1;
            this.id = String.valueOf(value);
            this.content = content;
        }


        //  UPDATES ENTRY IN FILE - HAS THREE PARAMETERS
        //  DROPS THE ITEM FROM A TODOITEM ARRAYLIST AND THEN SAVES NEW LIST TO FILE AND
        //  UPDATES GLOBAL RESOIVOIR
        //  IN JAVA CLASS YOU HAVE TO PASS IN A CONTEXT FROM THE ACTIVITY FOR
        //  THE JAVA CLASS TO BE ABLE TO SEE CONTEXT (GETFILESDIR() AND ALL)
        public static void UpdateItem(String Id, String fieldValueNew, Context context){


            ArrayList<ToDoContent.ToDoItem> UpdatedTodoList = new ArrayList<ToDoContent.ToDoItem>();
            // START COUNTER
            int i= 0;

            for (ToDoContent.ToDoItem item: ITEMS) {

                //  If counter is the same as arraylist index then update that item by saving
                //  Saving a new instance to the variable item
                if(i == Integer.parseInt(Id)){
                    item = new ToDoContent.ToDoItem(item.id, fieldValueNew);
                }

                // ADD ITEM TO updatedTodoList
                UpdatedTodoList.add(item);
                i++;
            }
            // SAVES updatedTodoList TO TEXT FILE
            ToDoContent todoInfo = new ToDoContent();

            // PASS CONTEXT ON TO writeItems
            todoInfo.writeItems(UpdatedTodoList, context);

        }




        // DELETES ENTRY FROM TEXT FILE - HAS THREE PARAMETERS
        //  DROPS THE ITEM FROM A TODOITEM ARRAYLIST AND THEN SAVES NEW LIST TO FILE AND
        //  UPDATES GLOBAL RESOIVOIR
        //  IN JAVA CLASS YOU HAVE TO PASS IN A CONTEXT FROM THE ACTIVITY FOR
        //  THE JAVA CLASS TO BE ABLE TO SEE CONTEXT (GETFILESDIR() AND ALL)
        public static void DeleteItem(String Id, Context context){

            ArrayList<ToDoContent.ToDoItem> UpdatedDeleteItems = new ArrayList<ToDoContent.ToDoItem>();

            //  COPY ARRAY TO possibleDeleteItems BUT DROP THE ITEM WITH ID THAT MATCHES POSITION
            int i = 0;
            for (ToDoContent.ToDoItem item: ToDoContent.ITEMS) {

                // IF THE COUNTER i IS EQUAL TO ArrayList INDEX DO NOT ADD TO UpdatedDeleteItems
                if(i == Integer.parseInt(Id)){

                }else{
                    UpdatedDeleteItems.add(item);
                }
                i++;
            }

            ToDoContent todoWrite = new ToDoContent();
            todoWrite.writeItems(UpdatedDeleteItems,context);
        }


        @Override
        public String toString() {
            return content;
        }
    }


}
