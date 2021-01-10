package gr.athtech.groupName.rentonow.services.impl;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import gr.athtech.groupName.rentonow.dtos.BookingDto;
import gr.athtech.groupName.rentonow.dtos.ClosedOrBookedDatesDto;
import gr.athtech.groupName.rentonow.dtos.FindBookingDto;
import gr.athtech.groupName.rentonow.exceptions.BadRequestException;
import gr.athtech.groupName.rentonow.exceptions.NotFoundException;
import gr.athtech.groupName.rentonow.models.*;
import gr.athtech.groupName.rentonow.repositories.BookingRepository;
import gr.athtech.groupName.rentonow.services.BookingService;
import gr.athtech.groupName.rentonow.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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

    @Override
    @CacheEvict(value = "bookings", key = "#booking.id")
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

    @Override
    public BookingDto getBookingById(Long id) throws NotFoundException {
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isEmpty()) {
            throw new NotFoundException("There is no booking with the provided id");
        }
        return BookingDto.fromBooking(booking.get());
    }

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

    @Override
    public void deleteBooking(Long bookingId) throws BadRequestException, NotFoundException {
        if (bookingId == null) {
            throw new BadRequestException("The bookingId cannot de null");
        }
        //Check if the booking exists
        BookingDto bookingDto = getBookingById(bookingId);

        //Retrieve the booking's property
        Property property = propertyService.findPropertyById(bookingDto.getPropertyId());

        //Check if current user is the host of the booking's property or admin
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean currentUserIsNotTheHost = !property.getHost().getId().equals(currentUser.getId());
        boolean currentUserIsNotAdmin = !currentUser.getRole().equals(Role.ADMIN);
        if (currentUserIsNotAdmin && currentUserIsNotTheHost) {
            throw new BadRequestException("Only the host of a property or the admin can delete one of its bookings");
        }
        bookingRepository.deleteById(bookingId);
    }

}
