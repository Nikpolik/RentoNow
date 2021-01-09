package gr.athtech.groupName.rentonow.services.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import gr.athtech.groupName.rentonow.exceptions.BadRequestException;
import gr.athtech.groupName.rentonow.exceptions.NotFoundException;
import gr.athtech.groupName.rentonow.exceptions.UploadException;
import gr.athtech.groupName.rentonow.lib.FileUpload;
import gr.athtech.groupName.rentonow.models.Image;
import gr.athtech.groupName.rentonow.models.Property;
import gr.athtech.groupName.rentonow.models.User;
import gr.athtech.groupName.rentonow.repositories.ImageRepository;
import gr.athtech.groupName.rentonow.services.ImageService;
import gr.athtech.groupName.rentonow.services.PropertyService;

@Service
public class ImageServiceImpl implements ImageService {

  private static final String ERROR_MESSAGE = "Valid images are of type JPEG/PNG and less then 25mb";
  private static final long MAX_SIZE = 25_0000; // 20 Megabytes
  private static final long MIN_SIZE = 5_000; // 5 Kilobytes a file smaller then this is propably not a valid image
  private static final Set<String> SUPPORTED_TYPES = Set.of("image/png", "image/jpeg", "image/jpg");

  @Autowired
  ImageRepository imageRepository;

  @Autowired
  PropertyService propertyService;

  @Autowired
  FileUpload fileUploader;

  @Override
  public Image storeImage(Long propertyId, MultipartFile image)
      throws NotFoundException, BadRequestException, UploadException {
    Property property = propertyService.findOwnedProperty(propertyId);
    if (!isValidImage(image)) {
      throw new BadRequestException(ERROR_MESSAGE);
    }
    Image uploadedImage = fileUploader.uploadFile(image);
    uploadedImage.setProperty(property);
    imageRepository.save(uploadedImage);
    return null;
  }

  private boolean isValidImage(MultipartFile image) {
    return isSupportedContentType(image.getContentType()) && isProperSize(image.getSize());
  }

  private boolean isSupportedContentType(String contentType) {
    try {
      return SUPPORTED_TYPES.contains(contentType);
    } catch (Exception e) {
      return false;
    }
  }

  private boolean isProperSize(Long size) {
    return size >= MIN_SIZE && size <= MAX_SIZE;
  }

  @Override
  public String destroyImage(Long propertyId, Long imageId) throws NotFoundException, UploadException {
    Image image = imageRepository.getOne(imageId);
    User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if(!image.getProperty().getHost().getId().equals(currentUser.getId())) {
      throw new NotFoundException();
    }
    String status = fileUploader.destroyFile(image.getCloudinaryId());
    if(status.equals("ok")) {
      imageRepository.delete(image);
    }
    return status;
  }

}
