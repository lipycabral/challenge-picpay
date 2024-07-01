package com.tech.picpay.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class InsufficientBalanceException extends PicPayException {

    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);

        pb.setTitle("Saldo insuficiente.");
        pb.setDetail("Você não pode transferir um valor maior que seu saldo atual.");

        return pb;
    }
}