package com.example.demo;

import com.github.javafaker.Faker;
import com.github.javafaker.File;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MainService {


    public void updateEmployees(Connection c) throws SQLException {
        Statement stmt_employees = c.createStatement();

        ResultSet rs_employees = stmt_employees.executeQuery( "SELECT * FROM employee e" );
        Faker faker = new Faker(new Locale("ru"));

        while (rs_employees.next()){
            long employee_id = rs_employees.getLong ("employee_id");
            String sex = rs_employees.getString ("sex");
            String mask = faker.phoneNumber().home_phone_mask();

            String query_employees;
            if (sex.equals("М")){
                query_employees = "UPDATE employee set first_name = '" + faker.name().first_name_male() + "' , " +
                        "last_name = '" + faker.name().last_name_male() +  "', " +
                        "patronymic = '" + faker.name().middle_name_male() + "', " +
                        "date_of_birth = '" + faker.date().birthday(25, 63) + "', " +
                        "home_address_city = '" + faker.address().city() + "', " +
                        "home_address_street = '" + faker.address().streetName() + "', " +
                        "home_address_house = '" + faker.address().buildingNumber() + "', " +
                        "home_address_house_ext = '" + faker.address().house_ext() + "', " +
                        "home_address_apartment = case when ('" + faker.address().secondaryAddress() + "' = '00' or '" + faker.address().secondaryAddress() + "' = '000') then '-' else '" + faker.address().secondaryAddress() + "' end, " +
                        "home_phone = '" + mask + faker.phoneNumber().home_phone_number() + "', " +
                        "work_phone = '" + mask + faker.phoneNumber().home_phone_number() + "', " +
                        "personal_phone = '" + faker.phoneNumber().phoneNumber() + "', " +
                        "office = '" + faker.address().secondaryAddress() + "', " +
                        "start_order_number = '" + faker.address().secondaryAddress() + "', " +
                        "photo = null " +
                        "where employee_id = '" + employee_id + "'";
            } else {
                query_employees = "UPDATE employee set first_name = '" + faker.name().first_name_female() + "' , " +
                        "last_name = '" + faker.name().last_name_female() +  "', " +
                        "patronymic = '" + faker.name().middle_name_female() + "', " +
                        "date_of_birth = '" + faker.date().birthday(25, 63) + "', " +
                        "home_address_city = '" + faker.address().city() + "', " +
                        "home_address_street = '" + faker.address().streetName() + "', " +
                        "home_address_house = '" + faker.address().buildingNumber() + "', " +
                        "home_address_house_ext = '" + faker.address().house_ext() + "', " +
                        "home_address_apartment = case when ('" + faker.address().secondaryAddress() + "' = '00' or '" + faker.address().secondaryAddress() + "' = '000') then '-' else '" + faker.address().secondaryAddress() + "' end, " +
                        "home_phone = '" + mask + faker.phoneNumber().home_phone_number() + "', " +
                        "work_phone = '" + mask + faker.phoneNumber().home_phone_number() + "', " +
                        "personal_phone = '" + faker.phoneNumber().phoneNumber() + "', " +
                        "office = '" + faker.address().secondaryAddress() + "', " +
                        "start_order_number = '" + faker.address().secondaryAddress() + "', " +
                        "photo = null " +
                        "where employee_id = '" + employee_id +   "'";
            }
            Statement stmt_employees_query = c.createStatement();

            stmt_employees_query.executeUpdate(query_employees);
        }
    }
    public void updateCompanies(Connection c) throws SQLException{
        Statement stmt_companies = c.createStatement();
        ResultSet rs_companies = stmt_companies.executeQuery( "SELECT * FROM company ");

        Faker faker = new Faker(new Locale("ru"));
        Random r = new Random();
        String alphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";

        while (rs_companies.next()){
            long company_id = rs_companies.getLong ("company_id");
            String name; String query_companies;
            Statement stmt_check_company = c.createStatement();

            if (rs_companies.getString("company_name").toLowerCase().contains("финансовый отдел")) {
                name = "Финансовый отдел " + alphabet.charAt(r.nextInt(alphabet.length())) + faker.address().buildingNumber();
                while (stmt_check_company.executeQuery("SELECT * FROM company  where  company_name = '" + name + "'").next()) {
                    name = "Финансовый отдел " + alphabet.charAt(r.nextInt(alphabet.length())) + faker.address().buildingNumber();
                }
            } else if (rs_companies.getString("company_name").contains("Финансовое управление")) {
                name = "Финансовое управление " + alphabet.charAt(r.nextInt(alphabet.length())) + faker.address().buildingNumber();
                while (stmt_check_company.executeQuery("SELECT * FROM company  where  company_name = '" + name + "'").next()) {
                    name = "Финансовое управление " + alphabet.charAt(r.nextInt(alphabet.length())) + faker.address().buildingNumber();
                }
            } else if (rs_companies.getString("company_name").contains("Главное финансовое управление")) {
                name = "Главное финансовое управление " + alphabet.charAt(r.nextInt(alphabet.length())) + faker.address().buildingNumber();
                while (stmt_check_company.executeQuery("SELECT * FROM company  where  company_name = '" + name + "'").next()) {
                    name = "Главное финансовое управление " + alphabet.charAt(r.nextInt(alphabet.length())) + faker.address().buildingNumber();
                }
            } else if (rs_companies.getString("company_name").contains("Главное управление")) {
                name = "Главное управление " + alphabet.charAt(r.nextInt(alphabet.length())) + faker.address().buildingNumber();
                while (stmt_check_company.executeQuery("SELECT * FROM company  where  company_name = '" + name + "'").next()) {
                    name = "Главное управление " + alphabet.charAt(r.nextInt(alphabet.length())) + faker.address().buildingNumber();
                }
            } else {
                name = "Организация " + alphabet.charAt(r.nextInt(alphabet.length())) + faker.address().buildingNumber();
                while (stmt_check_company.executeQuery("SELECT * FROM company  where  company_name = '" + name + "'").next()) {
                    name = "Организация " + alphabet.charAt(r.nextInt(alphabet.length())) + faker.address().buildingNumber();
                }
            }
            query_companies = "UPDATE company set company_name = '" + name + "' where company_id = '" + company_id + "'";

            Statement stmt_company_query = c.createStatement();
            stmt_company_query.executeUpdate(query_companies);
        }
    }
    public void updatePositions(Connection c) throws SQLException{
        Statement stmt_positions = c.createStatement();
        ResultSet rs_positions = stmt_positions.executeQuery( "SELECT * FROM positions p" );

        Faker faker = new Faker(new Locale("ru"));
        Random r = new Random();
        String alphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";

        while (rs_positions.next()){
            long position_id = rs_positions.getLong ("position_id");
            long company_id = rs_positions.getLong ("company_id");
            Statement stmt_check_position = c.createStatement();

            String name = "Должность " + alphabet.charAt(r.nextInt(alphabet.length())) + faker.address().buildingNumber();
            while (stmt_check_position.executeQuery("SELECT * FROM positions  where company_id = '" + company_id + "' and description = '" + name + "'").next()) {
                name = "Должность " + alphabet.charAt(r.nextInt(alphabet.length())) + faker.address().buildingNumber();
            }

            String query = "UPDATE positions SET description = '" + name + "' where position_id = '" +position_id +"'";
            Statement stmt_positions_query = c.createStatement();
            stmt_positions_query.executeUpdate(query);
        }

        Statement stmt_sub_positions = c.createStatement();
        ResultSet rs_sub_positions = stmt_sub_positions.executeQuery( "SELECT * FROM sub_position sp" );

        while (rs_sub_positions.next()){
            long sub_position_id = rs_sub_positions.getLong ("sub_position_id");
            Statement stmt_check_sub_position = c.createStatement();
            String name = "Должность " + alphabet.charAt(r.nextInt(alphabet.length())) + faker.address().buildingNumber();
            while (stmt_check_sub_position.executeQuery("SELECT * FROM positions  where description = '" + name + "'").next()) {
            name = "Должность " + alphabet.charAt(r.nextInt(alphabet.length())) + faker.address().buildingNumber();
            }

            String query = "UPDATE sub_position SET description = '" + name + "' where sub_position_id = '" + sub_position_id +"'";
            Statement stmt_sub_positions_query = c.createStatement();
            stmt_sub_positions_query.executeUpdate(query);
        }
    }
    public void updateDepartments(Connection c) throws SQLException {
        Statement stmt_companies = c.createStatement();
        ResultSet rs_companies = stmt_companies.executeQuery( "SELECT * FROM company c" );

        Faker faker = new Faker(new Locale("ru"));
        Random r = new Random();
        String alphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";

        while (rs_companies.next()){
            Statement stmt_departments = c.createStatement();

            long company_id = rs_companies.getLong ("company_id");

            ResultSet rs_departments = stmt_departments.executeQuery( "SELECT * FROM department d " ); //todo company_id
            while (rs_departments.next()){

                long department_id = rs_departments.getLong ("department_id");
                String name; String query_departments;
                Statement stmt_check_department = c.createStatement();

                if (rs_departments.getString("department_name").toLowerCase().contains("управление")) {
                    name = "Управление " + alphabet.charAt(r.nextInt(alphabet.length())) + faker.address().buildingNumber();
                    while (stmt_check_department.executeQuery("SELECT * FROM department  where company_id = '" + company_id + "' and department_name = '" + name + "'").next()) {
                        name = "Управление " + alphabet.charAt(r.nextInt(alphabet.length())) + faker.address().buildingNumber();
                    }
                } else if (rs_departments.getString("department_name").toLowerCase().contains("отдел")) {
                    name = "Отдел " + alphabet.charAt(r.nextInt(alphabet.length())) + faker.address().buildingNumber();
                    while (stmt_check_department.executeQuery("SELECT * FROM department  where company_id = '" + company_id + "' and department_name = '" + name + "'").next()) {
                        name = "Отдел " + alphabet.charAt(r.nextInt(alphabet.length())) + faker.address().buildingNumber();
                    }
                } else if (rs_departments.getString("department_name").toLowerCase().contains("сектор")) {
                    name = "Сектор " + alphabet.charAt(r.nextInt(alphabet.length())) + faker.address().buildingNumber();
                    while (stmt_check_department.executeQuery("SELECT * FROM department  where company_id = '" + company_id + "' and is_enabled = true and department_name = '" + name + "'").next()) {
                        name = "Сектор " + alphabet.charAt(r.nextInt(alphabet.length())) + faker.address().buildingNumber();
                    }
                } else if (rs_departments.getString("department_name").toLowerCase().contains("группа")){
                    name = "Группа " + alphabet.charAt(r.nextInt(alphabet.length())) + faker.address().buildingNumber();
                    while (stmt_check_department.executeQuery("SELECT * FROM department  where company_id = '" + company_id + "' and is_enabled = true and department_name = '" + name + "'").next()) {
                        name = "Группа " + alphabet.charAt(r.nextInt(alphabet.length())) + faker.address().buildingNumber();
                    }
                } else {
                    name = alphabet.charAt(r.nextInt(alphabet.length())) + faker.address().buildingNumber();
                    while (stmt_check_department.executeQuery("SELECT * FROM department  where company_id = '" + company_id + "' and is_enabled = true and department_name = '" + name + "'").next()) {
                        name = alphabet.charAt(r.nextInt(alphabet.length())) + faker.address().buildingNumber();
                    }
                }

                query_departments = "UPDATE department set department_name = '" + name + "', " +
                        "fax = '" + faker.phoneNumber().home_phone_number() + "', " +
                        "dep_code_kdr = '"+ faker.phoneNumber().dep_code_kdr() +"' where department_id = '" + department_id + "'";

                Statement stmt_departments_query = c.createStatement();
                stmt_departments_query.executeUpdate(query_departments);
            }

        }
    }
    public Object connectToDatabase(String username, String password, String ip, String databaseName, String schema) {
        String dbURL = "jdbc:postgresql://" + ip + "/" + databaseName + "?currentSchema=" + schema;
        Connection c;
        try {

            Class.forName("org.postgresql.Driver");
            c = DriverManager
                    .getConnection(dbURL, username, password);
            if (c.getSchema() == null){
                return new Exception("schema");
            }
        } catch (Exception e){
            e.printStackTrace();
            return e;
        }
        return c;
    }
    public String findInvalidFieldsOnPage(Exception exception){
        String[] allErrors = {"password authentication failed", "Ошибка при попытке подсоединения", "database", "schema"};

        String inputs = "error";
        for (String error : allErrors) {
            if (exception.getMessage().contains(error)){
                inputs = switch (error) {
                    case "password authentication failed" -> "username, password";
                    case "Ошибка при попытке подсоединения" -> "ip_address";
                    case "database" -> "databaseName";
                    case "schema" -> "schema";
                    default -> "error";
                };
            }
        }
        return inputs;




//        return "asd";
    }
    public void backupPGSQL(Connection c, String username, String password, String ip, String databaseName, String schema) {
        try{
            Runtime r =Runtime.getRuntime();
            String rutaCT = "D:\\PostgreSQL\\13\\bin\\"; // путь к дампу
            String backupFile = "D:\\backups\\" + databaseName + " ";
            Process p;
            ProcessBuilder pb;

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
            StringBuffer date = new StringBuffer();
            date.append(df.format(calendar.getTime()));
            java.io.File file = new java.io.File(rutaCT);
            if (file.exists()) {
                StringBuffer fechafile = new StringBuffer();
                fechafile.append(backupFile);
                fechafile.append(date.toString());
                fechafile.append(".backup");
                java.io.File ficherofile = new java.io.File(fechafile.toString());
                if (ficherofile.exists()) {
                    ficherofile.delete();
                }
                r = Runtime.getRuntime();
                pb = new ProcessBuilder(rutaCT + "pg_dump.exe", "-f", fechafile.toString(),
                        "-F", "c", "-Z", "9", "-v", "-O", "-h",ip, "-U", username, databaseName);
                pb.environment().put("PGPASSWORD", password);
                pb.redirectErrorStream(true);
                p = pb.start();
                try {
                    InputStream is = p.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    String ll;
                    while ((ll = br.readLine()) != null) {
                        System.out.println(ll);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch(IOException x) {
            System.err.println("Could not invoke browser, command=");
            System.err.println("Caught: " + x.getMessage());
        }
    }

    public void deletePayrolls(Connection c) throws SQLException{
        Statement stmt_employees_payrolls = c.createStatement();
        String query = "Delete from payroll";
        stmt_employees_payrolls.executeUpdate(query);
    }

}
