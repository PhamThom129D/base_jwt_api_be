package com.example.base_jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.env.Environment;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
@RequestMapping("")
public class UploadController {

    @Autowired
    private Environment env;


    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file, Model model) throws IOException {
        if (!file.isEmpty()) {
            String uploadPath = env.getProperty("upload.image.dir");
            String filename = file.getOriginalFilename();
            Path path = Paths.get(uploadPath, filename);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            // Trả về đường dẫn để hiển thị ảnh
            model.addAttribute("msg", "/img/" + filename);
        } else {
            model.addAttribute("msg", "Khong co anh");
        }
        return "redirect:/";
    }


    @GetMapping("/")
    public String showUploadForm() {
        return "upload";
    }
}
