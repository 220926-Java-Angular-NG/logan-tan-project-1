package com.revature.Utils;

public enum RType{
    ENTERTAINMENT("ENT",.25),
    WORK("WOR",1),
    EDUCATION("EDU",.80),
    TRAVEL("TRA",.75),
    MEDICAL("MED",.9),
    LEGAL("LEG",.1),
    MANAGEMENT("MAN",.3),
    BONUS("BON",1),
    ECT("ECT",1);
    private final String code;
    private final double percentage;
    RType(String code, double percentage){
        this.code = code;
        this.percentage = percentage;
    }
    public String getCode(){return code;}
    public double getPercentage(){return percentage;}
    public static RType getType(String code){
        switch (code) {
            case "ENT":
                return ENTERTAINMENT;
            case "WOR":
                return WORK;
            case "EDU":
                return EDUCATION;
            case "TRA":
                return TRAVEL;
            case "MED":
                return MEDICAL;
            case "LEG":
                return LEGAL;
            case "MAN":
                return MANAGEMENT;
            case "BON":
                return BONUS;
            default:
                return ECT;
        }
    }

}
