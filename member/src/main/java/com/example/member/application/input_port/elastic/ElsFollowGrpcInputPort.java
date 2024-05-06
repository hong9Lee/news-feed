package com.example.member.application.input_port.elastic;

import com.example.member.framework.web.dto.member.RelationsOutPutDTO;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import member.MemberRelation;
import member.MemberServiceGrpc;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import static java.util.Collections.emptyList;

@Service
@RequiredArgsConstructor
public class ElsFollowGrpcInputPort extends MemberServiceGrpc.MemberServiceImplBase {

    private final EsFollowInputPort esFollowInputPort;

    @Override
    public void getMemberRelations(MemberRelation.MemberRequest request,
                                   StreamObserver<MemberRelation.MemberResponse> responseObserver) {
        Long memberSeq = request.getMemberSeq();
        RelationsOutPutDTO dto = esFollowInputPort.getMemberRelations(memberSeq);

        MemberRelation.MemberResponse response = mapDtoToGrpcResponse(dto);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    private MemberRelation.MemberResponse mapDtoToGrpcResponse(RelationsOutPutDTO dto) {
        return MemberRelation.MemberResponse.newBuilder()
                .setMemberSeq(dto.getMemberSeq())
                .addAllFollowers(ObjectUtils.isEmpty(dto.getFollowers()) ? emptyList() : dto.getFollowers())
                .addAllFollowings(ObjectUtils.isEmpty(dto.getFollowings()) ? emptyList() : dto.getFollowings())
                .addAllBlockMembers(ObjectUtils.isEmpty(dto.getBlockMembers()) ? emptyList() : dto.getBlockMembers())
                .build();
    }
}
