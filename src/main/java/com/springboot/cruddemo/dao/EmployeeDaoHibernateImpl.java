package com.springboot.cruddemo.dao;


import com.springboot.cruddemo.entity.Employee;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class EmployeeDaoHibernateImpl implements EmployeeDAO {
    // define field for entity manager
    private EntityManager entityManager;

    // set up constructor injection
    @Autowired // optional @Autowired
    public EmployeeDaoHibernateImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public List<Employee> findAll() {
        // get current hibernate session
        Session session = entityManager.unwrap(Session.class);

        // create query
        Query<Employee> query = session.createQuery("from Employee", Employee.class);

        // execute query and get result list
        List<Employee> employees = query.getResultList();

        // return results
        return employees;
    }

    @Override
    public Employee findById(int id) {
        // get current hibernate session
        Session session = entityManager.unwrap(Session.class);

        // get employee
        Employee employee = session.get(Employee.class, id);

        // return employee
        return employee;
    }

    @Override
    public void save(Employee employee) {
// get current hibernate session
        Session session = entityManager.unwrap(Session.class);

        // save employee
        session.saveOrUpdate(employee);
    }

    @Override
    public void deleteById(int id) {
        // get current hibernate session
        Session session = entityManager.unwrap(Session.class);

        // delete object with primary key
        Query query = session.createQuery("delete from Employee where id =: employeeId");
        query.setParameter("employeeId", id);
        query.executeUpdate();
    }
}
