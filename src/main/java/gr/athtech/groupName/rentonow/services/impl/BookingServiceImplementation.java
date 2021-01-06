package gr.athtech.groupName.rentonow.services.impl;

import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import gr.athtech.groupName.rentonow.dtos.BookingDto;
import gr.athtech.groupName.rentonow.dtos.CreateBookingDto;
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

    @Override
    public Page<Booking> getBookings(Integer num, Integer size, String sortBy, String direction, FindBookingDto findBookingDto) throws BadRequestException {
        if (num == null || size == null || sortBy == null || direction == null) {
            throw new BadRequestException("One or all of the page parameters are null");
        }

        QBooking qBooking = QBooking.booking;
        List<Predicate> predicatesList = new ArrayList<>();

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
            predicatesList.add(qBooking.startDate.goe(fromDate));
        }
        LocalDate toDate = findBookingDto.getToDate();
        if (toDate != null) {
            predicatesList.add(qBooking.startDate.loe(toDate));
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
        booking.setCreationDate(LocalDateTime.now());
        booking = bookingRepository.saveAndFlush(booking);
        Availability availability = availabilityService.setBooked(booking, property);
        booking.setAvailability(availability);
        bookingRepository.save(booking);
        return BookingDto.fromBooking(booking);
    }

    @Override
    public void deleteBooking(Long bookingId) throws BadRequestException, NotFoundException {
        if (bookingId == null) {
            throw new BadRequestException("The bookingId cannot de null");
        }
        getBookingById(bookingId);
        bookingRepository.deleteById(bookingId);
    }

}
