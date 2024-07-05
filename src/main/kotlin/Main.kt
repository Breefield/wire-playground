import com.bree.dinosaurs.Dinosaur
import com.bree.dinosaurs.DinosaurTestClient
import com.bree.dinosaurs.DinosaurTestWireGrpc
import com.squareup.wire.GrpcClient

import io.grpc.ServerBuilder
import io.grpc.Status
import io.grpc.StatusException
import io.grpc.StatusRuntimeException
import io.grpc.protobuf.services.ProtoReflectionService
import io.grpc.stub.StreamObserver
import okhttp3.OkHttpClient
import okhttp3.Protocol

fun main(args: Array<String>) {
    println("Age:  ${Dinosaur(age = null).age}`")
    println("Age:  ${Dinosaur(age = 1).age}")
    println("Age:  ${Dinosaur(age = 0).age}")
    println("Name: ${Dinosaur().name}")
    println("Name: ${Dinosaur(name = "").name}")

    val server = ServerBuilder.forPort(8080)
        .addService(TestServer())
        .addService(ProtoReflectionService.newInstance())
        .build()

    server.start()
    server.awaitTermination()

    val grpcClient = GrpcClient.Builder()
        .client(OkHttpClient.Builder().protocols(listOf(Protocol.H2_PRIOR_KNOWLEDGE)).build())
        .baseUrl("http:0.0.0.0:8080")
        .build()

    val client = grpcClient.create(DinosaurTestClient::class)

}

class TestServer : DinosaurTestWireGrpc.DinosaurTestImplBase() {
    override fun Reflect(request: Dinosaur, response: StreamObserver<Dinosaur>) {
        try {
            response.onNext(request)
            response.onCompleted()
        } catch (e: StatusException) {
            response.onError(e)
        } catch (e: StatusRuntimeException) {
            response.onError(e)
        } catch (e: Exception) {
            response.onError(StatusException(Status.INTERNAL.withCause(e).withDescription(e.toString())))
        }
    }
}
