package com.example.testapplication4;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.example.testapplication4.databinding.PeopleFragmentBinding;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import java.util.ArrayList;
import java.util.List;

public class PeopleFragment extends Fragment implements View.OnClickListener{
    public static final String TAG = "peoplefragment";


    private MyViewModel vm;
    private PeopleFragmentBinding mBinding;

    public static PeopleFragment newInstance() {
        return new PeopleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(getActivity()).get(MyViewModel.class);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.people_fragment, container, false);
        RecyclerView recyclerView = mBinding.peopleListView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        refreshList();

        Log.d(TAG, String.valueOf(recyclerView.findViewHolderForAdapterPosition(0)));

        QMUITopBar topBar = mBinding.peopleTopBar;
        topBar.setTitle("PeopleList");
        topBar.addLeftTextButton("Add People", R.integer.add_people_button_id).setOnClickListener(this);
        topBar.addRightTextButton("Delete People", R.integer.delete_people_button_id).setOnClickListener(this);

        return mBinding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.integer.add_people_button_id:
                final QMUIDialog.EditTextDialogBuilder builder0 = new QMUIDialog.EditTextDialogBuilder(getActivity());
                builder0.setTitle("Add People")
                        .setPlaceholder("Your name")
                        .setInputType(InputType.TYPE_CLASS_TEXT)
                        .addAction("cancel", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                dialog.dismiss();
                            }
                        })
                        .addAction("yes", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                String text = builder0.getEditText().getText().toString();
                                if (text != null && text.length() > 0) {
                                    vm.addPeople(new People(text));
                                    refreshList();
                                    dialog.dismiss();
                                } else {
                                    Toast.makeText(getActivity(), "Please Enter Your Name", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .show();
                break;
            case R.integer.delete_people_button_id:
                if (vm.getPeopleList().size()==0) {
                    Toast.makeText(getContext(), "No People Here", Toast.LENGTH_LONG).show();
                    break;
                }
                String[] stringList = new String[vm.getPeopleList().size()];
                for (int i=0;i<vm.getPeopleList().size();i++) {
                    stringList[i] = vm.getPeopleList().get(i).getName();
                }
                final QMUIDialog.MultiCheckableDialogBuilder builder1 = new QMUIDialog.MultiCheckableDialogBuilder(getActivity())
                        .addItems(stringList, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });;
                builder1.addAction("cancel", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                });
                builder1.addAction("yes", new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        int[] indexs = builder1.getCheckedItemIndexes();
                        List<People> checkedPeople = new ArrayList<People>();
                        for (int i:indexs) {
                            checkedPeople.add(vm.getPeopleList().get(i));
                        }
                        vm.deletePeople(checkedPeople);
                        refreshList();
                        dialog.dismiss();
                    }
                });
                builder1.show();
                break;
        }
    }

    public void refreshList(){
        ObservableList<People> peopleList = vm.getPeopleList();
        PeopleBindingAdapter peopleBindingAdapter = new PeopleBindingAdapter(getContext(), peopleList);
        mBinding.peopleListView.setAdapter(peopleBindingAdapter);
    }


}