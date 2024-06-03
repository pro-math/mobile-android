package com.example.generationOfExamples

class ExampleGenerator {

    private fun convertTypeOperationToString(
        typeOperation: TypeOperation
    ): String = when (typeOperation) {
        TypeOperation.PLUS -> " + "
        TypeOperation.MINUS -> " - "
        TypeOperation.MULTIPLY -> " * "
        TypeOperation.DIVIDE -> " / "
    }

    fun generateExample(
        typeOperation: TypeOperation,
        border1: Int,
        border2: Int
    ): ExampleModel {
        var number1: Int = 0
        var number2: Int = 0
        var answer: Int = 0

        if (typeOperation != TypeOperation.DIVIDE) {
            number1 = (border1..border2).random()
            number2 = (border1..border2).random()
        } else {
            number2 = (border1..border2).random()
            while (number2 == 0) {
                number2 = (border1..border2).random()
            }
            answer = (border1..(border2 / number2)).random()
            number1 = answer * border2
        }

        answer = when (typeOperation) {
            TypeOperation.PLUS -> number1 + number2
            TypeOperation.MINUS -> number1 - number2
            TypeOperation.MULTIPLY -> number1 * number2
            TypeOperation.DIVIDE -> number1 / number2
        }

        val exampleString: String = number1.toString() + convertTypeOperationToString(typeOperation = typeOperation) + number2.toString() + " ="

        return ExampleModel(
            exampleString = exampleString,
            typeOperation = typeOperation,
            number1 = number1,
            number2 = number2,
            answer = answer
        )
    }

}