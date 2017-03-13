package fr.iut.persistence.dao;

import fr.iut.persistence.entities.Client;
import fr.iut.persistence.entities.Reservation;
import fr.iut.persistence.entities.Spot;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * Created by Sydpy on 3/9/17.
 */
public class DummyData {


    public static void main(String[] args) {

        HibernateUtil.openSession(HibernateUtil.Config.PROD);

        //Crete and persist your dummy datas for test here
        GenericDAO<Spot, Integer> daoSpot = new GenericDAO<>(Spot.class);
        daoSpot.removeAll();
        for (int i = 0; i < 50; i++) {
            Spot spot = new Spot();
            spot.setName("Emplacement " + i);
            spot.setCapacity(2);
            spot.setPointX((double) i);
            spot.setPointY((double) i);
            daoSpot.saveOrUpdate(spot);
        }
        ArrayList<Spot> spots = new ArrayList<>();
        spots.addAll(daoSpot.findAll());
        GenericDAO<Client, Integer> daoClient = new GenericDAO<>(Client.class);
        for (int i = 0; i < 50; i++) {
            Client client = new Client();
            client.setFirstname("pre " + i);
            client.setLastname("nom" + i);
            client.setPhone("06000000" + i);
            client.setEmail("reservation" + i + "@camping.oklm");
            daoClient.saveOrUpdate(client);
        }
        ArrayList<Client> clients = new ArrayList<>();
        clients.addAll(daoClient.findAll());
        GenericDAO<Reservation, Integer> dao = new GenericDAO<>(Reservation.class);
        for (int i = 0; i < 50; i++) {
            Reservation reservation = new Reservation();
            reservation.setSpot(spots.get(i));
            reservation.setClient(clients.get(i));
            reservation.setStarttime(new Timestamp(System.currentTimeMillis()));
            reservation.setEndtime(new Timestamp(System.currentTimeMillis() + 1));
            reservation.setPersonCount(1);
            dao.saveOrUpdate(reservation);
        }

    }
}
