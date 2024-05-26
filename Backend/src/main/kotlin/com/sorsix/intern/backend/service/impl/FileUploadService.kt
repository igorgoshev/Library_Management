package com.sorsix.intern.backend.service.impl

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*

@Service
class FileUploadService {
    @Value("\${upload.path}")
    private lateinit var uploadPath: String

    fun saveImage(file: MultipartFile): String {
        val fileName = UUID.randomUUID().toString() + "_" + file.originalFilename
        val path: Path = Paths.get(uploadPath).resolve(fileName)
        Files.createDirectories(path.parent)
        file.inputStream.use { inputStream ->
            Files.copy(inputStream, path)
        }
        return fileName
    }
}