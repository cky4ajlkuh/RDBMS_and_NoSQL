import crud.Helper;
import crud.OriflameAgent;

public class Main {
    public static void main(String[] args) {
        Helper.selectNods();
        Helper.selectRelations();
        Helper.selectRoot();
        Helper.selectByCity("Samara");
        //Helper.selectNods();
        //agent.updateOriflameAgent("Kolya", agent.getEmail(), agent.getCity(), agent.getAppointment());
        //Helper.selectNods();
        //agent.createRelationOn("Michail");
        /*
        agent.deleteRelationFrom("Olya");
        agent.deleteRelationFrom("");
        agent.deleteOriflameAgent();


        */
    }
}
