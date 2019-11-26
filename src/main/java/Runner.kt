import com.isograd.exercise.IsoContest
import com.isograd.exercise.IsoContest.Printer
import java.io.*
import java.util.stream.Collectors
import kotlin.math.max


private const val INPUT_PATH = "/input%d.txt"
private const val OUTPUT_PATH = "/output%d.txt"

fun main() {
    val reporting = mutableListOf<Boolean>()

    var i = 1
    do {
        val io = InputOutputResource(String.format(INPUT_PATH, i), String.format(OUTPUT_PATH, i))
        if (!io.exists) {
            break
        }
        println("=======[ Test $i ]=======")
        println("-- Input value --\n${io.input}\n")
        reporting.add(false)

        try {
            PrinterWrapper(io).use { printer ->
                IsoContest.io = printer
                IsoContest.main(arrayOfNulls<String>(0))

                val result = printer.output
                if (isSuccess(io, result)) {
                    reporting[reporting.lastIndex] = true
                    println("✅ Test $i succeed")
                } else {
                    System.err.println("❌ Test $i fail")
                    System.err.flush()
                }
                println("\n\n")
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        i++
    } while (true)

    displayResult(reporting)
}

private fun displayResult(reporting: MutableList<Boolean>) {
    when {
        reporting.isEmpty() -> System.err.println("No test found. Try to put the inputXXX.txt and outputXXX.txt in the resources directory.")
        reporting.all { it } -> System.out.printf("100%% Success %s\n", "✅".repeat(reporting.size))
        else -> System.out.printf("Tests fail : [%s] %.2f %%\n",
                reporting.joinToString("") { if (it) "✅" else "❌" },
                reporting.count { it }.toDouble() * 100.0 / reporting.size.toDouble()
        )
    }
}

fun printLineNumber(max: Int, current: Int) = String.format("%0${max}d. |", current)

private fun isSuccess(io: InputOutputResource, result: Array<String>): Boolean {
    println("-- Output value (from IsoContest.java) --")
    var success = true
    if (result.isEmpty()) {
        println("ERROR: No output. Use io.out.println() to submit your solution !")
        success = false
    } else {
        val limit = max(result.size, io.output.size) - 1
        val lineSize = limit.toString().length

        for (i in 0..limit) {
            System.out.flush()
            val expected = if (i < io.output.size) io.output[i] else null
            val received = if (i < result.size) result[i] else null
            if (expected != received) {
                System.err.println("${printLineNumber(lineSize, i)} FAIL >>> [ Received: $received , Expected: $expected ]")
                System.err.flush()
                success = false
            } else {
                println(printLineNumber(lineSize, i) + received)
            }
        }
    }
    return success
}

class PrinterWrapper(resource: InputOutputResource) : Printer(), AutoCloseable {
    private val buffer = ByteArrayOutputStream()

    val output: Array<String>
        get() = String(buffer.toByteArray())
                .split(System.lineSeparator().toRegex())
                .filter(String::isNotEmpty)
                .toTypedArray()

    init {
        this.`in` = ByteArrayInputStream(resource.input!!.toByteArray())
        this.out = PrintStream(buffer)
    }

    override fun close() {
        try {
            this.out.close()
        } catch (e: Exception) {
            // Silent closing
        }
    }
}

class InputOutputResource(inputName: String, outputName: String) {
    val input = readString(inputName)
    var output = readArray(outputName)

    val exists: Boolean
        get() = input != null

    private fun readString(input: String) = try {
        BufferedReader(InputStreamReader(javaClass.getResourceAsStream(input))).use {
            it.lines().collect(Collectors.joining(System.lineSeparator()))
        }
    } catch (e: Exception) {
        null
    }

    private fun readArray(input: String) = try {
        BufferedReader(InputStreamReader(javaClass.getResourceAsStream(input))).use {
            it.lines().toArray { length -> arrayOfNulls<String>(length) }
        }
    } catch (e: Exception) {
        emptyArray<String>()
    }
}
