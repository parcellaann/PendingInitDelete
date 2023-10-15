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

/*
Use this script for one of three purposes:
1. Move to pending
2. Move to init.
3. Delete applicants.

The input csv file contains applicantIDs.
Depending on the selected purpose, uncomment the appropriate line in the main function.
Fill in the following parameters: BEARER, CLIENT_ID, URL, CSV_FILE_PATH.
After that, the script is ready for use.

*/
public class Main {

    private static final String BEARER = ""; // Bearer token
    private static final String CLIENT_ID = ""; // Key
    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    private static final String URL = "";
    private static final String CSV_FILE_PATH = ""; //File with applicantId


    public static void main(String[] args)  {
        LOGGER.info("Script starts with file " + CSV_FILE_PATH);

            try (CSVReader reader = new CSVReader(new FileReader(CSV_FILE_PATH))) {
                List<String[]> lines = reader.readAll();

                for (String[] line : lines) {
                    if (line.length > 0) {
                        String applicantId = line[0];
                        //applicantMoveToPending(applicantId); //Move to pending
                        //applicantMoveToInit(applicantId); //Move to init
                        //deleteApplicant(applicantId); //Delete applicant
                    }
                }
            } catch (IOException | CsvException e) {
                e.printStackTrace();
            }
    }

    public static Response applicantMoveToPending(String applicantId) {
        Requests s = (Requests)(new FeignClient()).getClient(Requests.class, URL);
        Response response = s.applicantMoveToPending(applicantId, BEARER, CLIENT_ID);
        if (response.status() == 200)
            LOGGER.info("Applicant send to pending: " + applicantId);
        else
            LOGGER.error("Send to pending failed: " + applicantId + " " + response.status() + " "+ response.reason());
        return response;
    }

    public static Response applicantMoveToInit(String applicantId) {
        Requests s = (Requests)(new FeignClient()).getClient(Requests.class, URL);
        Response response = s.applicantMoveToInit(applicantId, BEARER, CLIENT_ID);
        if (response.status() == 200)
            LOGGER.info("Applicant send to init: " + applicantId);
        else
            LOGGER.error("Send to init failed: " + applicantId + " " + response.status() + " " + response.reason());
        return response;
    }

    public static Response deleteApplicant(String applicantId) {
        applicantMoveToInit(applicantId);
        Requests s = (Requests)(new FeignClient()).getClient(Requests.class, URL);
        Response response = s.deleteApplicant(applicantId, BEARER, CLIENT_ID);
        if (response.status() == 200)
            LOGGER.info("Delete applicant: " + applicantId);
        else
            LOGGER.error("Delete failed: " + applicantId + " " + response.status() + " " + response.reason());
        return response;
    }
}