package gr.athtech.groupName.rentonow.lib;

import java.io.IOException;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import gr.athtech.groupName.rentonow.exceptions.UploadException;
import gr.athtech.groupName.rentonow.models.Image;

@Component
public class FileUpload {
  private final Cloudinary cloudinaryClient;
  private static final String ERROR_MESSAGE="Could not communicate to file upload server";

  @Value("cloudinary.name")
  private String name;

  @Value("cloudinary.key")
  private String key;

  @Value("cloudinary.value")
  private String secret;

  public FileUpload()  {
    var settings = ObjectUtils.asMap(
      "cloud_name", name,
      "api_key", key,
      "api_secret", secret
    );
    this.cloudinaryClient = new Cloudinary(settings);
  }

  /**
   * 
   * @param file the image file to upload to cloadinary
   * @return A new image with url,secure, cloudinaryId url properly set
   * @throws UploadException
   */
  public Image uploadFile(MultipartFile file) throws UploadException {
    var options = ObjectUtils.asMap(
      "resource_type", "auto",
      "filename", file.getOriginalFilename()
    );
    try {
      // for some reason cloudinary no longer accepts MultiPartFiles so we must
      // send the bytes. The sample code is wrong
      // SDK line that does the file check
      // https://github.com/cloudinary/cloudinary_java/blob/master/cloudinary-http44/src/main/java/com/cloudinary/http44/UploaderStrategy.java#L114
      var response = this.cloudinaryClient.uploader().upload(file.getBytes(), options);
      String url = (String) response.get("url");
      String secureUrl = (String) response.get("secure_url");
      String cloudinaryId = (String) response.get("public_id");
      Image image = new Image();
      image.setSecureUrl(secureUrl);
      image.setUrl(url);
      image.setCloudinaryId(cloudinaryId);
      return image;
    } catch (IOException e) {
      throw new UploadException(ERROR_MESSAGE);
    }
  }

  public boolean destroyFile() {
    return true;
  }

}
