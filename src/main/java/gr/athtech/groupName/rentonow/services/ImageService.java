package gr.athtech.groupName.rentonow.services;

import org.springframework.web.multipart.MultipartFile;

import gr.athtech.groupName.rentonow.exceptions.BadRequestException;
import gr.athtech.groupName.rentonow.exceptions.NotFoundException;
import gr.athtech.groupName.rentonow.exceptions.UploadException;
import gr.athtech.groupName.rentonow.models.Image;

public interface ImageService {

  /**
   * Stores an image for the property with the provided
   * propertyId.
   *
   * @param propertyId the property this image is stored for
   * @param image containing the image to be stored
   * @return the Image stored
   * @throws NotFoundException in case there is no property with the provided propertyId
   * @throws BadRequestException in case the image provided in not valid
   * @throws UploadException in case something goes wrong upon uploading of the image
   */
  public Image storeImage(Long propertyId, MultipartFile image)
      throws NotFoundException, BadRequestException, UploadException;

  /**
   * Deletes the image with the provided imageId, which belongs to
   * the property with the provided propertyId
   *
   * @param propertyId for the id of the property to which this image belongs
   * @param imageId fot the id of the image to be deleted
   * @return a String with the status of deletion
   * @throws NotFoundException in case the user does not have access to the image
   * with the provided imageId
   * @throws UploadException in case something goes wrong upon deletion of the image
   */
  public String destroyImage(Long propertyId, Long imageId) throws NotFoundException, UploadException;
}
