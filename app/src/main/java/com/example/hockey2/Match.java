package com.example.hockey2;

public class Match {
    private int id;
    private String matchName;
    private String team1_name;
    private String team1_goal;
    private String team1_info;
    private String team2_name;
    private String team2_goal;
    private String team2_info;

    public Match() {
    }

    public Match(int id, String matchName, String team1_name, String team1_goal, String team1_info, String team2_name, String team2_goal, String team2_info) {
        this.id = id;
        this.matchName = matchName;
        this.team1_name = team1_name;
        this.team1_goal = team1_goal;
        this.team1_info = team1_info;
        this.team2_name = team2_name;
        this.team2_goal = team2_goal;
        this.team2_info = team2_info;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getTeam1_name() {
        return team1_name;
    }

    public void setTeam1_name(String team1_name) {
        this.team1_name = team1_name;
    }

    public String getTeam1_goal() {
        return team1_goal;
    }

    public void setTeam1_goal(String team1_goal) {
        this.team1_goal = team1_goal;
    }

    public String getTeam1_info() {
        return team1_info;
    }

    public void setTeam1_info(String team1_info) {
        this.team1_info = team1_info;
    }

    public String getTeam2_name() {
        return team2_name;
    }

    public void setTeam2_name(String team2_name) {
        this.team2_name = team2_name;
    }

    public String getTeam2_goal() {
        return team2_goal;
    }

    public void setTeam2_goal(String team2_goal) {
        this.team2_goal = team2_goal;
    }

    public String getTeam2_info() {
        return team2_info;
    }

    public void setTeam2_info(String team2_info) {
        this.team2_info = team2_info;
    }
}
