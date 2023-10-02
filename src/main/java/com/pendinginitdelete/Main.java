package com.pendinginitdelete;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.pendinginitdelete.interfaces.Requests;
import feign.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Main {

    private static final String bearer = ""; // Bearer token
    private static final String clientId = ""; // Key
    private static final Logger logger = LogManager.getLogger(Main.class);
    private static final String url = "";
    private static final String csvFilePath = ""; //File with applicantId


    public static void main(String[] args)  {
            logger.info("Script starts with file " + csvFilePath);

            try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
                List<String[]> lines = reader.readAll();

                for (String[] line : lines) {
                    if (line.length > 0) {
                        String applicantId = line[0];
                        applicantMoveToPending(applicantId); //Move to pending
                        //applicantMoveToInit(applicantId); //Move to init
                        //deleteApplicant(applicantId); //Delete applicant
                    }
                }
            } catch (IOException | CsvException e) {
                e.printStackTrace();
            }
    }

    public static Response applicantMoveToPending(String applicantId) {
        Requests s = (Requests)(new FeignClient()).getClient(Requests.class, url);
        Response response = s.applicantMoveToPending(applicantId, bearer, clientId);
        if (response.status() == 200)
            logger.info("Applicant send to pending: " + applicantId);
        else
            logger.error("Send to pending failed: " + applicantId + " " + response.status() + " "+ response.reason());
        return response;
    }

    public static Response applicantMoveToInit(String applicantId) {
        Requests s = (Requests)(new FeignClient()).getClient(Requests.class, url);
        Response response = s.applicantMoveToInit(applicantId, bearer, clientId);
        if (response.status() == 200)
            logger.info("Applicant send to init: " + applicantId);
        else
            logger.error("Send to init failed: " + applicantId + " " + response.status() + " " + response.reason());
        return response;
    }

    public static Response deleteApplicant(String applicantId) {
        Requests s = (Requests)(new FeignClient()).getClient(Requests.class, url);
        Response response = s.deleteApplicant(applicantId, bearer, clientId);
        if (response.status() == 200)
            logger.info("Delete applicant: " + applicantId);
        else
            logger.error("Delete failed: " + applicantId + " " + response.status() + " " + response.reason());
        return response;
    }
}