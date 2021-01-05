package gr.athtech.groupName.rentonow.services.impl;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import gr.athtech.groupName.rentonow.dtos.BookingDto;
import gr.athtech.groupName.rentonow.dtos.CreateBookingDto;
import gr.athtech.groupName.rentonow.exceptions.BadRequestException;
import gr.athtech.groupName.rentonow.exceptions.NotFoundException;
import gr.athtech.groupName.rentonow.models.Booking;
import gr.athtech.groupName.rentonow.models.Property;
import gr.athtech.groupName.rentonow.models.User;
import gr.athtech.groupName.rentonow.repositories.BookingRepository;
import gr.athtech.groupName.rentonow.services.AvailabilityService;
import gr.athtech.groupName.rentonow.services.BookingService;
import gr.athtech.groupName.rentonow.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static gr.athtech.groupName.rentonow.models.QBooking.booking;

@Service
public class BookingServiceImplementation implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private AvailabilityService availabilityService;

    @Override
    public Page<Booking> getBookings(Long guestId, Long propertyId, LocalDate fromDate, LocalDate toDate) throws BadRequestException {
        if (guestId == null && propertyId == null && fromDate == null && toDate == null) {
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "One of the following should be provided: Guest id, Property id, From Date, To Date");
        }

        List<Predicate> predicatesList = new ArrayList<>();

        if (guestId != null) {
            predicatesList.add(booking.guest.id.eq(guestId));
        }
        if (propertyId != null) {
            predicatesList.add(booking.property.id.eq(propertyId));
        }
        if (fromDate != null) {
            predicatesList.add(booking.startDate.goe(fromDate));
        }
        if (toDate != null) {
            predicatesList.add(booking.startDate.loe(toDate));
        }

        Predicate allPredicates = ExpressionUtils.allOf(predicatesList);

        //add parameters for sorting and paging ?

        Sort.Direction sortDirection = Sort.Direction.fromString("asc");
        Sort.Order order = new Sort.Order(sortDirection, "startDate");
        Sort sort = Sort.by(order);
        Pageable p = PageRequest.of(1, 10, sort);

        return bookingRepository.findAll(allPredicates, p);
    }

    @Override
    public BookingDto getBookingById(Long id) throws NotFoundException {
        Optional<Booking> booking = bookingRepository.findById(id);
        if (booking.isEmpty()) {
            throw new NotFoundException(HttpStatus.NOT_FOUND, "There is no booking with the provided id");
        }
        return BookingDto.fromBooking(booking.get());
    }

    @Override
    public BookingDto createBooking(CreateBookingDto createBookingDto, Long propertyId) throws NotFoundException, BadRequestException {
        if (createBookingDto == null || propertyId == null) {
            throw new BadRequestException("createBookingDto and propertyId cannot be null");
        }
        if (createBookingDto.getStartDate() == createBookingDto.getEndDate()) {
            throw new BadRequestException("Start date and end date cannot be the same day");
        }
        Boolean isPropertyAvailable = availabilityService.isPropertyAvailable(propertyId, createBookingDto.getStartDate(), createBookingDto.getEndDate());
        if (!isPropertyAvailable) {
            throw new BadRequestException("The property of this booking is not available for the provided dates");
        }
        Booking booking = CreateBookingDto.toBooking(createBookingDto);
        Property property = propertyService.findPropertyById(propertyId);
        booking.setProperty(property);
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        booking.setGuest(currentUser);
        booking = bookingRepository.save(booking);
        availabilityService.createAvailability(booking, property);
        return BookingDto.fromBooking(booking);
    }

    @Override
    public BookingDto updateBooking(BookingDto bookingDto) {
        return null;
    }

}
