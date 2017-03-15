package fr.iut.controller;

import fr.iut.persistence.dao.GenericDAO;
import fr.iut.persistence.entities.Employee;
import fr.iut.view.EmployeeManagerView;
import javafx.scene.SubScene;

import java.util.ArrayList;

/**
 * Created by shellcode on 2/17/17.
 */
public class EmployeesController implements ControllerInterface {

    GenericDAO<Employee, Integer> daoEmployee = new GenericDAO<>(Employee.class);
    private HomeController homeController;
    private EmployeeManagerView employeeManagerView = new EmployeeManagerView(this);

    public EmployeesController(HomeController homeController) {
        this.homeController = homeController;
    }

    @Override
    public SubScene getView() {
        return employeeManagerView;
    }

    @Override
    public void finish() {

    }

    public ArrayList<Employee> getEmployees() {
        GenericDAO<Employee, Integer> dao = new GenericDAO<>(Employee.class);
        return new ArrayList<>(dao.findAll());
    }

    public void saveEmployee(Employee employee) {
        daoEmployee.save(employee);
    }
}
