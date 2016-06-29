package com.example.orvilleclarke.testfrag;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.orvilleclarke.testfrag.ToDo.ToDoContent;

import java.util.ArrayList;

public class EditOrDeleteActivity extends AppCompatActivity {


    ArrayList<ToDoContent.ToDoItem> items;
    ArrayAdapter<String> itemsAdapter;
    TextView tvItem;
    TextView tvItemId;
    EditText etUpdateText ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_or_delete);


        // links textview to tvItem
        tvItem = (TextView) findViewById(R.id.tvItem);
        tvItemId = (TextView) findViewById(R.id.todoId);
        // links edittext to etUpdateText
        etUpdateText = (EditText) findViewById(R.id.etUpdateText);

        // gets messages from intent
        Bundle b = getIntent().getExtras();

        if (b != null) {
            ArrayList<ToDoContent.ToDoItem> whatToDisplay = new ArrayList<ToDoContent.ToDoItem>();
            ToDoContent ddd = new ToDoContent();
            whatToDisplay = ddd.readItems(getApplicationContext());

            ToDoContent.ToDoItem todo;
            String index = b.getString("key");
            int i = 0;

            ToDoContent.ToDoItem editItem = whatToDisplay.get(Integer.parseInt(index));

            tvItem.setText(editItem.content);
            tvItemId.setText(index);



        }
    }

    public void onUpdate(View view){


        // grabs string from the editText etUpdateText and saves to string
        String fieldValueNew = etUpdateText.getText().toString();
        String fieldValueOld = tvItem.getText().toString();
        String fieldValueId =  tvItemId.getText().toString();

        if(fieldValueNew.isEmpty()){


        }else {

            ToDoContent.ToDoItem.UpdateItem(fieldValueId, fieldValueNew, getApplicationContext());

        }
        // Sends user back to MainActivity
        Intent intent = new Intent(EditOrDeleteActivity.this, MainActivity.class);
        startActivityForResult(intent, RESULT_OK);
        finish();



    }

    public void onCancel(View view){

        Intent intent = new Intent(EditOrDeleteActivity.this, MainActivity.class);
        startActivityForResult(intent,RESULT_CANCELED);


    }
    public void onDelete (View view){

        // GET THE ArrayList index FROM THE INDEX
        String fieldValueId =  tvItemId.getText().toString();

        // DELETE METHOD
        ToDoContent.ToDoItem.DeleteItem(fieldValueId, getApplicationContext());

        // Build intent and return to the main menu
        Intent intent = new Intent(EditOrDeleteActivity.this, MainActivity.class);
        startActivityForResult(intent,RESULT_OK);

        }




    }



