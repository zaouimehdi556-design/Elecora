package com.elecora.app.pdf

import android.content.Context
import android.net.Uri
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper

data class PdfAnalysisResult(
    val pageCount: Int,
    val extractedText: String,
    val hasText: Boolean
)

object PdfAnalyzer {

    fun analyze(
        context: Context,
        uri: Uri
    ): PdfAnalysisResult {

        PDFBoxResourceLoader.init(context)

        context.contentResolver.openInputStream(uri).use { inputStream ->

            if (inputStream == null) {
                return PdfAnalysisResult(
                    pageCount = 0,
                    extractedText = "",
                    hasText = false
                )
            }

            PDDocument.load(inputStream).use { document ->

                val pageCount = document.numberOfPages

                val stripper = PDFTextStripper()

                stripper.startPage = 1
                stripper.endPage = pageCount

                val text = stripper
                    .getText(document)
                    .trim()

                return PdfAnalysisResult(
                    pageCount = pageCount,
                    extractedText = text,
                    hasText = text.isNotBlank()
                )
            }
        }
    }
}
