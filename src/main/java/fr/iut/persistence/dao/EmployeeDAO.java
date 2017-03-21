package fr.iut.persistence.dao;

import fr.iut.persistence.entities.Authorization;
import fr.iut.persistence.entities.Employee;
import fr.iut.persistence.exception.InvalidLoginPasswordException;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Sydpy on 2/28/17.
 */
public class EmployeeDAO extends GenericDAO<Employee, Integer> {

    /**
     * Connected user application wide.
     */
    private static Employee connectedUser = null;

    /**
     * Constructor.
     */
    public EmployeeDAO() {
        super(Employee.class);
    }

    /**
     * Find an Employee who is a user by is login.
     * @param login Login of the Employee.
     * @return The Employee with the matching login.
     */
    @Transactional(rollbackOn = Exception.class)
    public Employee findByLogin(String login) {

        String hql = "from Employee where login = :login";
        Query query = session.createQuery(hql);
        query.setParameter("login", login);

        try {
            return (Employee) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Find an Employee by his first name.
     * @param firstName First name of the Employee.
     * @return The Employee with the matching first name.
     */
    public List<Employee> findByFirstName(String firstName) {
        String hql = "from Employee where firstName = :firstName";
        Query query = session.createQuery(hql);
        query.setParameter("firstName", firstName);

        return query.getResultList();
    }

    /**
     * Find an Employee by his last name.
     * @param lastName Last name of the Employee.
     * @return The Employee with the matching last name.
     */
    public List<Employee> findByLastName(String lastName) {
        String hql = "from Employee where lastName = :lastName";
        Query query = session.createQuery(hql);
        query.setParameter("lastName", lastName);

        return query.getResultList();
    }

    /**
     * Update the current connected user.
     * @param login
     * @param password
     * @return The connected Employee.
     * @throws InvalidLoginPasswordException If the login/password is invalid.
     */
    @Transactional(rollbackOn = Exception.class)
    public Employee connectUser(String login, String password) throws InvalidLoginPasswordException {

        Employee byLogin = findByLogin(login);

        if (byLogin != null && byLogin.getPassword().equals(password)) {
            connectedUser = byLogin;
            return connectedUser;
        } else {
            connectedUser = null;
            throw new InvalidLoginPasswordException();
        }
    }

    /**
     * @param authorization Authorization the Employee has to be granted
     * @return the set of Employees which has the authorization provided in first arg
     */
    public Set<Employee> findAllWithAuthorization(Authorization authorization){

        Set<Employee> withAuthorization = new HashSet<>();

        List<Employee> all = findAll();

        for (Employee employee : all) {
            if(employee.getAuthorizations().contains(authorization)) {
                withAuthorization.add(employee);
            }
        }

        return withAuthorization;
    }

    /**
     * Disconnects the current connected user.
     */
    public void disconnectUser() {
        connectedUser = null;
    }

    /**
     * @return the current connected Employee.
     */
    public static Employee getConnectedUser() {
        return connectedUser;
    }
}
