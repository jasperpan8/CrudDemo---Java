package com.example.cruddemo.demo;

import com.example.cruddemo.entity.Department;
import com.example.cruddemo.entity.Department_Employee;
import com.example.cruddemo.entity.Employee;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.persistence.*;
import javax.sql.DataSource;
import java.util.Properties;

//import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
public class CrudDemo {

    private DataSource getMySQLDataSource(){
        // MySQL datasource
        final MysqlDataSource sqlDs = new MysqlDataSource();
        sqlDs.setUser("springstudent");
        sqlDs.setPassword("springstudent");
        sqlDs.setURL("jdbc:mysql://localhost:3306/company");
        return sqlDs;
    }

    private Properties getProperties(){
        final Properties properties = new Properties();
        properties.put("hibernate.dialect","org.hibernate.dialect.MySQL5Dialect");
        properties.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver"); // MySQL Driver
        return properties;
    }

    private EntityManagerFactory entityManagerFactory(DataSource dataSource, Properties properties){
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan("com/example/cruddemo/entity");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(properties);
        em.setPersistenceUnitName("Crud-Demo");
        em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        em.afterPropertiesSet();
        return em.getObject();
    }

    public static void main(String[] args) {
        CrudDemo demo = new CrudDemo();
        DataSource dataSource = demo.getMySQLDataSource();
        Properties properties = demo.getProperties();
        EntityManagerFactory entityManagerFactory = demo.entityManagerFactory(dataSource,properties);
        EntityManager em = entityManagerFactory.createEntityManager();
        PersistenceUnitUtil unitUtil = entityManagerFactory.getPersistenceUnitUtil();

//        insertEmployee(em);
        getEmployeeById(em); // Employee{id=1, name='Jasper', department_employees=[]}
//        addJunctionTable(em);
//        updateEmployee(em);
//        deleteEmployee(em);
    }

    // Create
    private static void insertEmployee(EntityManager em){
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Employee e = new Employee();
        e.setName("Jasper");
        em.persist(e); //cannot use setId
        tx.commit();
    }

    // Update Data from Employee
    private static void updateEmployee(EntityManager em){
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Employee e = new Employee();
        e.setName("Jason");
        e.setId(6);
        em.merge(e);
        tx.commit();
    }

    // Read Data from Employee
    private static void getEmployeeById(EntityManager em){
        // Need to Query as Employee instead of employee(employee is not mapped)
        // Test Query by name
//        Query query = em.createQuery("select e from Employee e left join fetch e.department_employees de where e.name like :custName");
//        query.setParameter("custName","Jasper");
        Query query = em.createQuery("select e from Employee e left join fetch e.department_employees de where e.id = ?1 ");

        query.setParameter(1,1);
        Employee e = (Employee)query.getSingleResult();
        System.out.println(e);
    }

    // Add data to Junction Table
    private static void addJunctionTable(EntityManager em){
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Employee e = new Employee();
        e.setName("Jack");
        Department d = new Department();

        em.persist(d);
        d.setDepartment_name("Engineer");

        Department_Employee de = new Department_Employee();
        de.setEmployee(e);
        de.setDepartment(d);
        d.addDepartmentEmployees(de);

        em.persist(e);
        tx.commit();
    }

    // Delete Employee
    private static void deleteEmployee(EntityManager em){
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Employee e = em.find(Employee.class,5);
        em.remove(e);
        tx.commit();
    }

}
