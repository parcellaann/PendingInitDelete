package com.pendinginitdelete.interfaces;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import feign.Response;

public interface Requests {

    @RequestLine("POST /resources/applicants/{applicantId}/status/pending")
    @Headers({"Authorization: Bearer {bearer}", "Content-Type: application/json", "X-Client-Id: dashboard", "X-Impersonate: {clientId}"})
    Response applicantMoveToPending(
            @Param("applicantId") String applicantId,
            @Param("bearer") String bearer,
            @Param("clientId") String clientId);

    @RequestLine("POST /resources/applicants/{applicantId}/review/status/init")
    @Headers({"Authorization: Bearer {bearer}", "Content-Type: application/json", "X-Client-Id: dashboard", "X-Impersonate: {clientId}"})
    Response applicantMoveToInit(
            @Param("applicantId") String applicantId,
            @Param("bearer") String bearer,
            @Param("clientId") String clientId);

    @RequestLine("DELETE /resources/applicants/{applicantId}")
    @Headers({"Authorization: Bearer {bearer}", "X-Rate-Limiter: no-limit", "Content-Type: application/json", "X-Client-Id: dashboard", "X-Impersonate: {clientId}"})
    Response deleteApplicant(
            @Param("applicantId") String applicantId,
            @Param("bearer") String bearer,
            @Param("clientId") String clientId);
}
