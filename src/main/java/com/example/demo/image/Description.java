package com.example.demo.image;

public enum Description {
    //4veya5se verilebilir
    LOVELY("Lovely"),
    SWEET("Sweet"),
    SEXY("Sexy"),
    PRETTY("Pretty"),
    COOL("Cool"),

    //3se verilebilir
    NEUTRAL("Neutral"),

    //1 veya 2yse verilebilir
    UGLY("Ugly"),
    POOR("Poor"),
    BAD_PHYSIC("Bad Physic");

    private final String notifier;

    private Description(String notifier){
        this.notifier = notifier;
    }

    public String getNotifier(){
        return this.notifier;
    }
}
