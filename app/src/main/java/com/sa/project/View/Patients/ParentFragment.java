package com.sa.project.View.Patients;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

/**
 * the parent fragment to reduce the redundant
 */
public abstract class ParentFragment extends Fragment {
    protected RecyclerView recyclerView;
    protected RecyclerView.LayoutManager layoutManager;
    protected PatientRecyclerAdapter adapter;
    protected ShareViewModel shareViewModel;

}
