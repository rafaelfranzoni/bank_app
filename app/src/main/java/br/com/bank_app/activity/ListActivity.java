package br.com.bank_app.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import br.com.bank_app.R;
import br.com.bank_app.control.Bank;
import br.com.bank_app.control.Util;
import br.com.bank_app.model.Credencial;
import br.com.bank_app.model.Transacoes;

public class ListActivity extends AppCompatActivity {

    private Credencial credencial;

    private ListView lv;

    private TextView name, conta, saldo;

    private ImageButton logout;

    ArrayList<HashMap<String, String>> listTransacoes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        credencial = Bank.getInstance().getCredencial();

        lv = this.findViewById(R.id.list);

        name = this.findViewById(R.id.lb_name);
        conta = this.findViewById(R.id.lb_conta);
        saldo = this.findViewById(R.id.lb_saldo);

        logout = this.findViewById(R.id.ibtn_logout);

        name.setText(credencial.getName());
        conta.setText(credencial.getBankAccount() + " / " + Util.formatConta(credencial.getAgency()));
        saldo.setText(Util.formatValor(credencial.getBalance()));

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bank.getInstance().setCredencial(new Credencial());
                finish();

                Intent intent = new Intent(ListActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        searchTransacao();
    }

    public void searchTransacao() {
        RequestQueue queue = Volley.newRequestQueue(ListActivity.this);  // this = context

        final String url = "https://bank-app-test.herokuapp.com/api/statements/"+credencial.getUserId();

        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener< JSONObject >()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());

                        ArrayList<HashMap<String, String>> listTransacoes;
                        JSONArray dados = null;
                        try {
                            dados = response.getJSONArray("statementList");

                            listTransacoes = new ArrayList<>();

                            // loop através de todos as transacoes
                            for (int i = 0; i < dados.length(); i++) {

                                JSONObject value = dados.getJSONObject(i);

                                // mapa de hash tmp para transacoes único
                                HashMap<String, String> transacoes = new HashMap<>();

                                SimpleDateFormat sdf = (SimpleDateFormat)SimpleDateFormat.getInstance();
                                sdf.applyPattern("yyyy-MM-dd");
                                Date dta = null;
                                try {
                                    dta = sdf.parse(value.getString("date"));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                transacoes.put("date", Util.getStrData(dta));
                                transacoes.put("desc", value.getString("desc"));
                                transacoes.put("title", value.getString("title"));
                                transacoes.put("value", Util.formatValor(value.getString("value")));

                                // adicionando transacoes à lista de transacoes
                                listTransacoes.add(transacoes);
                            }
                            ListAdapter adapter = new SimpleAdapter(ListActivity.this, listTransacoes,
                                    R.layout.list_item, new String[]{"title", "date", "value"},
                                    new int[]{R.id.lb_title, R.id.lb_date, R.id.lb_value});
                            lv.setAdapter(adapter);
                            Log.d("ListActivity", listTransacoes.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }
        );

        // add it to the RequestQueue
        queue.add(getRequest);
    }
}