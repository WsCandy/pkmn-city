package com.gocity.graphql.hello;

import com.gocity.graphql.hello.rpc.Hello;
import com.gocity.graphql.hello.rpc.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

// gradle generateProto

@Service
public class HelloService extends HelloServiceGrpc.HelloServiceImplBase {

    @Override
    public void hello(Hello.Request request, StreamObserver<Hello.Response> responseObserver) {
        var text = request.getText();
        var response = Hello.Response.newBuilder()
            .setText(text)
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
