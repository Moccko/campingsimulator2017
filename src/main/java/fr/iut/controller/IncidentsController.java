package fr.iut.controller;

import fr.iut.persistence.dao.GenericDAO;
import fr.iut.persistence.entities.Client;
import fr.iut.persistence.entities.Location;
import fr.iut.persistence.entities.Problem;
import fr.iut.persistence.entities.Spot;
import fr.iut.view.IncidentsManagerView;
import javafx.scene.SubScene;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class IncidentsController {

    /**
     * instance of the controller
     */
    private HomeController homeController;

    /**
     * local list of problems
     */
    private ArrayList<Problem> problems = new ArrayList<>();

    /**
     * DAO of Incidents
     */
    private GenericDAO<Problem, Integer> daoIncidents = new GenericDAO<>(Problem.class);

    IncidentsController(HomeController homeController) { this.homeController = homeController;}

    public SubScene getView() {
        return new IncidentsManagerView(this);
    }

    /**
     * get incidents of database and create in local
     */
    public void createIncidents(){
        problems = (ArrayList<Problem>) daoIncidents.findAll();
    }

    /**
     * @param p resolve an incident
     */
    public void resolveIncident(Problem p){
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        Timestamp currentTimestamp = new Timestamp(now.getTime());
        p.setSolutionDatetime(currentTimestamp);

        daoIncidents.update(p);
    }

    /**
     * @param p the problem
     * @param desc description of the incident
     * @param app string of the apparence timestamp of the incident
     * @param sol string of the solution timestamp of the incident
     * update the incident
     */
    public void updateIncident(Problem p, String desc, String app, String sol){
        p.setDescription(desc);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
        Date parsedDate;
        try {
            parsedDate = dateFormat.parse(app);
            Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
            p.setAppearanceDatetime(timestamp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(sol != null){
            try {
                parsedDate = dateFormat.parse(sol);
                Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
                p.setSolutionDatetime(timestamp);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        daoIncidents.update(p);
    }

    /**
     * @param sort_options selected sort method to sort the problem list
     */
    public void sortIncidents(int sort_options){
        problems.sort((o1, o2) -> {
            int result;
            switch (sort_options){
                case 1:
                    result = o2.getDescription().toLowerCase().compareTo(o1.getDescription().toLowerCase());
                    break;
                case 2:
                    result = o1.getAppearanceDatetime().compareTo(o2.getAppearanceDatetime());
                    break;
                case 3:
                    result = o2.getAppearanceDatetime().compareTo(o1.getAppearanceDatetime());
                    break;
                case 4:
                    result = o1.getSolutionDatetime().compareTo(o2.getSolutionDatetime());
                    break;
                case 5:
                    result = o2.getSolutionDatetime().compareTo(o1.getSolutionDatetime());
                    break;
                default:
                    result = o1.getDescription().toLowerCase().compareTo(o2.getDescription().toLowerCase());
                    break;
            }
            return result;
        });
    }

    /**
     * @param problem create the incident and save it
     */
    public void saveIncident(Problem problem){
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        Timestamp currentTimestamp = new Timestamp(now.getTime());
        problem.setAppearanceDatetime(currentTimestamp);

        daoIncidents.save(problem);
    }

    public ArrayList<Problem> getIncidents() {
        return problems;
    }

    public List<Client> getClients(){
        GenericDAO<Client, Integer> daoClients = new GenericDAO<>(Client.class);
        return daoClients.findAll();
    }

    public List<Location> getLocations(){
        GenericDAO<Location, Integer> daoLocations = new GenericDAO<>(Location.class);
        return daoLocations.findAll();
    }
}
