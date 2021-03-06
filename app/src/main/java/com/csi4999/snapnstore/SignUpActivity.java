package com.csi4999.snapnstore;


import android.app.ProgressDialog;
import android.os.AsyncTask;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    Button register, log_in;
    EditText First_Name, Last_Name, User_Name, Email, Password ;
    String F_Name_Holder, L_Name_Holder, U_Name_Holder, EmailHolder, PasswordHolder;
    String finalResult ;
    String HttpURL = "http://35.223.15.126/AndroidUserRegistration.php";
    Boolean CheckEditText ;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Assign Id'S
        First_Name = (EditText)findViewById(R.id.editTextF_Name);
        Last_Name = (EditText)findViewById(R.id.editTextL_Name);
        User_Name = (EditText)findViewById(R.id.editTextU_Name);
        Email = (EditText)findViewById(R.id.editTextEmail);
        Password = (EditText)findViewById(R.id.editTextPassword);

        register = (Button)findViewById(R.id.Submit);
        log_in = (Button)findViewById(R.id.Login);

        //Adding Click Listener on button.
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Checking whether EditText is Empty or Not
                CheckEditTextIsEmptyOrNot();

                if(CheckEditText){

                    // If EditText is not empty and CheckEditText = True then this block will execute.

                    UserRegisterFunction(F_Name_Holder,L_Name_Holder, U_Name_Holder, EmailHolder, PasswordHolder);

                }
                else {

                    // If EditText is empty then this block will execute .
                    Toast.makeText(SignUpActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }


            }
        });

    }

    public void CheckEditTextIsEmptyOrNot(){

        F_Name_Holder = First_Name.getText().toString();
        L_Name_Holder = Last_Name.getText().toString();
        U_Name_Holder = User_Name.getText().toString();
        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();


        if(TextUtils.isEmpty(F_Name_Holder) || TextUtils.isEmpty(L_Name_Holder) || TextUtils.isEmpty(U_Name_Holder) || TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder))
        {

            CheckEditText = false;

        }
        else {

            CheckEditText = true ;
        }

    }

    public void UserRegisterFunction(final String firstName, final String lastName, final String userName, final String email, final String password){

        class UserRegisterFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(SignUpActivity.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                    Toast.makeText(SignUpActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("firstName",params[0]);

                hashMap.put("lastName",params[1]);

                hashMap.put("userName",params[2]);

                hashMap.put("email",params[3]);

                hashMap.put("password",params[4]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        UserRegisterFunctionClass userRegisterFunctionClass = new UserRegisterFunctionClass();

        userRegisterFunctionClass.execute(firstName,lastName,userName,email,password);
    }

}