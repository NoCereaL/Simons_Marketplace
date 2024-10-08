package com.example.simonsmarket;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.simonsmarket.databinding.FragmentLoginBinding;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    public static String result = "";
    public LoginFragment lf = this;
    public static View v;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        v = view;       //On View Created set static v = to this view so we can call this view in backendworker

        String user = String.valueOf(binding.loginTextfield.getText());
        String password = String.valueOf(binding.loginPassfield.getText());
        BackendWorker backend = new BackendWorker(getContext());
    }


    public static class BackendWorker extends AsyncTask<String, Void, String>{
        Context context;
        public AlertDialog alertDialog;
        public static String status;
        public BackendWorker(Context ctx){context = ctx;}
        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String login_url = "https://moyanask.com/EPTeam1/login.php";
            if(type.equals("login")) {
                try {
                    String user = params[1];
                    String password = params[2];
                    System.out.println("Login Process began");
                    URL url = new URL(login_url);
                    HttpURLConnection con = (HttpURLConnection)url.openConnection();
                    con.setRequestMethod("POST");
                    con.setDoOutput(true);
                    con.setDoInput(true);

                    System.out.println("Connected");

                    OutputStream outputStream = con.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
                    String post_data = URLEncoder.encode("user", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8") + "&"
                            + URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");
                    writer.write(post_data);
                    writer.flush();
                    writer.close();
                    outputStream.close();

                    InputStream inputStream = con.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    result = "";
                    String line = "";


                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }
                    System.out.println(result);

                    if (result.equals("Approved")) {
                        System.out.println(result);

                    }
                    //result = bufferedReader.readLine();

                    bufferedReader.close();
                    inputStream.close();
                    con.disconnect();
                    status = result;
                    return result;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            System.out.println("Started");
            alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("Login Status");
        }

        @Override
        protected void onPostExecute(String res) {
            System.out.println("Result: \n");
            alertDialog.setMessage(res);        //Return server php file/http response
            alertDialog.show();             //Show alert dialog
            System.out.println(res);
            if(res.equals("Approved")) {                //Check Approval
                LoginFragment.LoginSuccessful(v);       //Call LoginSuccessful method with static v as this loginFragment view
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }

    public static void LoginSuccessful(@NonNull View view){     //Make class static so we can call in BackendWorker static class
        Navigation.findNavController(view).navigate(R.id.action_LoginFragment_to_mainActivity2);           //Navigation if login successful

    }


    public static View newView(@NonNull View view){
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}