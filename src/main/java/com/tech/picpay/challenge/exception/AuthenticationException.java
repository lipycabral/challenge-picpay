package com.tech.picpay.challenge.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class AuthenticationException extends PicPayException {
    @Override
    public ProblemDetail toProblemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);

        pb.setTitle("Usuário ou senha inválido. Tente novamente.");

        return pb;
    }
}
