package br.com.bank_app.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.bank_app.R;
import br.com.bank_app.control.Bank;
import br.com.bank_app.model.Credencial;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUser, edtPassword;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUser = this.findViewById(R.id.edtUser);
        edtPassword = this.findViewById(R.id.edtPassword);

        btnLogin = (Button)this.findViewById(R.id.btnLogin);

        inicializar();
    }

    private void inicializar() {

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isValidPassword(edtPassword.getText().toString())){
                    System.out.println("Not Valid");
                }else{
                    System.out.println("Valid");
                    RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);  // this = context

                    String url = "https://bank-app-test.herokuapp.com/api/login";
                    StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>()
                            {
                                @Override
                                public void onResponse(String r) {

                                    JSONObject response = null;
                                    JSONObject dados = null;
                                    try {
                                        response = new JSONObject(r);
                                        dados = response.getJSONObject("userAccount");
                                        Credencial credencial = new Credencial();

                                        credencial.setAgency(dados.getString("agency"));
                                        credencial.setBalance(dados.getString("balance"));
                                        credencial.setBankAccount(dados.getString("bankAccount"));
                                        credencial.setName(dados.getString("name"));
                                        credencial.setUserId(dados.getString("userId"));

                                        Bank.iniciar(credencial);
                                        finish();

                                        Intent intent = new Intent(LoginActivity.this, ListActivity.class);
                                        startActivity(intent);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener()
                            {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    System.out.println(error);
                                    Log.d("Error.Response", String.valueOf(error));
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(LoginActivity.this);
                                    alerta.setIcon(android.R.drawable.ic_dialog_alert);
                                    alerta.setTitle("Algo deu errado!");
                                    alerta.setMessage("Não foi possível logar com estas credenciais. Verifique!");
                                    alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {}
                                    });
                                    alerta.show();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String, String>  params = new HashMap<String, String>();
                            params.put("user", edtUser.getText().toString());
                            params.put("password", edtPassword.getText().toString());

                            return params;
                        }
                    };
                    queue.add(postRequest);
                }
            }
        });
    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }
}