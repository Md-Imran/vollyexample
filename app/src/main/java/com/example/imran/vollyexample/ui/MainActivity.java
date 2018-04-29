package com.example.imran.vollyexample.ui;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.imran.vollyexample.R;
import com.example.imran.vollyexample.app.AppController;
import com.example.imran.vollyexample.model.CustomUser;
import com.example.imran.vollyexample.model.UserList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    // json object response url
    private String urlJsonObj = "https://api.androidhive.info/volley/person_object.json";
    private String BASE_URL = "https://reqres.in";
    // json array response url
    private String urlJsonArry = "https://api.androidhive.info/volley/person_array.json";
    private ArrayList<UserList.UserDataList> mUserDataList = new ArrayList<>();
    private String DATA_SET_BASE_URL = "https://reqres.in";
    private String CUSTOM_URL = "https://www.reqres.in/api/users?page=2";
    int numberOfRequestsCompleted;
    private List<CustomUser> customUserList = new ArrayList<>();
    private static String TAG = MainActivity.class.getSimpleName();
    private Button btnMakeObjectRequest, btnMakeArrayRequest, btnPostRequest, btnLodeData;


    // Progress dialog
    private ProgressDialog pDialog;

    private TextView txtResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindView();
        buttonAction();
    }

    private void bindView() {
        btnMakeObjectRequest = (Button) findViewById(R.id.btnObjRequest);
        btnMakeArrayRequest = (Button) findViewById(R.id.btnArrayRequest);
        btnLodeData = (Button) findViewById(R.id.btnLodeData);
        btnPostRequest = (Button) findViewById(R.id.btnPostRequest);
        txtResponse = (TextView) findViewById(R.id.txtResponse);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
    }

    private void buttonAction() {
        btnMakeObjectRequest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // making json object request
                makeJsonObjectRequest();
            }
        });

        btnMakeArrayRequest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // making json array request
                makeJsonArrayRequest();
            }
        });

        btnPostRequest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // making post request
                //postRequest();
                try {
                    registration();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btnLodeData.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getCustomRequest();

            }
        });
    }

    /**
     * Method to make json object request where json response starts wtih {
     */
    private void makeJsonObjectRequest() {
        showpDialog();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlJsonObj, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {
                            String name = response.getString("name");
                            String email = response.getString("email");
                            JSONObject phone = response.getJSONObject("phone");
                            String home = phone.getString("home");
                            String mobile = phone.getString("mobile");

                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Name : " + name + " Email " + email +
                                    " Home " + home + " Mobile " + mobile);
                            txtResponse.setText(stringBuilder);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Got Jeson Exception" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                        hidepDialog();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    /**
     * Method to make json array request where response starts with [
     */
    private void makeJsonArrayRequest() {

        showpDialog();
        final StringBuilder stringBuilder = new StringBuilder();


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlJsonArry, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        String name = jsonObject.getString("name");
                        String email = jsonObject.getString("email");
                        JSONObject phone = jsonObject.getJSONObject("phone");
                        String home = phone.getString("home");
                        String mobile = phone.getString("mobile");
                        stringBuilder.append("Name : " + name + " Email " + email +
                                " Home " + home + " Mobile " + mobile);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    hidepDialog();
                }
                txtResponse.setText(stringBuilder);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
    }

    private void POSTStringAndJSONRequest() {

        ///  RequestQueue queue = SingletonRequestQueue.getInstance(getApplicationContext()).getRequestQueue();

        VolleyLog.DEBUG = true;
        String uri = BASE_URL + "/api/users";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                VolleyLog.wtf(response, "utf-8");
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
            }

        }, errorListener) {

            public Priority getPriority() {
                return Priority.LOW;
            }

            @Override
            public Map getParams() {
                Map params = new HashMap();

                params.put("name", "Anupam");
                params.put("job", "Android Developer");
                return params;
            }

            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
                return headers;
            }

        };

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", "JournalDev.com");
            jsonObject.put("job", "To teach you the best");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(uri, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.wtf(response.toString(), "utf-8");
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();

            }
        }, errorListener) {
            public int getMethod() {
                return Method.POST;
            }

            @Override
            public Priority getPriority() {
                return Priority.NORMAL;
            }
        };

        StringRequest stringRequestPOSTJSON = new StringRequest(Request.Method.POST, uri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                VolleyLog.wtf(response);
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

            }
        }, errorListener) {
            public Request.Priority getPriority() {
                return Request.Priority.HIGH;
            }

            @Override
            public Map getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }

            @Override
            public byte[] getBody() throws AuthFailureError {

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("name", "Android Tutorials");
                    jsonObject.put("job", "To implement Volley in an Android Application.");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String requestBody = jsonObject.toString();

                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest);
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
        AppController.getInstance().addToRequestQueue(stringRequestPOSTJSON);
    }


    private void getCustomRequest() {
        showpDialog();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, CUSTOM_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        try {

                            /*page	2
                            per_page	3
                            total	12
                            total_pages*/
                            String page = response.getString("page");
                            String per_page = response.getString("per_page");
                            String total = response.getString("total");
                            String total_pages = response.getString("total_pages");
                            String data = response.getString("data");

                            JSONObject jsonObj = new JSONObject(data);

                            JSONArray ja_data = jsonObj.getJSONArray("data");
                            int length = jsonObj.length();
                            for (int i = 0; i < length; i++) {
                                JSONObject jobj = ja_data.getJSONObject(i);
                                CustomUser customUser = new CustomUser();
                                customUser.setId(jobj.getString("id").toString());
                                customUser.setFirst_name(jobj.getString("first_name").toString());
                                customUser.setLast_name(jobj.getString("last_name").toString());
                                customUser.setAvatar(jobj.getString("avatar").toString());
                                customUserList.add(customUser);
                                /* Toast.makeText(MainActivity.this, ja_dataJSONObject.getString("id").toString(), Toast.LENGTH_LONG).show();
                                Toast.makeText(MainActivity.this, ja_dataJSONObject.getString("first_name").toString(), Toast.LENGTH_LONG).show();
                                Toast.makeText(MainActivity.this, ja_dataJSONObject.getString("last_name").toString(), Toast.LENGTH_LONG).show();
                                Toast.makeText(MainActivity.this, ja_dataJSONObject.getString("avatar").toString(), Toast.LENGTH_LONG).show();
                        */
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Got Jeson Exception" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                        hidepDialog();


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            if (error instanceof NetworkError) {
                Toast.makeText(getApplicationContext(), "No network available", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }
    };

    private void postRequest() {

        String url = BASE_URL + "/api/users";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                    }
                },
                errorListener
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", "Imran");
                params.put("domain", "http://itsalif.info");
                params.put("address", "221/Baker street");
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(postRequest);
    }

    private void registration() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", "sydney@fife");
        jsonObject.put("password", "pistol");
        String uri = BASE_URL + "/api/register";
        JsonObjectRequest jsonObjectPostRequest = new JsonObjectRequest(Request.Method.POST, uri, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
            }
        }, errorListener) {
            @Override
            public Request.Priority getPriority() {
                return Request.Priority.NORMAL;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonObjectPostRequest);
    }


}
