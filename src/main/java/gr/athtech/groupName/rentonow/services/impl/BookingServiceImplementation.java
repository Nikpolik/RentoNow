package gr.athtech.groupName.rentonow.services.impl;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import gr.athtech.groupName.rentonow.dtos.ClosedOrBookedDatesDto;
import gr.athtech.groupName.rentonow.dtos.BookingDto;
import gr.athtech.groupName.rentonow.dtos.FindBookingDto;
import gr.athtech.groupName.rentonow.exceptions.BadRequestException;
import gr.athtech.groupName.rentonow.exceptions.NotFoundException;
import gr.athtech.groupName.rentonow.models.*;
import gr.athtech.groupName.rentonow.repositories.BookingRepository;
import gr.athtech.groupName.rentonow.services.BookingService;
import gr.athtech.groupName.rentonow.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImplementation implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private AvailabilityServiceImpl availabilityService;

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
    @Override
    public Page<Booking> getBookings(Integer num, Integer size, String sortBy, String direction, FindBookingDto findBookingDto) throws BadRequestException {
        if (num == null || size == null || sortBy == null || direction == null) {
            throw new BadRequestException("One or all of the page parameters are null");
        }

        if (findBookingDto == null || findBookingDto.isEmpty()) {
            throw new BadRequestException("At least one of the search criteria should be provided");
        }
        QBooking qBooking = QBooking.booking;
        List<Predicate> predicatesList = new ArrayList<>();

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        predicatesList.add(qBooking.guest.eq(currentUser).or(qBooking.property.host.eq(currentUser)));

        Long guestId = findBookingDto.getGuestId();
        if (guestId != null) {
            predicatesList.add(qBooking.guest.id.eq(guestId));
        }
        Long propertyId = findBookingDto.getPropertyId();
        if (propertyId != null) {
            predicatesList.add(qBooking.property.id.eq(propertyId));
        }
        LocalDate fromDate = findBookingDto.getFromDate();
        if (fromDate != null) {
            predicatesList.add(qBooking.availability.startDate.goe(fromDate));
        }
        LocalDate toDate = findBookingDto.getToDate();
        if (toDate != null) {
            predicatesList.add(qBooking.availability.startDate.loe(toDate));
        }

        Predicate allPredicates = ExpressionUtils.allOf(predicatesList);

        Sort.Direction sortDirection = Sort.Direction.fromString(direction);
        Sort.Order order = new Sort.Order(sortDirection, sortBy);
        Sort sort = Sort.by(order);
        Pageable p = PageRequest.of(num, size, sort);

        return bookingRepository.findAll(allPredicates, p);
    }

    /**
     * A method that retrieves a Booking by its id and
     * returns the corresponding BookingDto.
     *
     * @param id for the id of the required Booking
     * @return the corresponding BookingDto for the
     * required Booking
     * @throws NotFoundException when there is no
     * Booking with the provided id
     */
    @Override
    public BookingDto getBookingById(Long id) throws NotFoundException {
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isEmpty()) {
            throw new NotFoundException("There is no booking with the provided id");
        }
        return BookingDto.fromBooking(booking.get());
    }

    /**
     * A method that creates a Booking for the provided
     * propertyId and for the dates provided in the
     * closedOrBookedDatesDto parameter.
     * @param closedOrBookedDatesDto including the dates
     * this booking concerns
     * @param propertyId for the id of the property which
     * this booking concerns
     * @return the corresponding BookingDto of the created
     * Booking
     * @throws NotFoundException when there is no property
     * with the provided propertyId
     * @throws BadRequestException when one of the parameters
     * is null or when the startDate and endDate of the booking
     * are equal
     */
    @Override
    public BookingDto createBooking(ClosedOrBookedDatesDto closedOrBookedDatesDto, Long propertyId) throws NotFoundException, BadRequestException {
        if (closedOrBookedDatesDto == null || propertyId == null) {
            throw new BadRequestException("createBookingDto and propertyId cannot be null");
        }
        if (closedOrBookedDatesDto.getStartDate() == closedOrBookedDatesDto.getEndDate()) {
            throw new BadRequestException("Start date and end date cannot be the same day");
        }
        Boolean isPropertyAvailable = availabilityService.isPropertyAvailable(propertyId, closedOrBookedDatesDto.getStartDate(), closedOrBookedDatesDto.getEndDate());
        if (!isPropertyAvailable) {
            throw new BadRequestException("The property of this booking is not available for the provided dates");
        }
        Booking booking = new Booking();
        Property property = propertyService.findPropertyById(propertyId);
        booking.setProperty(property);
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        booking.setGuest(currentUser);
        booking.setCreationDate(LocalDateTime.now());
        booking = bookingRepository.saveAndFlush(booking);
        Availability availability = availabilityService.setBooked(booking, property, closedOrBookedDatesDto);
        booking.setAvailability(availability);
        bookingRepository.save(booking);
        return BookingDto.fromBooking(booking);
    }

    /**
     * A method to delete a booking by its id
     * @param bookingId for the od of the booking to be deleted
     * @throws BadRequestException when the bookingId provided is null
     * @throws NotFoundException when there is no booking with the
     * provided bookingId
     */
    @Override
    public void deleteBooking(Long bookingId) throws BadRequestException, NotFoundException {
        if (bookingId == null) {
            throw new BadRequestException("The bookingId cannot de null");
        }
        getBookingById(bookingId);
        bookingRepository.deleteById(bookingId);
    }

}
