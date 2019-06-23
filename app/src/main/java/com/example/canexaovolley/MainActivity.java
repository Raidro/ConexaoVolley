package com.example.canexaovolley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {


    TextView txtMsg;
    Button btnMsg;
    RequestQueue queue;
    StringRequest sRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //iniciando os componentes
        btnMsg = (Button) findViewById(R.id.btnMsg);
        txtMsg = (TextView) findViewById(R.id.txtMsg);

        //evento de botao no click

        btnMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //EnviarRequisicao();
                reqJSON();
            }
        });


        queue = SingletonVolley.getInstance(this.getApplicationContext()).getRequestFila();

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void reqJSON() {

        String url = "http://www.mocky.io/v2/5d0e35eb3400007800ca4b4d";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //txtMsg.setText("A resposta e: " + response.toString());
                        txtMsg.setText("");
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = response.getJSONArray("usuario");
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject usuario = jsonArray.getJSONObject(i);
                                String nome = usuario.getString("nome");
                                String sexo = usuario.getString("sexo");
                                String email = usuario.getString("email");
                                txtMsg.append(nome +" \n"+ email + "\n " + sexo + " \n \n" + "---------------\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Erro! Sem Retorno", Toast.LENGTH_SHORT).show();
            }
        }
        );
        SingletonVolley.getInstance(this).addReqFila(jsonObjectRequest);

    }


    private void EnviarRequisicao() {

        //chamada basica do volley

        String url = "http://www.google.com.br";
        //queue = Volley.newRequestQueue(this);

        //qual o method, qual url

        sRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//se ele responder a resposta vem no response(variavel usada com resposta)

                txtMsg.setText("A resposta e: " + response.toString());


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                txtMsg.setText("Erro na requisicao" + error);

            }
        }

        );
        //disparar a thread de requisicao

        //queue.add(sRequest);

        SingletonVolley.getInstance(this).addReqFila(sRequest);

    }


}
