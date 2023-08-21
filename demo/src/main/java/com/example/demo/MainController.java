package com.example.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.SQLException;

@Controller
@RequestMapping("/format_database")
@RequiredArgsConstructor
public class MainController {


    @Autowired
    private final  MainService mainService;


    @GetMapping
    public String getMainPage(){
        return "format_database";
    }

    Object c = null;

    @GetMapping("/connect_to_db")
    @ResponseBody
    public Object connectToDB(@RequestParam(value = "username") String username,
                           @RequestParam(value = "password") String password,
                           @RequestParam(value = "ip_address") String ip,
                           @RequestParam(value = "databaseName") String databaseName,
                           @RequestParam(value = "schema") String schema){

        c = mainService.connectToDatabase(username, password, ip, databaseName, schema);
        if (c instanceof Connection) {
            return "ok";
        } else {
            return mainService.findInvalidFieldsOnPage((Exception) c);
        }

    }

    @PostMapping("/fill_db")
    @ResponseBody
    public String formatDB(@RequestParam(value = "username") String username,
                           @RequestParam(value = "password") String password,
                           @RequestParam(value = "ip_address") String ip,
                           @RequestParam(value = "databaseName") String databaseName,
                           @RequestParam(value = "schema") String schema) throws SQLException {

        mainService.backupPGSQL((Connection) c, username, password, ip, databaseName, schema);

        mainService.deletePayrolls((Connection) c);
        mainService.updateEmployees((Connection) c);
        mainService.updateDepartments((Connection) c);
        mainService.updatePositions((Connection) c);
        mainService.updateCompanies((Connection) c);

        return "/format_database";
    }


}
