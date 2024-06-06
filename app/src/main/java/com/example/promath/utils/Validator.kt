package com.example.promath.utils

fun isValidLogin(
    login: String
): Boolean {
    val validChar = listOf('Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M', 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '-', '_', '.')

    val l = buildList<Int> {

    }

    if (login.length < 3 || login.length > 25) {
        return false
    }

    var result = true
    for (c in login) {
        if (c !in validChar) {
            result = false
        }
    }

    return result
}

fun isValidPassword(
    password: String
): Boolean {
    val validCharUP = listOf('Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M')
    val validCharDOWN = listOf('q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm')
    val validCharNumber = listOf('1', '2', '3', '4', '5', '6', '7', '8', '9', '0')
    val validCharSymbols = listOf('-', '_', '.', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '{', '}', '<', '>', ',', '/', ':', ';', '?')

    if (password.length < 8 || password.length > 25) {
        return false
    }
    var up = 0
    var down = 0
    var num = 0
    var sym = 0

    var result = true
    for (c in password) {
        when (c) {
            in validCharUP -> {
                up += 1
            }
            in validCharDOWN -> {
                down += 1
            }
            in validCharNumber -> {
                num += 1
            }
            in validCharSymbols -> {
                sym += 1
            }
            else -> {
                result = false
            }
        }
    }

    return result && up != 0 && down != 0 && num != 0 && sym != 0
}