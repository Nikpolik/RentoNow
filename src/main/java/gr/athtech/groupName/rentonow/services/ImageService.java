package gr.athtech.groupName.rentonow.services;

import org.springframework.web.multipart.MultipartFile;

import gr.athtech.groupName.rentonow.exceptions.BadRequestException;
import gr.athtech.groupName.rentonow.exceptions.NotFoundException;
import gr.athtech.groupName.rentonow.exceptions.UploadException;
import gr.athtech.groupName.rentonow.models.Image;

public interface ImageService {
  public Image storeImage(Long propertyId, MultipartFile image)
      throws NotFoundException, BadRequestException, UploadException;
}
