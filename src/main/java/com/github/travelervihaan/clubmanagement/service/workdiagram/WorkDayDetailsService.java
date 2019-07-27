package com.github.travelervihaan.clubmanagement.service.workdiagram;

import com.github.travelervihaan.clubmanagement.model.workdiagram.WorkDay;
import com.github.travelervihaan.clubmanagement.model.workdiagram.WorkDayImportance;
import com.github.travelervihaan.clubmanagement.repository.workdiagram.WorkDayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkDayDetailsService {

    private WorkDayRepository workDayRepository;

    @Autowired
    public WorkDayDetailsService(WorkDayRepository workDayRepository){
        this.workDayRepository = workDayRepository;
    }

    void saveImportanceLevel(WorkDay workDay, WorkDayImportance workDayImportance){
        workDay.setWorkDayImportance(workDayImportance);
        while(workDay.getEmployers().size() > workDayImportance.getImportanceLevel()){
            workDay.getEmployers().remove(workDay.getEmployers().size()-1);
        }
        workDayRepository.save(workDay);
    }

    void saveBookedArtist(WorkDay workDay, String artist){
        if(artist != null || !artist.equals("")) {
            workDay.setBookedArtist(artist);
            workDayRepository.save(workDay);
        }
    }

    void saveWorkTime(WorkDay workDay, int workTime){
        if(workTime > 0 && workTime < 24){
            workDay.setWorkingTime(workTime);
            workDayRepository.save(workDay);
        }
    }
}
