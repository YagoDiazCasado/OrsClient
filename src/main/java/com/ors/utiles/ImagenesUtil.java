package main.java.com.ors.utiles;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class ImagenesUtil {
	public static byte[] getImagen(String url) {
	    try {
	        // Si es una URL absoluta (http, https, etc.)
	        if (url.startsWith("http://") || url.startsWith("https://")) {
	            URI i = new URI(url);
	            try (InputStream inputStream = i.toURL().openStream()) {
	                return inputStream.readAllBytes();
	            }
	        } else {
	            // Si es una ruta relativa o absoluta del sistema de archivos
	             return Files.readAllBytes(Paths.get(url));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	  public static byte[] fileToByte(File file) throws IOException {
		    BufferedImage bImage = ImageIO.read(file);
		    ByteArrayOutputStream bos = new ByteArrayOutputStream();
		    ImageIO.write(bImage, "png", bos);  // ⚠ cuidado con formato aquí
		    return bos.toByteArray();
		}


	public static byte[] convertImageToBytes(Image image) throws IOException {
		BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ImageIO.write(bImage, "png", outputStream); // Usa "png" o el formato adecuado
		return outputStream.toByteArray();
	}

	public static Image byteArrayToImage(byte[] data) {
	    return new Image(new ByteArrayInputStream(data));
	}

}
