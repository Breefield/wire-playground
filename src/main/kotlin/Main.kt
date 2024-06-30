import com.bree.dinosaurs.Dinosaur

fun main(args: Array<String>) {
    println("Age:  ${Dinosaur(age = null).age}")
    println("Age:  ${Dinosaur(age = 1).age}")
    println("Name: ${Dinosaur().name}")
    println("Name: ${Dinosaur(name = "").name}")
}