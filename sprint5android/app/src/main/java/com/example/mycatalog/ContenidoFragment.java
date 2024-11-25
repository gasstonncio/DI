package com.example.mycatalog;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContenidoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContenidoFragment extends Fragment {

    public ContenidoFragment() {
        // Required empty public constructor
    }

    public static ContenidoFragment newInstance(String param1, String param2) {
        ContenidoFragment fragment = new ContenidoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contenido, container, false);
    }
}