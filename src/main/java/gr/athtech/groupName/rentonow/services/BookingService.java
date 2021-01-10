package gr.athtech.groupName.rentonow.services;

import gr.athtech.groupName.rentonow.dtos.ClosedOrBookedDatesDto;
import gr.athtech.groupName.rentonow.dtos.BookingDto;
import gr.athtech.groupName.rentonow.dtos.FindBookingDto;
import gr.athtech.groupName.rentonow.exceptions.BadRequestException;
import gr.athtech.groupName.rentonow.exceptions.NotFoundException;
import gr.athtech.groupName.rentonow.models.Booking;
import org.springframework.data.domain.Page;


public interface BookingService {

    /**
     * A method that searches for Bookings according to
     * specific search criteria including page parameters
     * as well as propertyId, guestId, fromDate and toDate.
     *
     * @param num for the number of page required
     * @param size for the size of the page required
     * @param sortBy to indicate the column by which the results
     *        will be sorted
     * @param direction to indicate the direction of the sorting
     * @param findBookingDto with the search criteria
     * @return a Page of Bookings
     * @throws BadRequestException when all of the parameters
     *         provided are null
     */
    Page<Booking> getBookings(Integer num, Integer size, String sortBy, String direction, FindBookingDto findBookingDto) throws BadRequestException;

    /**
     * A method that retrieves a Booking by its id and
     * returns the corresponding BookingDto.
     *
     * @param id for the id of the required Booking
     * @return the required Booking
     * @throws NotFoundException when there is no
     * Booking with the provided id
     */
    Booking getBookingById(Long id) throws NotFoundException;

    /**
     * A method that creates a Booking for the provided
     * propertyId and for the dates provided in the
     * closedOrBookedDatesDto parameter.
     *
     * @param closedOrBookedDatesDto including the dates
     * this booking concerns
     * @param propertyId for the id of the property which
     * this booking concerns
     * @return the created Booking
     * @throws NotFoundException when there is no property
     * with the provided propertyId
     * @throws BadRequestException when one of the parameters
     * is null or when the startDate and endDate of the booking
     * are equal
     */
    Booking createBooking(ClosedOrBookedDatesDto closedOrBookedDatesDto, Long propertyId) throws NotFoundException, BadRequestException;

    /**
     * A method to delete a booking by its id (only allowed to
     * the host of the booking's property and the admin).
     *
     * @param id for the id of the booking to be deleted
     * @throws BadRequestException when the bookingId provided is null
     * or the user trying to delete the booking is not the host of the
     * booking's property or admin
     * @throws NotFoundException when there is no booking with the
     * provided bookingId
     */
    void deleteBooking(Long id) throws NotFoundException, BadRequestException;

}
