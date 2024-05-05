package com.example.feed.util;

import io.grpc.ManagedChannel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import member.MemberRelation;
import member.MemberServiceGrpc;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GrpcClientUtil {

    @Qualifier("managedMemberChannel")
    private final ManagedChannel channel;

    @Qualifier("memberServiceStub")
    private final MemberServiceGrpc.MemberServiceBlockingStub stub;

    public MemberRelation.MemberResponse getMemberRelations(long memberSeq) {
        MemberRelation.MemberRequest request = MemberRelation.MemberRequest.newBuilder().setMemberSeq(memberSeq).build();
        return stub.getMemberRelations(request);
    }

    public void shutdown() {
        channel.shutdown();
    }




}
