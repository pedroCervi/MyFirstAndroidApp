package com.example.pc.avaapp;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Tab2 extends Fragment {

        public EditText nomeInput;
        public EditText mailInput;
        public EditText telefoneInput;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2, container, false);

        //BOTAO DE ENVIAR EMAIL
        nomeInput = (EditText) rootView.findViewById(R.id.nomeInformado);
        mailInput = (EditText) rootView.findViewById(R.id.emailInformado);
        telefoneInput = (EditText) rootView.findViewById(R.id.telefoneInformado);
        Button mailButton_bt;
        mailButton_bt = (Button)rootView.findViewById(R.id.mailButton);
        mailButton_bt.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v){

                String mailInformado = mailInput.getText().toString();
                Boolean condicaoEmail = isValidEmail(mailInformado);

                if(condicaoEmail) {
                    //usar app nativo para enviar email
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL  , new String[]{mailInformado});
                    i.putExtra(Intent.EXTRA_SUBJECT, "AvaApp");
                    i.putExtra(Intent.EXTRA_TEXT   , "lista de atividades");

                    try {
                        startActivity(Intent.createChooser(i, "Escolha seu cliente de email"));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getActivity(), "Não há cliente de email instalado.", Toast.LENGTH_SHORT).show();
                    }

                    nomeInput.setText("");
                    mailInput.setText("");
                    telefoneInput.setText("");
                } else{
                    Toast.makeText(getActivity(), "Email inválido", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return rootView;
    }

        //FUNCAO PARA VERIFICAR A STRING DO EMAIL INFORMADO
        public final static boolean isValidEmail(CharSequence mailInformado){
            if(mailInformado==null){
                return false;
            } else{
                return Patterns.EMAIL_ADDRESS.matcher(mailInformado).matches();
            }

        }

}
