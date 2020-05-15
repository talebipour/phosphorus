package com.github.talebipour

import java.util.*
import kotlin.collections.HashMap

val sentences = mutableListOf<SentenceWord>()
val wordMap = HashMap<String, Word>()

fun main() {
    val input = readLine()

    val tokenizer = StringTokenizer(input, " ,.", true)

    var commaBefore = false

    while (tokenizer.hasMoreTokens()) {
        val previous = sentences.lastOrNull()
        when (val token = tokenizer.nextToken()) {
            "," -> {
                previous?.word!!.commaAfter = true
                commaBefore = true
            }
            " " -> {
            }
            "." -> {
                previous?.endOfSentence = true
                commaBefore = false
            }
            else -> {
                var word = wordMap[token]
                if (word == null) {
                    word = Word(token)
                    wordMap[token] = word
                }
                sentences.add(SentenceWord(word))
                if (previous != null && !previous.endOfSentence) {
                    previous.word.rightWords.add(word)
                    word.leftWords.add(previous.word)
                }
                word.commaBefore = commaBefore || commaBefore
                commaBefore = false
            }
        }

    }

    for (word in wordMap.values) {
        if (word.commaAfter) {
            word.applyCommaAfter()
        }
        if (word.commaBefore) {
            word.applyCommaBefore()
        }
    }

    val writer = System.out.bufferedWriter()
    for ((i, sentenceWord) in sentences.withIndex()) {
        writer.append(sentenceWord.word.text)
        if (sentenceWord.endOfSentence) {
            writer.append('.')
            if (i < sentences.size - 1) {
                writer.append(' ')
            }
        } else if (sentenceWord.word.commaAfter || (i < sentences.size - 1 && sentences[i + 1].word.commaBefore)) {
            writer.append(", ")
        } else if (i < sentences.size - 1) {
            writer.append(' ')
        }
    }
    writer.flush()
}

class Word(val text: String) {
    var commaBefore =  false
    var commaAfter =  false
    var commaAfterApplied =  false
    var commaBeforeApplied =  false
    val leftWords = mutableSetOf<Word>()
    val rightWords = mutableSetOf<Word>()

    fun applyCommaAfter() {
        if (commaAfterApplied) {
            return
        }
        commaAfter = true
        commaAfterApplied = true
        for (word in rightWords) {
            word.applyCommaBefore()
        }
    }

    fun applyCommaBefore() {
        if (commaBeforeApplied) {
            return
        }
        commaBefore = true
        commaBeforeApplied = true
        for (word in leftWords) {
            word.applyCommaAfter()
        }
    }

    override fun toString(): String {
        return "Word(text='$text', commaBefore=$commaBefore, commaAfter=$commaAfter)"
    }

}
class SentenceWord(val word: Word) {
    var endOfSentence = false
    override fun toString(): String {
        return word.text
    }

}