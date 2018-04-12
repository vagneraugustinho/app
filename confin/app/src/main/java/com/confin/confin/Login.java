package com.confin.confin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends Activity {

    private TextInputEditText campo_email, campo_senha;
    private Switch btn_manterLogado;
    private Button bnt_entrar, btn_cancelar_login;
    private TextView btn_cadastrar_se, btn_esqueceu_senha;
    private FirebaseAuth auth;

    private AlertDialog.Builder atencao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inicializaComponentes();
        botoes();
    }

    private void inicializaComponentes() {
        campo_email          = (TextInputEditText)findViewById(R.id.id_email_login);
        campo_senha          = (TextInputEditText)findViewById(R.id.id_senha_login);
        btn_manterLogado     = (Switch)findViewById(R.id.id_btn_manter_logado);
        btn_cadastrar_se     = (TextView)findViewById(R.id.id_btn_cadastrar_se);
        btn_esqueceu_senha   = (TextView)findViewById(R.id.id_btn_esqueceu_senha);
        bnt_entrar           = (Button)findViewById(R.id.id_btn_entrar);
        btn_cancelar_login   = (Button)findViewById(R.id.id_btn_cancelar_login);
    }

    private void botoes() {
        bnt_entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(campo_email.getText().length()==0) {
                    //Toast.makeText(getApplication(), "Campo de e-mail não está preenchido, verifique!", Toast.LENGTH_LONG).show();
campo_email.setError("O campo e-mail não pode ficar vazio!");
                }else if((campo_senha.getText().length()==0)) {
                   // Toast.makeText(getApplication(), "Campo de senha não está preenchido, verifique!", Toast.LENGTH_LONG).show();
campo_senha.setError("O campo senha não pode ficar vazio!");
                }else{
                    String email = campo_email.getText().toString().trim();
                    String senha = campo_senha.getText().toString().trim();
                    login(email, senha);
                }
            }
        });

        btn_cancelar_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btn_cadastrar_se.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(Login.this, Cadastro_Inicial.class));
            }
        });

        btn_esqueceu_senha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity( new Intent(Login.this, ResetSenha.class));
            }
        });

        btn_manterLogado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (btn_manterLogado.isChecked()) {
                   Snackbar.make(view, "botão não está funcionando ainda!", Snackbar.LENGTH_LONG).setAction("Action",null).show();
               }
            }
        });
    }

    private void login(final String email, String senha) {
        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplication(), "Bem vindo! "+ email, Toast.LENGTH_LONG).show();
                    startActivity(new Intent( Login.this, MainActivity.class));
                }else{
//                    alerta("Usuario ou senha incorretos!");
                    atencao("E-mail ou senha, estão incorretos, verifique!");
                }
            }
        });
    }

    private void atencao(String mensagem) {
        atencao = new AlertDialog.Builder(Login.this);
        atencao.setCancelable(false);
        atencao.setTitle("Atençao!");
        atencao.setMessage(mensagem);
        atencao.setPositiveButton("OK", null);
        atencao.create().show();
    }

    private void alerta(String msg) {
        Toast.makeText(Login.this, msg,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }
}
