package com.example.demoexam2024;


import lombok.*;
import java.sql.Date;

public class Requests {

    @Getter
    private int requestID;

    @Getter
    private Date startDate;

    @Getter @Setter
    private String orgTechType;

    @Getter @Setter
    private String orgTechModel;

    @Getter @Setter
    private String problemDescription;

    @Getter @Setter
    private String requestStatus;

    @Getter @Setter
    private Date completionDate;

    @Getter @Setter
    private String repairParts;

    @Getter @Setter
    private int masterID;

    @Getter
    private int clientID;


    // Конструктор для первичного создания заявки
    public Requests(String orgTechType, String orgTechModel, String problemDescription, int masterID, int clientID){
        this.startDate = new Date(System.currentTimeMillis());
        this.orgTechType = orgTechType;
        this.orgTechModel = orgTechModel;
        this.problemDescription = problemDescription;
        this.requestStatus = "Создана";
        this.masterID = masterID;
        this.clientID = clientID;
    }

    // Конструктор для поиска или отображения информации о заявке
    public Requests(int requestID, Date startDate, String orgTechType, String orgTechModel,
                    String problemDescription, String requestStatus, Date completionDate,
                    String repairParts, int masterID, int clientID) {
        this.requestID = requestID;
        this.startDate = startDate;
        this.orgTechType = orgTechType;
        this.orgTechModel = orgTechModel;
        this.problemDescription = problemDescription;
        this.requestStatus = requestStatus;
        this.completionDate = completionDate;
        this.repairParts = repairParts;
        this.masterID = masterID;
        this.clientID = clientID;
    }

}