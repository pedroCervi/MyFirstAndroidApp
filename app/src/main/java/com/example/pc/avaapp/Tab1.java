package com.example.pc.avaapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tab1 extends Fragment{

    //VARIAVEIS DE COMUNICACAO COM O XML
    private Button botaoDialogXML;
    private ListView listaDeAtividadesXML;

    //VARIAVEIS INTERNAS
    private List<HashMap<String,String>> listaDeItems;
    private SimpleAdapter adaptadorListaArray;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.tab1, container, false);


        //CRIAÇÃO DOS OBJETOS E CASTS DE ASSOCIACAO COM O XML
        botaoDialogXML = (Button) rootView.findViewById(R.id.dialogButton);
        listaDeAtividadesXML = (ListView) rootView.findViewById(R.id.listaDeAtividades);
        listaDeItems = new ArrayList<>();
        adaptadorListaArray = new SimpleAdapter(getActivity(), listaDeItems, R.layout.list_item,
                new String[]{"CampoTitulo", "CampoDescricao"},
                new int[]{R.id.textView, R.id.textView2});
        listaDeAtividadesXML.setAdapter(adaptadorListaArray);

        inclusaoInicialDeItems();

        //BOTAO ADICIONAR
        botaoDialogXML.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
                LinearLayout dialogLayout = new LinearLayout(getActivity());
                dialogLayout.setOrientation(LinearLayout.VERTICAL);

                final EditText dialogTitleInput = new EditText(getActivity());
                dialogTitleInput.setHint("Título");
                dialogLayout.addView(dialogTitleInput);

                final EditText dialogDescriptionInput = new EditText(getActivity());
                dialogDescriptionInput.setHint("Descrição");
                dialogDescriptionInput.setMinLines(4);
                dialogDescriptionInput.setGravity(Gravity.TOP);
                dialogDescriptionInput.setGravity(Gravity.LEFT);
                dialogLayout.addView(dialogDescriptionInput);

                alertBuilder.setTitle("Insira a nova atividade");
                alertBuilder.setView(dialogLayout);

                alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String novoTituloDialog = dialogTitleInput.getText().toString();
                        String novaDescricaoDialog = dialogDescriptionInput.getText().toString();

                        if (novoTituloDialog.length()>0 && novaDescricaoDialog.length()>0){
                            listaDeItems = popularLista(novoTituloDialog, novaDescricaoDialog);
                            Toast.makeText(getActivity(), "Atividade adicionada", Toast.LENGTH_SHORT).show();
                            adaptadorListaArray.notifyDataSetChanged();
                        } else{
                            Toast.makeText(getActivity(), "Atividade não adicionada: algum campo está vazio", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                alertBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alert = alertBuilder.create();
                alert.show();

            }
        });

        //DELETAR ITEM AO USAR LONG PRESS
        listaDeAtividadesXML.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
                LinearLayout dialogLayout = new LinearLayout(getActivity());
                dialogLayout.setOrientation(LinearLayout.VERTICAL);

                alertBuilder.setTitle("Deletar item?");
                alertBuilder.setView(dialogLayout);

                alertBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deletarItem(position);
                        Toast.makeText(getActivity(), "Atividade deletada", Toast.LENGTH_SHORT).show();
                        adaptadorListaArray.notifyDataSetChanged();

                    }
                });

                alertBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                AlertDialog alert = alertBuilder.create();
                alert.show();

                return true;
            }
        });


        //ABERTURA DE NOVA ATIVIDADE VIA ESCOLHA DE ITEM
        listaDeAtividadesXML.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            private SimpleAdapter getListAdapter() {
                return adaptadorListaArray;
            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){

                Intent openItemIntent = new Intent(getActivity(), ItemActivity.class);
                Map<String,String> map = (Map<String,String>)this.getListAdapter().getItem(position);
                String title = map.get("CampoTitulo");
                String description = map.get("CampoDescricao");
                openItemIntent.putExtra("titulo", title);
                openItemIntent.putExtra("descricao", description);
                startActivity(openItemIntent);

            }
        });

        return rootView;
    }


    //FUNCOES AUXILIARES
    public List deletarItem(int position){
        listaDeItems.remove(position);
        return listaDeItems;
    }

    public List popularLista(String t, String d){
        HashMap<String, String> itemNovo = new HashMap<>();
        itemNovo.put("CampoTitulo", t.toString());
        itemNovo.put("CampoDescricao", d.toString());
        //listaDeItems.add(itemNovo);
        listaDeItems.add(0, itemNovo);
        return listaDeItems;
    }

    void inclusaoInicialDeItems(){
        popularLista("Atividade 1", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostru.");
        popularLista("Atividade 2", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostru.");
        popularLista("Atividade 3", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostru.");
        popularLista("Atividade 4", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostru.");
        popularLista("Atividade 5", "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostru.");
    }

}

/*

 */