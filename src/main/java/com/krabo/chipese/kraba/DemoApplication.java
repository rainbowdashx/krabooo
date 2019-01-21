package com.krabo.chipese.kraba;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.print.DocFlavor.URL;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

@SpringBootApplication
@Controller
public class DemoApplication {

	@Autowired
	ServletContext servletContext;

	@GetMapping("/")
	public String uploadForm(Model model) {

		return "uploadForm";
	}

	@PostMapping("/")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {

		InputStream inputStream = null;
		OutputStream outputStream = null;

		String NewFileName = java.util.UUID.randomUUID().toString() + ".jpg";

		File newFile = new File("I:/wowstuff/krabbochipese/kraba/src/main/resources/static/" + NewFileName);

		try {
			inputStream = file.getInputStream();
			if (!newFile.exists()) {
				newFile.createNewFile();
			}

			SaveImageAsJPG(inputStream, newFile);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return newFile.getAbsolutePath();

		
	}

	public void SaveImageAsJPG(InputStream InInputStream, File Destination) {

		BufferedImage bufferedImage;

		try {
			// LESEN INPUT STREAM
			bufferedImage = ImageIO.read(InInputStream);

			// NEUE IMAGE in SPEICHER color = white backgorund falls tranparent image
			BufferedImage newBufferedImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(),
					BufferedImage.TYPE_INT_RGB);
			newBufferedImage.createGraphics().drawImage(bufferedImage, 0, 0, Color.WHITE, null);

			// schreiben auf plattung
			ImageIO.write(newBufferedImage, "jpg", Destination);

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
