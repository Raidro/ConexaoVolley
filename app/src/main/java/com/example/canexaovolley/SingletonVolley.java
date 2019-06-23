package com.example.canexaovolley;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class SingletonVolley {

    private static SingletonVolley myInstancia;
    private RequestQueue myQueue;
    private static Context myContext;


    //metodo construtor, passar quem e o contexto

    public SingletonVolley(Context context) {

        myContext = context;
        myQueue = getRequestFila();

        this.myQueue = myQueue;
    }

    // gerencia a fila
    public RequestQueue getRequestFila() {
        if (myQueue == null) {
            myQueue = Volley.newRequestQueue(myContext.getApplicationContext());

        }
        return myQueue;
    }


    //synch est[a sincronizando as threads
    public static synchronized SingletonVolley getInstance(Context context) {

        if (myInstancia == null) {

            myInstancia = new SingletonVolley(context);

        }
        return myInstancia;
    }


    //adiciona a fila
    public <T> void addReqFila(Request<T> req) {


        getRequestFila().add(req);

    }
}
