package com.dranser.quizchatia;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class CategoriaFragment extends Fragment {

    View myFragment;

    public static CategoriaFragment newInstance(){
        CategoriaFragment categoriaFragment = new CategoriaFragment();
        return categoriaFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myFragment = inflater.inflate(R.layout.fragment_categoria,container,false);
        return myFragment;
    }
}
