package com.example.orvilleclarke.testfrag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.orvilleclarke.testfrag.ToDo.ToDoContent;


public class MainActivity extends AppCompatActivity
implements ItemFragment.OnListFragmentInteractionListener{

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layoutcontainer);



        if (savedInstanceState != null) {
            try {
//                getSupportFragmentManager().beginTransaction()
//                        .add(R.id.container, new ItemFragment())
//                        .commit();
//
//                getSupportFragmentManager().beginTransaction()
//                        .add(R.id.container2, new AddFrag())
//                        .commit();
//
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else{

            try{
                getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ItemFragment())
                    .commit();

                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container2, new AddFrag())
                        .commit();

                }catch(Exception e){
                e.printStackTrace();
            }
        }
//        else{
//            return;
        }




    @Override
    public void onListFragmentInteraction(int position) {

        try {

            // Create Intent that swaps out MainActivity and brings in EditOrDeleteActivity
            Intent intent = new Intent(MainActivity.this, EditOrDeleteActivity.class);

            // Create bundle
            Bundle b = new Bundle();

            // Adds key and ArrayList Index converted to String to the bundle b
            b.putString("key", String.valueOf(position));
            // Adds Bundle b to the intent
            intent.putExtras(b);
            //TODO figure out why startActivityForResult doesn't work
            startActivityForResult(intent, RESULT_OK);
            finish();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data){

    super.onActivityResult(requestCode, resultCode,data);
    if(resultCode == RESULT_OK){


    }
    if(requestCode == RESULT_CANCELED){

    }

}

    public View onAddItem(View view){


        // Initializes etNewItem variable with the EditText etNewItem field
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);

        String itemText = etNewItem.getText().toString();
        int value =ToDoContent.ITEMS.size()+ 1;


        ToDoContent.ToDoItem newToDo = new ToDoContent.ToDoItem(String.valueOf(value),itemText.toString());
//        ToDoContent.ITEMS.add(newToDo);

        try {
            ToDoContent.addItemToList(newToDo, getApplicationContext());
        }catch(Exception e){
            e.printStackTrace();
        }
       Intent refresh = new Intent(this, MainActivity.class);
        startActivityForResult(refresh, RESULT_OK);
        this.finish();


        return view;

    }
}
