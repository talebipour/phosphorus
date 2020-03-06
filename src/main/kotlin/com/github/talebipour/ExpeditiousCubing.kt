import java.io.InputStream
import java.io.PrintStream
import java.util.stream.Collectors

class ExpeditiousCubing {

    companion object {
        fun solve(input: InputStream, output: PrintStream) {
            val br = input.bufferedReader()
            val times = br.readLine().split(" ").stream()
                    .map { it.replace(".", "").toInt() }
                    .collect(Collectors.toList())
            val goal = br.readLine().replace(".", "").toInt()

            times.sort()
            if ((times[1] + times[2] + times[3]) / 3 <= goal) {
                output.print("infinite")
                return
            }
            if ((times[0] + times[1] + times[2]) / 3 > goal) {
                output.print("impossible")
                return
            }
            val worstTime = 3 * goal - times[1] - times[2]
            output.print("${worstTime / 100}.${worstTime % 100}")
        }
    }

}

fun main() {
    ExpeditiousCubing.solve(System.`in`, System.out)
}
