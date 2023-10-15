package com.pendinginitdelete.interfaces;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;

public interface Requests {

    @RequestLine("POST /resources/applicants/{applicantId}/status/pending")
    Response applicantMoveToPending(
            @Param("clientId") String clientId);

    @RequestLine("POST /resources/applicants/{applicantId}/review/status/init")
    Response applicantMoveToInit(
            @Param("applicantId") String applicantId);

    @RequestLine("DELETE /resources/applicants/{applicantId}")
    @Headers({"X-Rate-Limiter: no-limit"})
    Response deleteApplicant(
            @Param("clientId") String clientId);
}
