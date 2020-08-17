package com.example.testapplication4;

import androidx.appcompat.app.AlertDialog;
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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.testapplication4.databinding.EventDialogBinding;
import com.example.testapplication4.databinding.EventFragmentBinding;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import java.security.acl.Owner;

public class EventFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "eventfragment";

    MyViewModel vm;
    EventFragmentBinding mBinding;

    ObservableList<People> chosenPeopleList;
    People owner;

    public static EventFragment newInstance() {
        return new EventFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(getActivity()).get(MyViewModel.class);
        mBinding = DataBindingUtil.inflate(inflater, R.layout.event_fragment, container, false);
        RecyclerView recyclerView = mBinding.eventListView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        refreshList();


        QMUITopBar topBar = mBinding.eventTopBar;
        topBar.setTitle("PeopleList");
        topBar.addLeftTextButton("Add Event", R.integer.add_event_button_id).setOnClickListener(this);
        topBar.addRightTextButton("Delete Event", R.integer.delete_event_button_id).setOnClickListener(this);

        return mBinding.getRoot();
    }


    public void refreshList(){
        ObservableList<Event> eventList = vm.getEventList();
        EventBindingAdapter eventBindingAdapter = new EventBindingAdapter(getContext(), eventList);
        mBinding.eventListView.setAdapter(eventBindingAdapter);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.integer.add_event_button_id:
                final EventDialogBinding dialogBinding =
                        DataBindingUtil.inflate(LayoutInflater.from(getActivity()), R.layout.event_dialog, null, false);
                dialogBinding.chooseOwnerButton.setOnClickListener(this);
                dialogBinding.chooseParticipentButton.setOnClickListener(this);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("增加联系人:");
                builder.setView(dialogBinding.getRoot());
                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int i) {
                        Event event = new Event(dialogBinding.eventName.getText().toString(),
                                owner, Float.parseFloat(dialogBinding.feeText.getText().toString()));
                        vm.addEvent(event);
                        refreshList();
                        vm.getPeopleList().get(vm.getPeopleList().indexOf(owner)).addOutput(event.getFee());
                        for(People person: chosenPeopleList) {
                            vm.getPeopleList().get(vm.getPeopleList().indexOf(person)).addPredict(event.getFee()/chosenPeopleList.size());
                        }
                    }
                });
                builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int i) {

                    }
                });
                builder.show();// 显示对话框
                break;
            case R.integer.delete_event_button_id:
                break;
            case R.id.choose_owner_button:
                if (vm.getPeopleList().size()==0) {
                    Toast.makeText(getContext(), "No People Here", Toast.LENGTH_LONG).show();
                    break;
                }
                String[] stringList = new String[vm.getPeopleList().size()];
                for (int i=0;i<vm.getPeopleList().size();i++) {
                    stringList[i] = vm.getPeopleList().get(i).getName();
                }
                final QMUIDialog.CheckableDialogBuilder builder2 = new QMUIDialog.CheckableDialogBuilder(getActivity());
                builder2.setTitle("请选择")
                        .addItems(stringList, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                builder2.setCheckedIndex(which);
                            }
                        })
                        .addAction("确定", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                owner = vm.getPeopleList().get(builder2.getCheckedIndex());
                                dialog.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.choose_participent_button:
                if (vm.getPeopleList().size()==0) {
                    Toast.makeText(getContext(), "No People Here", Toast.LENGTH_LONG).show();
                    break;
                }
                String[] stringList2 = new String[vm.getPeopleList().size()];
                for (int i=0;i<vm.getPeopleList().size();i++) {
                    stringList2[i] = vm.getPeopleList().get(i).getName();
                }
                final QMUIDialog.MultiCheckableDialogBuilder builder3 = new QMUIDialog.MultiCheckableDialogBuilder(getActivity());
                builder3.setTitle("请选择")
                        .addItems(stringList2, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .addAction("确定", new QMUIDialogAction.ActionListener() {
                            @Override
                            public void onClick(QMUIDialog dialog, int index) {
                                chosenPeopleList = new ObservableArrayList<>();
                                for (int i: builder3.getCheckedItemIndexes()) {
                                    chosenPeopleList.add(vm.getPeopleList().get(i));
                                }
                                dialog.dismiss();
                            }
                        })
                        .show();
        }
    }
}