package com.example.testapplication4;

import android.view.View;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MyViewModel extends ViewModel{
    // TODO: Implement the ViewModel
    private ObservableList<People> peopleList;
    private ObservableList<Event> eventList;

    public MyViewModel(){
        peopleList = new ObservableArrayList<People>();
        eventList = new ObservableArrayList<Event>();
    }

    public void addPeople(People people){
        peopleList.add(people);
    }

    public void deletePeople(int index){
        peopleList.remove(index);
    }

    public void deletePeople(List<People> delList){
        peopleList.removeAll(delList);
    }

    public void addEvent(Event event){
        eventList.add(event);
    }

    public void deleteEvent(int index){
        eventList.remove(index);
    }

    public void deleteEvent(List<Event> delList){
        eventList.removeAll(delList);
    }




}